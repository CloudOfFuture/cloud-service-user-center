package com.kunlun.api.service;

import com.kunlun.api.mapper.PointMapper;
import com.kunlun.entity.Point;
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
    public String checkPoint(Integer pointValue, String openid) {
        Point point = pointMapper.findByOpenid(openid);
        if (null == point) {
            return "没有可使用的积分";

        } else if (pointValue > point.getPoint()) {
            return "积分不足";
        }
        return null;
    }


}
