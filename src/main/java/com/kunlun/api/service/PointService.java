package com.kunlun.api.service;

import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.io.IOException;

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
     * @param openId     openId
     * @return
     */
    DataRet<String> checkPoint(Integer pointValue, String openId);

    /**
     * 操作用户积分
     *
     * @param point
     * @param userId
     * @return
     */
    DataRet<String> updatePoint(Integer point, String userId);

    /**
     * 根据用户Id查询用户积分
     *
     * @param userId
     * @return
     */
    DataRet<Point> findByUserId(String userId);
}
