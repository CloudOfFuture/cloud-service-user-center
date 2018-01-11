package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.api.mapper.DeliveryMapper;
import com.kunlun.entity.Delivery;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author by hws
 * @created on 2017/12/25.
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {


    @Autowired
    private DeliveryMapper deliveryMapper;

    /**
     * 根据收获地址id查找详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<Delivery> findById(Long id) {
        Delivery delivery = deliveryMapper.findById(id);
        if (delivery == null) {
            return new DataRet<>("Error", "查无此收货信息");
        }
        return new DataRet<>(delivery);
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
    @Override
    public PageResult findByUserId(String wxCode, Long userId, Integer pageNo, Integer pageSize) {
        if (StringUtil.isEmpty(wxCode) && userId == null) {
            return new PageResult("ERROR", "参数错误");
        }
        PageHelper.startPage(pageNo, pageSize);
        String dUserId;
        if (userId == null) {
            dUserId = WxUtil.getOpenId(wxCode);
        } else {
            dUserId = String.valueOf(userId);
        }
        if (dUserId == null) {
            return new PageResult<>();
        }
        Page<Delivery> page = deliveryMapper.findByUserId(dUserId);
        return new PageResult<>(page);
    }


    /**
     * 新增收货地址
     *
     * @param delivery Delivery
     * @return DataRet
     */
    @Override
    public DataRet<String> add(Delivery delivery) {
        if (StringUtil.isEmpty(delivery.getWxCode()) && StringUtil.isEmpty(delivery.getUserId())) {
            return new DataRet<>("ERROR", "参数错误");
        }
        //最新添加的地址为默认地址
        delivery.setDefaultAddress(CommonEnum.DEFAULT_ADDRESS.getCode());
        if (delivery.getUserId() == null) {
            String userId = WxUtil.getOpenId(delivery.getWxCode());
            delivery.setUserId(userId);
        }
        //判断该用户是否为第一次创建收货地址
        Integer result = deliveryMapper.add(delivery);
        if (result == 0) {
            return new DataRet<>("ERROR", "新增失败");
        }
        deliveryMapper.updateDefaultById(delivery.getId(), delivery.getUserId());
        return new DataRet<>("新增成功");
    }


    /**
     * 修改收货地址信息
     *
     * @param delivery Delivery
     * @return DataRet
     */
    @Override
    public DataRet<String> update(Delivery delivery) {
        if (delivery.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = deliveryMapper.update(delivery);
        if (result <= 0) {
            return new DataRet<>("ERROR", "修改失败");
        }
        return new DataRet<>("修改成功");
    }


    /**
     * 删除收货地址
     *
     * @param id Long
     * @return DataRet
     */
    @Override
    public DataRet<String> deleteById(Long id) {
        //查询收货地址是否为默认地址
        Delivery delivery = deliveryMapper.findById(id);
        Integer result = deliveryMapper.deleteById(id);
        if (result == 0) {
            return new DataRet<>("ERROR", "删除失败");
        }
        boolean defaultAddress = CommonEnum.DEFAULT_ADDRESS.getCode().equals(delivery.getDefaultAddress());
        if (!defaultAddress) {
            return new DataRet<>("删除成功");
        }
        //删除默认地址，设置下一个默认地址
        Page<Delivery> page = deliveryMapper.findByUserId(delivery.getUserId());
        if (page.size() == 0) {
            return new DataRet<>("删除成功");
        }
        Integer updateResult = deliveryMapper.updateDefaultAddress(page.get(0).getId());
        if (updateResult > 0) {
            return new DataRet<>("删除成功");
        }
        return new DataRet<>("ERROR", "设置默认地址失败");
    }


    /**
     * 设置默认地址
     *
     * @param id     Long
     * @param wxCode String
     * @return DataRet
     */
    @Override
    public DataRet<String> defaultAddress(Long id, String wxCode, Long userId) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        if (StringUtil.isEmpty(wxCode) && userId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        String dUserId;
        if (userId == null) {
            dUserId = WxUtil.getOpenId(wxCode);
        } else {
            dUserId = String.valueOf(userId);
        }
        Delivery delivery = deliveryMapper.findById(id);
        Integer result = deliveryMapper.updateDefaultAddress(id);
        if (result == 0) {
            return new DataRet<>("ERROR", "设置默认地址失败");
        }
        if (CommonEnum.UN_DEFAULT_ADDRESS.getCode().equals(delivery.getDefaultAddress())) {
            result = deliveryMapper.updateDefaultById(id, dUserId);
            if (result == 0) {
                return new DataRet<>("ERROR", "修改默认地址失败");
            }
        }
        return new DataRet<>("设置成功");
    }


    /**
     * 获取默认地址
     *
     * @param wxCode String
     * @return DataRet
     */
    @Override
    public DataRet<Delivery> getDefault(String wxCode, Long userId) {
        if (StringUtil.isEmpty(wxCode) && userId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        String dUserId;
        if (userId == null) {
            dUserId = WxUtil.getOpenId(wxCode);
        } else {
            dUserId = String.valueOf(userId);
        }
        Delivery delivery = deliveryMapper.getDefault(dUserId);
        if (delivery == null) {
            return new DataRet<>("ERROR", "查无结果");
        }
        return new DataRet<>(delivery);
    }
}
