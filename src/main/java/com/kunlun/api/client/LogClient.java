package com.kunlun.api.client;

import com.kunlun.api.hystrix.LogClientHystrix;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-11 14:54
 */
@FeignClient(value = "cloud-service-log", fallback = LogClientHystrix.class)
public interface LogClient {

    @PostMapping("/log/add/pointLog")
    DataRet addPointLog(@RequestBody PointLog pointLog);
}
