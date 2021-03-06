package com.kunlun.api.controller;

import com.kunlun.api.service.PointService;
import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/22.
 */
@RestController
@RequestMapping("point")
public class PointController {
    @Autowired
    private PointService pointService;

    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openid     openid
     * @return
     */
    @PostMapping("/checkPoint")
    public DataRet<String> checkPoint(@RequestParam(value = "pointValue") Integer pointValue,
                                      @RequestParam(value = "openid") String openid) {
        return pointService.checkPoint(pointValue, openid);
    }

    /**
     * 操作用户积分（增,减）
     *
     * @param operatePoint
     * @param userId
     * @return
     */
    @PostMapping("/updatePoint")
    public DataRet<String> updatePoint(@RequestParam(value = "operatePoint") Integer operatePoint,
                                       @RequestParam(value = "userId") String userId) {
        return pointService.updatePoint(operatePoint, userId);
    }

    /**
     * 根据userId查询用户积分
     *
     * @param userId
     * @return
     */
    @GetMapping("/findPointByUserId")
    public DataRet<Point> findByUserId(@RequestParam(value = "userId") String userId) {
        return pointService.findByUserId(userId);
    }
}
