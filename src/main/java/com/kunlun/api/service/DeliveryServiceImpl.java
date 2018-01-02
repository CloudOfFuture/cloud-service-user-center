package com.kunlun.api.service;

import com.kunlun.api.mapper.DeliveryMapper;
import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryMapper deliveryMapper;

    /**
     * 根据收获地址id查找详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<Delivery> findDetailById(Long id) {

        Delivery delivery = deliveryMapper.findDetailById(id);
        if(delivery==null){
            return new DataRet("Error","查无此收货信息");
        }
        return new DataRet<>(delivery);
    }

    /**
     * 根据id校验收货地址有效性
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<Delivery> check(Long id) {
        Delivery delivery = deliveryMapper.findDetailById(id);
        if(delivery==null){
            return new DataRet<>("ERROR","查无此收货地址");
        }
        return new DataRet<>(delivery);
    }
}
