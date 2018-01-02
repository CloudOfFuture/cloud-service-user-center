package com.kunlun.api.controller;

import com.kunlun.api.service.PointService;
import com.kunlun.entity.Point;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/22.
 */
@RestController
@RequestMapping("point")
public class PintController {


    private Logger LOGGER = Logger.getLogger("日志");

    @Autowired
    private PointService pointService;

    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openid     openid
     * @return
     */
    @GetMapping("/checkPoint")
    public DataRet<String> checkPoint(@RequestParam(value = "pointValue") Integer pointValue,
                              @RequestParam(value = "openid") String openid) {
        LOGGER.info("积分检查");
        return pointService.checkPoint(pointValue, openid);
    }

    /**
     * 操作用户积分（增,减）
     * @param point
     * @param userId
     * @return
     */
    @PostMapping("/updatePoint")
    public DataRet<String> updatePoint(@RequestParam(value = "point") Integer point,
                                       @RequestParam(value = "userId") String userId){
        return pointService.updatePoint(point,userId);
    }

    /**
     * 根据userId查询用户积分
     * @param userId
     * @return
     */
    @GetMapping("/findPointByUserId")
    public DataRet<Point> findPointByUserId(String userId){
        return pointService.findPointByUserId(userId);
    }

}
