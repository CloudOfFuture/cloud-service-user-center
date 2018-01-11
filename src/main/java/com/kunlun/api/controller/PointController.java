package com.kunlun.api.controller;

import com.kunlun.api.service.PointService;
import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
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


    private Logger LOGGER = Logger.getLogger("日志");

    @Autowired
    private PointService pointService;

    /**
     * 积分检查
     *
     * @param pointValue 使用积分
     * @param openId     openId
     * @return
     */
    @GetMapping("/checkPoint")
    public DataRet<String> checkPoint(@RequestParam(value = "pointValue") Integer pointValue,
                                      @RequestParam(value = "openId") String openId) {
        LOGGER.info("积分检查");
        return pointService.checkPoint(pointValue, openId);
    }

    /**
     * 操作用户积分（增,减）
     *
     * @param point
     * @param userId
     * @return
     */
    @PostMapping("/updatePoint")
    public DataRet<String> updatePoint(@RequestParam(value = "point") Integer point,
                                       @RequestParam(value = "userId") String userId) {
        return pointService.updatePoint(point, userId);
    }

    /**
     * 根据userId查询用户积分
     *
     * @param userId
     * @return
     */
    @GetMapping("/findPointByUserId")
    public DataRet<Point> findPointByUserId(@RequestParam(value = "userId") String userId) {
        return pointService.findPointByUserId(userId);
    }


    /**
     * 获取积分记录列表
     *
     * @param pageNo
     * @param pageSize
     * @param wxCode
     * @return
     */
    @GetMapping("/findPointLog")
    public PageResult findPointLog(@RequestParam(value = "pageNo") Integer pageNo,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   @RequestParam(value = "wxCode") String wxCode) {
        return pointService.findPointLog(pageNo, pageSize, wxCode);
    }

}
