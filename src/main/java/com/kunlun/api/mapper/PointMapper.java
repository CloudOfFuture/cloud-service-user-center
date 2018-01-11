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
     * @param userId
     * @return
     */
    Point findByUserId(@Param("userId") String userId);

    /**
     * 操作用户积分
     *
     * @param point
     * @return
     */
    int update(Point point);

    /**
     * 新增积分记录
     *
     * @param point Point
     * @return int
     */
    int add(Point point);
}
