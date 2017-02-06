package com.focuschina.ehealth_lib.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: RxBus
 * @Description: TODO: (rxJava 事件总线)
 * @date 2017/1/5 下午2:38
 */
public class RxBus {

    private static volatile RxBus mInstance;

    private final Subject<RxEvent, RxEvent> bus;

    private final Map<String, RxEvent> mStickyEventMap;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) mInstance = new RxBus();
            }
        }
        return mInstance;
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void reset() {
        mInstance = null;
    }

    /**
     * 发送消息
     *
     * @param event 事件
     */
    public void post(Object event, String tag) {
        RxEvent rxEvent = new RxEvent(tag, event);
        send(rxEvent);
    }

    /**
     * 发送空消息
     */
    public void post(String tag) {
        RxEvent rxEvent = new RxEvent(tag, new Object());
        send(rxEvent);
    }

    /**
     * 粘性发生，如果没出现订阅，则根据类型保留最后一次的数据，当订阅者订阅后则发生给订阅者
     *
     * @param event 事件
     */
    public void postSticky(Object event, String tag) {
        synchronized (mStickyEventMap) {
            RxEvent rxEvent = new RxEvent(tag, event, true);
            mStickyEventMap.put(tag, rxEvent);
            send(rxEvent);
        }
    }

    public void postSticky(String tag) {
        synchronized (mStickyEventMap) {
            RxEvent rxEvent = new RxEvent(tag, new Object(), true);
            mStickyEventMap.put(tag, rxEvent);
            send(rxEvent);
        }
    }

    private void send(RxEvent rxEvent) {
        bus.onNext(rxEvent);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType 事件
     * @param <E>       事件类型
     * @return Observable
     */
    public <E> Observable<E> toObservable(Class<E> eventType, String tag) {
//        return bus.ofType(eventType);  //ofType = filter + cast
        return bus
                .filter(rxEvent -> //rxEvent.getData() = null,不用再去检查是不是特定类型或者其子类的实例
                        tag.equals(rxEvent.getTag()) && (rxEvent.getData() == null || eventType.isInstance(rxEvent.getData())))
                .flatMap(new Func1<RxEvent, Observable<E>>() {
                    @Override
                    public Observable<E> call(RxEvent rxEvent) {
                        return Observable.just(eventType.cast(rxEvent.getData()));
                    }
                });
    }

    /**
     * 获取粘性事件传递的 被观察者
     *
     * @param eventType 事件
     * @param <E>       事件类型
     * @return Observable
     */
    public <E> Observable<E> toObservableSticky(final Class<E> eventType, String tag) {
        synchronized (mStickyEventMap) {
            Observable<E> observable = toObservable(eventType, tag);
            final Object event = getStickyEvent(tag); //查询是否有数据
            if (event != null) { //发送最后的一个缓存数据
                return observable.mergeWith(Observable.create(
                        subscriber -> subscriber.onNext(eventType.cast(event))));
            } else {
                return observable;
            }
        }
    }


    public <E> Subscription subscribeEvent(Class<E> eventType, String tag, RxReceiver<E> receiver) {
        return toObservable(eventType, tag).subscribe(new TryCatchSubscribe<>(receiver));
    }


    public <E> Subscription subscribeEventSticky(Class<E> eventType, String tag, RxReceiver<E> receiver) {
        return toObservableSticky(eventType, tag).subscribe(new TryCatchSubscribe<>(receiver));
    }

    private class TryCatchSubscribe<E> extends Subscriber<E> {
        private RxReceiver<E> receiver;

        TryCatchSubscribe(RxReceiver<E> receiver) {
            this.receiver = receiver;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            /**
             * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
             * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
             * subscribeEvent(eventType); //不建议
             */
            /**
             * 原因:
             * 这里注意: Sticky事件 不能在onError时重绑事件,这可能导致因绑定时得到引起Error的Sticky数据而产生死循环
             * 所以此处的做法是采取统一自己捕捉异常处理，使其不走onError的执行
             */
        }

        @Override
        public void onNext(E e) {
            try {
                if (null != receiver) receiver.onReceiveEvent(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * 根据tag获取Sticky事件
     */
    public RxEvent getStickyEvent(String tag) {
        synchronized (mStickyEventMap) {
            return mStickyEventMap.get(tag);
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public void removeStickyEvent(String tag) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.remove(tag);
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
