package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午2:46
 * @desc
 */
@Mapper
public interface UserMapper {

    /**
     * 根据手机号码登录
     *
     * @param mobile   String
     * @param password String
     * @return User
     */
    User login(@Param("mobile") String mobile,
               @Param("password") String password);

    /**
     * 根据手机查询是否存在
     *
     * @param mobile String
     * @return int
     */
    int validByMobile(@Param("mobile") String mobile);

    /**
     * 查询昵称是否存在
     *
     * @param nickName String
     * @return int
     */
    int validByNickName(@Param("nickName") String nickName);


    /**
     * 根据id查询
     *
     * @param id Long
     * @return User
     */
    User findById(@Param("id") Long id);


    /**
     * 更新用户登录IP地址
     *
     * @param id Long
     * @param ip String
     */
    void updateLoginIpAddress(@Param("id") Long id,
                              @Param("ip") String ip);

    /**
     * 注册
     *
     * @param user User
     * @return int
     */
    int register(User user);

    /**
     * 查询用户列表
     *
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param searchKey     匹配条件
     * @param userType      用户类型
     * @param certification 用户认证
     * @return Page
     */
    Page<User> findByCondition(@Param("startDate") String startDate,
                               @Param("endDate") String endDate,
                               @Param("searchKey") String searchKey,
                               @Param("userType") String userType,
                               @Param("certification") String certification);

    /**
     * 修改管理员个人信息
     *
     * @param user User
     * @return int
     */
    int updateAdminInfo(User user);

    /**
     * 修改卖家个人基础信息
     *
     * @param user User
     * @return int
     */
    int updateSellerInfo(User user);

    /**
     * 修改卖家个人需要认证的信息
     *
     * @param user User
     * @return int
     */
    int updateSellerAuthInfo(User user);

    /**
     * 校验是否是管理员用户
     *
     * @param userId Long
     * @return int
     */
    int validAdmin(@Param("userId") Long userId);

    /**
     * 商户信息审核
     *
     * @param sellerId      Long
     * @param certification String
     * @param reason        String
     * @return int
     */
    int auditSeller(@Param("sellerId") Long sellerId,
                    @Param("certification") String certification,
                    @Param("reason") String reason);

    /**
     * 修改密码
     *
     * @param userId   Long
     * @param password String
     * @return int
     */
    int updatePassword(@Param("userId") Long userId,
                       @Param("password") String password);
}
