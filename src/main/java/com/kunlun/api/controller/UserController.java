package com.kunlun.api.controller;

import com.kunlun.api.service.UserService;
import com.kunlun.entity.User;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午2:43
 * @desc 用户模块
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    UserService userService;

    /**
     * 登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @param request  请求
     * @return DataRet
     */
    @PostMapping(value = "/login")
    public DataRet login(@RequestParam(value = "mobile") String mobile,
                         @RequestParam(value = "password") String password,
                         HttpServletRequest request) throws Exception {
        String ip = IpUtil.getIPAddress(request);
        return userService.login(mobile, password, ip);
    }

    /**
     * 商户注册
     *
     * @param user               User
     * @param httpServletRequest HttpServletRequest
     * @return DataRet
     */
    @PostMapping("/register")
    public DataRet registry(@RequestBody User user,
                            HttpServletRequest httpServletRequest) throws Exception {
        String ip = IpUtil.getIPAddress(httpServletRequest);
        return userService.register(user, ip);
    }

    /**
     * 修改个人资料
     *
     * @param user User
     * @return DataRet
     */
    @PostMapping(value = "/updateInfo")
    public DataRet updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    /**
     * 修改商户个人资料（需要后台系统认证的信息）
     *
     * @param user User
     * @return DataRet
     */
    @PostMapping(value = "/updateAuthInfo")
    public DataRet updateUserAuthInfo(@RequestBody User user) {
        return userService.updateUserAuthInfo(user);
    }


    /**
     * 修改密码
     *
     * @param password 密码
     * @param userId   Long
     * @return BaseResult
     */
    @GetMapping(value = "/updatePassword")
    public DataRet updatePassword(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "password") String password) throws Exception {
        return userService.updatePassword(userId, password);
    }


    /**
     * 后台系统退出登录
     *
     * @param mobile 手机号
     * @return BaseResult
     */
    @GetMapping(value = "/logout")
    public DataRet logout(@RequestParam(value = "mobile") String mobile) {
        return userService.logout(mobile);
    }


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
    @GetMapping(value = "/userList")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "startDate", required = false) String startDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam(value = "searchKey", required = false) String searchKey,
                                      @RequestParam(value = "userType", required = false) String userType,
                                      @RequestParam(value = "certification", required = false) String certification) {
        return userService.findByCondition(pageNo, pageSize, startDate, endDate, searchKey, userType, certification);
    }


    /**
     * 根据id查询详情
     *
     * @param id 主键
     * @return BaseResult
     */
    @GetMapping(value = "/findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return userService.findById(id);
    }

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
    @PostMapping(value = "/auditSeller")
    public DataRet auditSeller(@RequestParam(value = "sellerId") Long sellerId,
                               @RequestParam(value = "operateId") Long operateId,
                               @RequestParam(value = "certification") String certification,
                               @RequestParam(value = "reason", required = false) String reason) {
        return userService.auditSeller(sellerId, operateId, certification, reason);
    }
}
