package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */
@Repository
public interface UserDao extends BaseDao<User> {
    public User queryUserByName(String userName);

    public Integer updateUserPwdById(User user);

    public List<Map> queryCustomerManagers();

    public List<UserDto> queryByParams(UserQuery userQuery);

}
