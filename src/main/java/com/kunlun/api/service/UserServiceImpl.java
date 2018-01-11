package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.FileClient;
import com.kunlun.api.client.SysRoleClient;
import com.kunlun.api.mapper.UserMapper;
import com.kunlun.constant.Constant;
import com.kunlun.entity.MallImg;
import com.kunlun.entity.SysRole;
import com.kunlun.entity.User;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.CommonUtil;
import com.kunlun.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午2:46
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FileClient fileClient;

    @Autowired
    SysRoleClient roleClient;

    @Override
    public DataRet login(String mobile, String password, String ip) throws Exception {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return new DataRet("param_error", "参数错误");
        }
        if (mobile.length() < Constant.MOBILE_LENGTH) {
            return new DataRet("login_error", "账号有误");
        }
        if (password.length() < Constant.PASSWORD_MIN_LENGTH) {
            return new DataRet("login_error", "密码不能小于六位");
        }
        String encryptPassword = EncryptUtil.encryptMD5(password);
        User user = userMapper.login(mobile, encryptPassword);
        if (null == user) {
            return new DataRet("ERROR", "手机号或密码不正确!");
        }
        //更新用户最后登录IP地址
        userMapper.updateLoginIpAddress(user.getId(), ip);
        //将用户手机号码作为加密字符串回传
        String tokenStr = EncryptUtil.aesEncrypt(user.getMobile(), "ScorpionMall8888");
        user.setToken(tokenStr);
        return new DataRet<>(user);
    }

    @Override
    public DataRet register(User user) throws Exception {
        if (user == null || StringUtils.isEmpty(user.getMobile())) {
            return new DataRet("param_error", "参数有误");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            return new DataRet("param_error", "参数有误");
        }

        if (user.getMobile().length() < Constant.MOBILE_LENGTH) {
            return new DataRet("mobile_error", "手机号码有误");
        }

        if (user.getPassword().length() < Constant.PASSWORD_MIN_LENGTH) {
            return new DataRet("password_error", "密码长度不能小于六位");
        }

        Integer mobileResult = userMapper.validByMobile(user.getMobile());
        if (mobileResult > 0) {
            return new DataRet("mobile_exist", "手机号已存在");
        }

        Integer nickNameResult = userMapper.validByNickName(user.getNickName());
        if (nickNameResult > 0) {
            return new DataRet("nickName_exist", "该昵称已存在");
        }

        //计算年龄和性别
        user.setAge(CommonUtil.getAgeByIdCardNo(user.getCertificateId()));
        user.setSex(CommonUtil.getGenderByIdCardNo(user.getCertificateId()));
        user.setPassword(EncryptUtil.encryptMD5(user.getPassword()));
        boolean authFlag = StringUtils.isEmpty(user.getIdPhotoBgUrl()) &&
                StringUtils.isEmpty(user.getIdPhotoFrontUrl());
        user.setCertification(authFlag ? CommonEnum.NOT_AUTH.getCode() : CommonEnum.AUDITING.getCode());

        Integer result = userMapper.register(user);
        if (result == 0) {
            return new DataRet("ERROR", "注册失败");
        }
        //存储证件照片
        saveIdPhotoImage(user.getId(), user.getIdPhotoFrontUrl(), user.getIdPhotoBgUrl());
        //绑定商户角色
        bindRole(user);
        //将用户手机号码作为加密字符串回传
        String tokenStr = EncryptUtil.aesEncrypt(user.getMobile(), "ScorpionMall8888");
        user.setToken(tokenStr);
        return new DataRet<>("注册成功");
    }


    @Override
    public DataRet updateUserInfo(User user) {
        if (user.getId() == null) {
            return new DataRet<>("param_error", "id不能为空");
        }
        User localUser = userMapper.findById(user.getId());
        int result;
        if (CommonEnum.SELLER.getCode().equals(localUser.getUserType())) {
            //商家只能修改部分信息
            result = userMapper.updateSellerInfo(user);
        } else {
            //管理员可以修改所有信息
            user.setAge(CommonUtil.getAgeByIdCardNo(user.getCertificateId()));
            user.setSex(CommonUtil.getGenderByIdCardNo(user.getCertificateId()));
            result = userMapper.updateAdminInfo(user);
        }
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet<>("update_error", "修改失败");
    }

    /**
     * 修改商户个人资料（需要后台系统认证的信息）
     *
     * @param user User
     * @return DataRet
     */
    @Override
    public DataRet updateUserAuthInfo(User user) {
        if (user.getId() == null) {
            return new DataRet<>("param_error", "id不能为空");
        }
        user.setAge(CommonUtil.getAgeByIdCardNo(user.getCertificateId()));
        user.setSex(CommonUtil.getGenderByIdCardNo(user.getCertificateId()));
        int result = userMapper.updateSellerAuthInfo(user);
        saveIdPhotoImage(user.getId(), user.getIdPhotoFrontUrl(), user.getIdPhotoBgUrl());
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet<>("update_error", "修改失败");
    }

    @Override
    public DataRet logout(String mobile) {
        return new DataRet<>("退出登录成功");
    }

    /**
     * 修改密码
     *
     * @param userId   Long
     * @param password password
     * @return DataRet
     * @throws Exception e
     */
    @Override
    public DataRet updatePassword(Long userId, String password) throws Exception {
        if (userId == null || StringUtils.isEmpty(password)) {
            return new DataRet("param_error", "参数有误");
        }
        password = EncryptUtil.encryptMD5(password);
        int result = userMapper.updatePassword(userId, password);
        if (result > 0) {
            return new DataRet<>("密码修改成功");
        }
        return new DataRet("update_error", "密码修改失败");
    }

    /**
     * 查询用户列表
     *
     * @param pageNo        当前页
     * @param pageSize      每页条数
     * @param startDate     注册时间
     * @param endDate       注册时间
     * @param searchKey     模糊查询信息
     * @param userType      用户类型
     * @param certification 用户认证
     * @return PageResult
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize,
                                      String startDate, String endDate,
                                      String searchKey, String userType,
                                      String certification) {
        if (pageNo == null || pageSize == null) {
            return new PageResult();
        }
        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        if (StringUtils.isEmpty(startDate)) {
            startDate = null;
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = null;
        }
        Page<User> page = userMapper.findByCondition(startDate, endDate, searchKey, userType, certification);
        return new PageResult<>(page);
    }

    @Override
    public DataRet findById(Long id) {
        if (id == null) {
            return new DataRet("param_error", "参数有误");
        }
        User user = userMapper.findById(id);
        if (user == null) {
            return new DataRet("not_found", "查无结果");
        }
        return new DataRet<>(user);
    }

    /**
     * 实名认证审核
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
    @Override
    public DataRet auditSeller(Long sellerId, Long operateId, String certification, String reason) {
        if (operateId == null || sellerId == null || StringUtils.isEmpty(certification)) {
            return new DataRet("param_error", "参数有误");
        }
        int validResult = userMapper.validAdmin(operateId);
        if (validResult == 0) {
            return new DataRet("audit_error", "只有管理员用户才能审核");
        }
        if (certification.equals(CommonEnum.NOT_PASS_AUTH.getCode()) && StringUtils.isEmpty(reason)) {
            return new DataRet("audit_error", "请填写未通过原因");
        }
        int result = userMapper.auditSeller(sellerId, certification, reason);
        if (result > 0) {
            return new DataRet<>("审核成功");
        }
        return new DataRet("audit_error", " 审核失败");
    }

    /**
     * 校验账号是否是管理员账号
     *
     * @param userId Long
     * @return DataRet
     */
    @Override
    public DataRet validAdmin(Long userId) {
        int result = userMapper.validAdmin(userId);
        if (result > 0) {
            return new DataRet<>(result);
        }
        return new DataRet<>("error", "非管理员账号");
    }

    /**
     * 存储证件照片
     *
     * @param userId      Long
     * @param frontImgUrl String
     * @param bgImgUrl    String
     */
    private void saveIdPhotoImage(Long userId, String frontImgUrl, String bgImgUrl) {
        if (!StringUtils.isEmpty(frontImgUrl)) {
            MallImg mallImage = new MallImg();
            mallImage.setTargetId(userId);
            mallImage.setUrl(frontImgUrl);
            mallImage.setType(CommonEnum.TYPE_IMG_ID_PHOTO.getCode());
            fileClient.add(mallImage);
        }
        if (!StringUtils.isEmpty(bgImgUrl)) {
            MallImg mallImage = new MallImg();
            mallImage.setUrl(bgImgUrl);
            mallImage.setTargetId(userId);
            mallImage.setType(CommonEnum.TYPE_IMG_ID_PHOTO.getCode());
            fileClient.add(mallImage);
        }
    }

    /**
     * 绑定商户角色
     *
     * @param user 用户id（商户id）
     */
    private void bindRole(User user) {
        if (!CommonEnum.USER_ORDINARY.getCode().equals(user.getUserType())) {
            return;
        }
        DataRet<SysRole> roleResult = roleClient.findSellerRole();
        if (roleResult.isSuccess() && roleResult.getBody() != null) {
            roleClient.userBindRole(user.getId(), roleResult.getBody().getId());
        }
    }
}
