package com.kunlun.api.controller;

import com.kunlun.api.service.DeliveryService;
import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    /**
     * 根据收货地址id查找收获地址详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public DataRet<Delivery> findById(@RequestParam(value = "id") Long id) {
        return deliveryService.findById(id);
    }

    /**
     * 用户收货地址分页
     *
     * @param wxCode   String 小程序用户id
     * @param pageNo   Integer
     * @param userId   Long  商户（卖家id）
     * @param pageSize Integer
     * @return PageResult
     */
    @GetMapping("/findByUserId")
    public PageResult findByUserId(@RequestParam(value = "wxCode", required = false) String wxCode,
                                   @RequestParam(value = "userId", required = false) Long userId,
                                   @RequestParam(value = "pageNo") Integer pageNo,
                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return deliveryService.findByUserId(wxCode, userId, pageNo, pageSize);
    }


    /**
     * 新增收货地址
     *
     * @param delivery
     * @return
     */
    @PostMapping("/add")
    public DataRet<String> add(@RequestBody Delivery delivery) {
        return deliveryService.add(delivery);
    }


    /**
     * 修改收货地址
     *
     * @param delivery
     * @return
     */
    @PostMapping("/update")
    public DataRet<String> update(@RequestBody Delivery delivery) {
        return deliveryService.update(delivery);
    }


    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public DataRet<String> deleteById(@RequestParam(value = "id") Long id) {
        return deliveryService.deleteById(id);
    }


    /**
     * 设置默认收货地址
     *
     * @param id
     * @return
     */
    @PostMapping("/defaultAddress")
    public DataRet<String> defaultAddress(@RequestParam(value = "id") Long id,
                                          @RequestParam(value = "wxCode", required = false) String wxCode,
                                          @RequestParam(value = "userId", required = false) Long userId) {
        return deliveryService.defaultAddress(id, wxCode, userId);
    }


    /**
     * 获取默认地址
     *
     * @param wxCode
     * @param userId
     * @return
     */
    @GetMapping("/getDefault")
    public DataRet<Delivery> getDefault(@RequestParam(value = "wxCode", required = false) String wxCode,
                                        @RequestParam(value = "userId", required = false) Long userId) {
        return deliveryService.getDefault(wxCode, userId);
    }
}
