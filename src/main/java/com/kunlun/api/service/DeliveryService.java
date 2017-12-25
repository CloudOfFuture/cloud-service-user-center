package com.kunlun.api.service;

import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
public interface DeliveryService {

    /**
     * 根据收获地址id查找详情
     * @param id
     * @return
     */
    DataRet findDetailById(Long id);
}
