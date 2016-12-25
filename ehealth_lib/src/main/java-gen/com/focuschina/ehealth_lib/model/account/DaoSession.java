package com.focuschina.ehealth_lib.model.account;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.focuschina.ehealth_lib.model.account.UserLogin;

import com.focuschina.ehealth_lib.model.account.UserLoginDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userLoginDaoConfig;

    private final UserLoginDao userLoginDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userLoginDaoConfig = daoConfigMap.get(UserLoginDao.class).clone();
        userLoginDaoConfig.initIdentityScope(type);

        userLoginDao = new UserLoginDao(userLoginDaoConfig, this);

        registerDao(UserLogin.class, userLoginDao);
    }
    
    public void clear() {
        userLoginDaoConfig.clearIdentityScope();
    }

    public UserLoginDao getUserLoginDao() {
        return userLoginDao;
    }

}
