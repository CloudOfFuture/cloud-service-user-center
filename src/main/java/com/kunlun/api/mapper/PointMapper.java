package com.kunlun.api.mapper;

import com.kunlun.entity.Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/22.
 */
@Mapper
public interface PointMapper {

    /**
     * 根据openid查询积分信息
     *
     * @param openid
     * @return
     */
    Point findByOpenid(@Param("openid") String openid);
    
}
