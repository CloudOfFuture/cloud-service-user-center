package com.kunlun.api.service;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/22.
 */
public interface PointService {

    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openid     openid
     * @return
     */
    String checkPoint(Integer pointValue, String openid);
}
