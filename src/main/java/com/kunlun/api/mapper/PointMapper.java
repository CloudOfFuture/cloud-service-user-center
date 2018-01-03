package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Point;
import com.kunlun.entity.PointLog;
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

    /**
     * 操作用户积分
     * @param userId
     * @param point
     * @return
     */
    Integer updatePoint(@Param("userId") String userId,
                        @Param(("point")) Integer point);


    /**
     * 获取积分列表
     *
     * @param userId
     * @return
     */
    Page<PointLog> findByUserId(@Param("userId") String userId);
}
