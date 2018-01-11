package com.kunlun.api.service;

import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.io.IOException;


/**
 * @author by hws
 * @created on 2017/12/25.
 */
public interface DeliveryService {

    /**
     * 根据收获地址id查找详情
     *
     * @param deliveryId
     * @return
     */
    DataRet<Delivery> findById(Long deliveryId);

    /**
     * 用户收货地址分页
     *
     * @param wxCode   String 小程序用户id
     * @param pageNo   Integer
     * @param userId   Long  商户（卖家id）
     * @param pageSize Integer
     * @return PageResult
     */
    PageResult findByUserId(String wxCode, Long userId, Integer pageNo, Integer pageSize);


    /**
     * 新增收货地址
     *
     * @param delivery
     * @return
     */
    DataRet<String> add(Delivery delivery);


    /**
     * 修改收货地址
     *
     * @param delivery
     * @return
     */
    DataRet<String> update(Delivery delivery);


    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    DataRet<String> deleteById(Long id);

    /**
     * 设置默认地址
     *
     * @param id
     * @param wxCode
     * @param userId
     * @return
     */
    DataRet<String> defaultAddress(Long id, String wxCode, Long userId);


    /**
     * 获取默认地址
     *
     * @param wxCode
     * @param userId
     * @return
     */
    DataRet<Delivery> getDefault(String wxCode, Long userId);
}
