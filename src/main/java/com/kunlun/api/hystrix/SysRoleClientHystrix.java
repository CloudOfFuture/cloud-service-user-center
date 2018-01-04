package com.kunlun.api.hystrix;

import com.kunlun.api.client.SysRoleClient;
import com.kunlun.entity.SysRole;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-04 09:36
 */
@Component
public class SysRoleClientHystrix implements SysRoleClient {
    @Override
    public DataRet<SysRole> findSellerRole() {
        return new DataRet<>("ERROR", "查询失败");
    }

    @Override
    public DataRet userBindRole(Long userId, Long roleId) {
        return new DataRet<>("ERROR", "绑定失败");
    }
}
