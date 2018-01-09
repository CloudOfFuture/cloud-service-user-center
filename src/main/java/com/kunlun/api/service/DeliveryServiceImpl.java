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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * @param deliveryId
     * @return
     */
    @Override
    public DataRet<Delivery> findDetailById(Long deliveryId) {

        Delivery delivery = deliveryMapper.findDetailById(deliveryId);
        if (delivery == null) {
            return new DataRet("Error", "查无此收货信息");
        }
        return new DataRet<>(delivery);
    }


    /**
     * 用户收货地址分页
     *
     * @param wxCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findByWxCode(String wxCode, Integer pageNo, Integer pageSize) {
        if (StringUtil.isEmpty(wxCode)) {
            return new PageResult("ERROR", "参数错误");
        }
        PageHelper.startPage(pageNo, pageSize);
        String userId = WxUtil.getOpenId(wxCode);
        Page<Delivery> page = deliveryMapper.findByWxCode(userId);
        return new PageResult(page);
    }


    /**
     * 新增收货地址
     *
     * @param delivery
     * @return
     */
    @Override
    public DataRet<String> add(Delivery delivery) {
        if (StringUtil.isEmpty(delivery.getWxCode())) {
            return new DataRet<>("ERROR", "参数错误");
        }
        String userId = WxUtil.getOpenId(delivery.getWxCode());
        delivery.setUserId(userId);
        //判断该用户是否为第一次创建收货地址
        Page<Delivery> page = deliveryMapper.findByWxCode(userId);
        if (page.size() <= 0) {
            Integer result = deliveryMapper.add(delivery);
            if (result > 0) {
                return new DataRet<>("新增成功");
            }
        }
        Integer result = deliveryMapper.add(delivery);
        if (result <= 0) {
            return new DataRet<>("ERROR", "新增失败");
        }
        Integer defaultResult = deliveryMapper.updateDefaultById(delivery.getId(), userId);
        if (defaultResult <= 0) {
            return new DataRet<>("ERROR", "修改默认地址失败");
        }
        return new DataRet<>("新增成功");
    }


    /**
     * 修改收货地址信息
     *
     * @param delivery
     * @return
     */
    @Override
    public DataRet<String> update(Delivery delivery) {
        if (delivery.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        if (delivery == null) {
            return new DataRet<>("ERROR", "查无此物");
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
     * @param id
     * @return
     */
    @Override
    public DataRet<String> delete(Long id) {
        //查询收货地址是否为默认地址
        Delivery delivery = deliveryMapper.findDetailById(id);
        Integer result = deliveryMapper.deleteById(id);
        if (result <= 0) {
            return new DataRet<>("ERROR", "删除失败");
        }
        if (CommonEnum.DEFAULT_ADDRESS.getCode().equals(delivery.getDefaultAddress())) {
            Page<Delivery> page = deliveryMapper.findByWxCode(delivery.getUserId());
            if (page.size() > 0) {
                Integer updateResult = deliveryMapper.updateDefaultAddress(page.get(0).getId());
                if (updateResult <= 0) {
                    return new DataRet<>("ERROR", "设置默认地址失败");
                }
                return new DataRet<>("删除成功");
            }
            return new DataRet<>("删除成功");
        }
        return new DataRet<>("删除成功");
    }


    /**
     * 设置默认地址
     *
     * @param id
     * @param wxCode
     * @return
     */
    @Override
    public DataRet<String> defaultAddress(Long id, String wxCode) {
        if (StringUtil.isEmpty(wxCode)) {
            return new DataRet<>("ERROR", "参数错误");
        }
        String userId = WxUtil.getOpenId(wxCode);
        Delivery delivery = deliveryMapper.findDetailById(id);
        Integer result = deliveryMapper.updateDefaultAddress(id);
        if (result <= 0) {
            return new DataRet<>("ERROR", "设置默认地址失败");
        }
        if (CommonEnum.UN_DEFAULT_ADDRESS.getCode().equals(delivery.getDefaultAddress())) {
            Integer defaultResult = deliveryMapper.updateDefaultById(id, userId);
            if (defaultResult <= 0) {
                return new DataRet<>("ERROR", "修改默认地址失败");
            }
        }
        return new DataRet<>("设置成功");
    }


    /**
     * 获取默认地址
     *
     * @param wxCode
     * @return
     */
    @Override
    public DataRet<Delivery> getDefault(String wxCode) {
        if (StringUtil.isEmpty(wxCode)) {
            return new DataRet<>("ERROR", "参数错误");
        }
        String userId = WxUtil.getOpenId(wxCode);
        Delivery delivery = deliveryMapper.getDefault(userId);
        if (delivery == null) {
            return new DataRet<>("ERROR", "查无结果");
        }
        return new DataRet<>(delivery);
    }
}
