package com.kunlun.api.client;

import com.kunlun.api.hystrix.SysRoleClientHystrix;
import com.kunlun.entity.SysRole;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-04 09:35
 */
@FeignClient(value = "cloud-service-common", fallback = SysRoleClientHystrix.class)
public interface SysRoleClient {

    /**
     * 查询商户角色
     *
     * @return SysRole
     */
    @GetMapping("/role/distribution/findSellerRole")
    DataRet<SysRole> findSellerRole();

    /**
     * 用户绑定角色
     *
     * @param userId Long
     * @param roleId Long
     * @return DataRet
     */
    @PostMapping("/role/distribution/user")
    DataRet userBindRole(@RequestParam("userId") Long userId,
                         @RequestParam("roleId") Long roleId);
}
