package com.focuschina.ehealth_lib.util;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 这里可以将Object转为 json,map,str...
 * 
 *\@JSONType(ignores={"url"})产生json时将不会有url
 */
public class ClassUtil {
	
	static String strType = String.class.getSimpleName();
	static String intType = Integer.TYPE+"";
	static String booleanType = Boolean.TYPE+"";
	static String byteType = Byte.TYPE+"";
	static String charType = char.class.getSimpleName();
	static String shortType = Short.TYPE+"";
	static String floatType = Float.TYPE+"";
	static String longType = Long.TYPE+"";
	static String doubleType = Double.TYPE+"";
	static String dateType = Date.class.getSimpleName();

	
	/** 表示有modifie中的一个既可*/
	public static final int OR = 0x10000000;
	/** 表示与modifie完全一样  0表示package*/
	public static final int AND = 0x00000000;
	
	/**
	 * 自己写的反谢（对父类也进行反谢）
	 */
	public static Map<String, Object> toMap(Object obj){
		return toMap(obj, true);
	}
	/**
	 * 由类得到map 
	 * @param obj
	 * @param isF -- 对父类反射吗
	 * @return: Map<String,Object>
	 */
	public static Map<String, Object> toMap(Object obj, boolean isF){
		return toMap(obj,isF, Modifier.PUBLIC|AND);
	}
	/**
	 * 由类得到map 
	 * @param obj
	 * @param isF -- 对父类反射吗
	 * @param modifie -- or|Modifier.PUBLIC|...
	 * @return: Map<String,Object>
	 */
	public static Map<String, Object> toMap(Object obj, boolean isF, int modifie){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class<?> c = obj.getClass();
			do {
				map = toMap(obj, c, map,modifie);
			} while (isF&&(c = c.getSuperclass())!=null);
		} catch (Exception e) {
			LogUtil.err(e);
		}
		return map;
	}
	/**
	 * @param modifie --反射那些modifie的字断 (注意 package 为0)
	 */
	private static Map<String, Object> toMap(Object obj, Class<?> c, Map<String, Object> map, int modifie) throws Exception {
		boolean isOr =(modifie & 0x10000000)>0;
		Field[] declaredFields = c.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field field = declaredFields[i];
			//修饰符Modifier
			int modifiers = field.getModifiers();
			if (isOr) {//有一个相同即可
				if ((modifie & modifiers)<=0) {
					continue;
				}
			}else {//完全一样
				if (modifiers!=modifie) {
					continue;
				}
			}
			// 如int
			String type = field.getType().getSimpleName();
			// 变量名
			String name = field.getName();
			if (isBaseType(type)) {
				field.setAccessible(true);
				Object object = field.get(obj);
				map.put(name, object);
			}else{
				Class<?> cls = field.getType();
				if (c.equals(cls)) {//当为单例时
					continue;
				}
				map = toMap(cls.newInstance(),cls,map,modifie);
			}
		}
		return map;
	}
	
	/**
	 * 是否为基本类型
	 */
	public static boolean isBaseType(String type){
		if (type.equals(intType)) {
			return true;
		}
		if (type.equals(strType)) {
			return true;
		}
		if (type.equals(booleanType)) {
			return true;
		}
		if (type.equals(byteType)) {
			return true;
		}
		if (type.equals(charType)) {
			return true;
		}
		if (type.equals(shortType)) {
			return true;
		}
		if (type.equals(floatType)) {
			return true;
		}
		if (type.equals(longType)) {
			return true;
		}
		if (type.equals(doubleType)) {
			return true;
		}
		if (type.equals(dateType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据路径反谢
	 */
	public static String toUrl(Class<?> c, int index){
		String name = c.getName();
		String[] strs = name.split("\\.");
		String url = "";
		for (int i = index; i < strs.length; i++) {
			url += strs[i] + "/";
		}
		url += '/';
		url = url.replace("//", "");
		return url;
	}
	/**
	 * fastJson用得转换
	 */
	public static String toJson(Object obj){
		return  new Gson().toJson(obj);
	}
	/**
	 * map里的内容转成json
	 */
	public static String toJson(Map<String, Object> map){
		return new Gson().toJson(map);
	}
	
	/**
	 * 如     test=df&test2=test
	 */
	public static String toAndStr(Object obj){
		return toSome(obj, '&');
	}
	/**
	 * 各字断用c相连
	 */
	private static String toSome(Object obj, char c){
		String str = "";
		Map<String, Object> map = toMap(obj);
		Set<Entry<String,Object>> entrySet = map.entrySet();
		for (Entry<String,Object> e:entrySet) {
			str += e.getKey()+"="+e.getValue()+c;
		}
		str += c;
		str = str.replace(""+c+c, "");
		return str;
	}




	/**
	 * @param obj 中只能放基本类型和实现了Serializable接口的内容
	 * @return
	 * @throws Exception
     */
	public static int getSize(Object obj) throws Exception {
		if (obj instanceof Bitmap){
			Bitmap bitmap = (Bitmap) obj;
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
		int size= 0;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			size = baos.size();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
			}
		}
		return size;
	}

}
