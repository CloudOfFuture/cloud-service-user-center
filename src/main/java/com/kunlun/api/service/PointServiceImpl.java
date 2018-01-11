package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.api.client.LogClient;
import com.kunlun.api.mapper.PointMapper;
import com.kunlun.constant.Constant;
import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.CommonUtil;
import com.kunlun.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/22.
 */
@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private LogClient logClient;


    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openid     openid
     * @return DataRet
     */
    @Override
    public DataRet<String> checkPoint(Integer pointValue, String openid) {
        if (StringUtil.isEmpty(openid) || pointValue == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Point point = pointMapper.findByUserId(openid);
        if (null == point || point.getPoint() <= 0) {
            return new DataRet<>("Error", "没有可使用的积分");
        }
        if (Math.abs(pointValue) > point.getPoint()) {
            return new DataRet<>("Error", "积分不足");
        }
        return new DataRet<>("正常");
    }

    /**
     * 操作用户积分
     *
     * @param operatePoint Integer(+ ,-)
     * @param userId       String
     * @return DataRet
     */
    @Override
    public DataRet<String> updatePoint(Integer operatePoint, String userId) {
        if (StringUtil.isEmpty(userId) || operatePoint == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Point point = pointMapper.findByUserId(userId);
        if (operatePoint < 0 && Math.abs(operatePoint) > point.getPoint()) {
            return new DataRet<>("ERROR", "积分不足");
        }
        int currentPoint = point.getPoint() + operatePoint;
        point.setPoint(currentPoint);
        if (currentPoint < Constant.WX_POINT_LEVEL1) {
            point.setLevel(1);
            point.setLevelName("白银");
        } else if (currentPoint < Constant.WX_POINT_LEVEL2) {
            point.setLevel(2);
            point.setLevelName("黄金");
        } else if (currentPoint < Constant.WX_POINT_LEVEL3) {
            point.setLevel(3);
            point.setLevelName("铂金");
        } else {
            point.setLevel(4);
            point.setLevelName("钻石");
        }
        int result = pointMapper.update(point);
        if (result == 0) {
            return new DataRet<>("ERROR", "积分修改失败");
        }
        PointLog pointLog = CommonUtil.constructPointLog(userId, operatePoint, point.getPoint());
        logClient.addPointLog(pointLog);
        return new DataRet<>("积分修改成功");
    }

    /**
     * 根据用户Id查询用户积分
     *
     * @param userId String
     * @return DataRet
     */
    @Override
    public DataRet<Point> findByUserId(String userId) {
        if (StringUtil.isEmpty(userId)) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Point point = pointMapper.findByUserId(userId);
        if (point == null) {
            point = new Point();
            point.setUserId(userId);
            point.setLevelName("白银");
            int result = pointMapper.add(point);
            if (result == 0) {
                return new DataRet<>("ERROR", "查询失败");
            }
        }
        return new DataRet<>(point);
    }
}
