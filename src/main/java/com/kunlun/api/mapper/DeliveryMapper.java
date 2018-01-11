package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Mapper
public interface DeliveryMapper {

    /**
     * 根据收获地址id查找收获地址
     *
     * @param id
     * @return
     */
    Delivery findById(@Param("id") Long id);


    /**
     * 新增收拾地址
     *
     * @param delivery
     * @return
     */
    Integer add(Delivery delivery);


    /**
     * 修改该用户其他的地址为非默认地址
     *
     * @param id
     * @param userId
     * @return
     */
    Integer updateDefaultById(@Param("id") Long id, @Param("userId") String userId);


    /**
     * 修改收货地址信息
     *
     * @param delivery
     * @return
     */
    Integer update(Delivery delivery);

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("id") Long id);

    /**
     * 更改为默认地址
     *
     * @param id
     * @return
     */
    Integer updateDefaultAddress(@Param("id") Long id);


    /**
     * 获取默认地址
     *
     * @param userId
     * @return
     */
    Delivery getDefault(@Param("userId") String userId);

    /**
     * 根据 userId查询列表
     *
     * @param userId
     * @return
     */
    Page<Delivery> findByUserId(@Param("userId") String userId);
}
