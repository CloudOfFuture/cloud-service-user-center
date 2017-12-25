package com.kunlun.api.service;

import com.kunlun.api.mapper.DeliveryMapper;
import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DataRet findDetailById(Long id) {
        //检测传入的id值是否有误
        if(StringUtils.isNullOrEmpty(id.toString())){
            return new DataRet("Error","收货地址id有误");
        }
        Delivery delivery = deliveryMapper.findDetailById(id);
        if(delivery==null){
            return new DataRet("Error","查无此收货信息");
        }
        return new DataRet<>(delivery);
    }
}
