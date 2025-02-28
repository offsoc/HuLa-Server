package com.hula.core.user.service.cache;

import com.hula.common.constant.RedisKey;
import com.hula.common.service.cache.AbstractRedisStringCache;
import com.hula.core.user.dao.UserDao;
import com.hula.core.user.domain.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户基本信息的缓存
 * @author nyh
 */
@Component
public class UserInfoCache extends AbstractRedisStringCache<Long, User> {
    @Resource
    private UserDao userDao;

    @Override
    protected String getKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_INFO_FORMAT, uid);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    @Override
    protected Map<Long, User> load(List<Long> uidList) {
        List<User> needLoadUserList = userDao.listByIds(uidList);
        return needLoadUserList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
