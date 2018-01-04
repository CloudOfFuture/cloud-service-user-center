package com.kunlun.api.service;

import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

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
     * @param wxCode     wxCode
     * @return
     */
    DataRet<String> checkPoint(Integer pointValue, String wxCode);

    /**
     * 操作用户积分
     * @param point
     * @param wxCode
     * @return
     */
    DataRet<String> updatePoint(Integer point,String wxCode);

    /**
     * 根据用户Id查询用户积分
     * @param wxCode
     * @return
     */
    DataRet<Point> findPointByUserId(String wxCode);



    /**
     * 获取积分记录
     *
     * @param pageNo
     * @param pageSize
     * @param wxCode
     * @return
     */
    PageResult findPointLog(Integer pageNo, Integer pageSize, String wxCode);
}
