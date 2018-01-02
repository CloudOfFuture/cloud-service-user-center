package com.kunlun.api.service;

import com.kunlun.api.mapper.PointMapper;
import com.kunlun.entity.Point;
import com.kunlun.result.DataRet;
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


    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openid     openid
     * @return
     */
    @Override
    public DataRet<String> checkPoint(Integer pointValue, String openid) {
        Point point = pointMapper.findByOpenid(openid);
        if (null == point) {
            if (pointValue > 0) {
                return new DataRet<>("Error","没有可使用的积分");
            }
        } else if (pointValue > point.getPoint()) {
            return new DataRet<>("Error","积分不足");
        }
        return new DataRet<>("正常");
    }

    /**
     * 操作用户积分
     *
     * @param point
     * @param userId
     * @return
     */
    @Override
    public DataRet<String> updatePoint(Integer point, String userId) {
        Integer result = pointMapper.updatePoint(userId,point);
        if(result<=0){
            return new DataRet<>("ERROR","修改用户积分失败");
        }
        return new DataRet<>("积分操作成功");
    }

    /**
     * 根据用户Id查询用户积分
     *
     * @param userId
     * @return
     */
    @Override
    public DataRet<Point> findPointByUserId(String userId) {
        Point point = pointMapper.findByOpenid(userId);
        if(point == null){
            return new DataRet<>("ERROR","查询积分失败");
        }
        return new DataRet<>(point);
    }


}
