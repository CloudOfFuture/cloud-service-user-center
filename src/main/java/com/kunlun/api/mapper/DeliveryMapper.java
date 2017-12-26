package com.kunlun.api.mapper;

import com.kunlun.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Mapper
public interface DeliveryMapper {

    /**
     * 根据收获地址id查找收获地址
     * @param id
     * @return
     */
    Delivery findDetailById(@RequestParam("id") Long id);

}
