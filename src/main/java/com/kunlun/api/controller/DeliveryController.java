package com.kunlun.api.controller;

import com.kunlun.api.service.DeliveryService;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@RestController
@RequestMapping("delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    /**
     * 根据收获地址id查找收获地址详情
     * @param id
     * @return
     */
    @GetMapping("findDetailById")
    private DataRet findDetailById(@RequestParam(value = "id") Long id){
        return deliveryService.findDetailById(id);
    }
}
