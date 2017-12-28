package com.kunlun.api.service;

import com.kunlun.entity.User;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午2:46
 * @desc
 */
public interface UserService {

    /**
     * 后台登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @param ip       IP地址
     * @return DataRet
     */
    DataRet login(String mobile, String password, String ip) throws Exception;

    /**
     * 管理后台注册
     *
     * @param user User
     * @return DataRet
     */
    DataRet register(User user) throws Exception;

    /**
     * 修改个人资料
     *
     * @param user User
     * @return DataRet
     */
    DataRet updateUserInfo(User user);

    /**
     * 修改个人资料
     *
     * @param user User
     * @return DataRet
     */
    DataRet updateUserAuthInfo(User user);

    /**
     * 后台系统退出登录
     *
     * @param mobile 手机号
     * @return DataRet
     */
    DataRet logout(String mobile);

    /**
     * 修改密码
     *
     * @param userId   Long
     * @param password String
     * @return DataRet
     * @throws Exception e
     */
    DataRet updatePassword(Long userId, String password) throws Exception;

    /**
     * 分页条件查询 会员列表
     *
     * @param pageNo        当前页
     * @param pageSize      每页条数
     * @param startDate     注册时间
     * @param endDate       注册时间
     * @param searchKey     模糊查询信息
     * @param userType      用户类型
     * @param certification 认证
     * @return List
     */
    PageResult findByCondition(Integer pageNo,
                               Integer pageSize,
                               String startDate,
                               String endDate,
                               String searchKey,
                               String userType,
                               String certification);

    /**
     * 根据id查询详情
     *
     * @param id Long
     * @return DataRet
     */
    DataRet findById(Long id);

    /**
     * 审核商户信息
     *
     * @param sellerId      商户id
     * @param operateId     操作人id
     * @param certification 认证标识
     *                      NOT_AUTH 未实名认证,
     *                      AUTH_ING 审核中，
     *                      PASS_AUTH 认证通过，
     *                      NOT_PASS_AUTH 认证未通过
     * @param reason        不通过原因
     * @return DataRet
     */
    DataRet auditSeller(Long sellerId, Long operateId, String certification, String reason);

    /**
     * 校验账号是否是管理员账号
     * @param userId Long
     * @return  DataRet
     */
    DataRet validAdmin(Long userId);
}
