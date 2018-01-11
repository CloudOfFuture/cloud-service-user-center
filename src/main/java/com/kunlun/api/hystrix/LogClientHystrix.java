package com.kunlun.api.hystrix;

import com.kunlun.api.client.LogClient;
import com.kunlun.entity.PointLog;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-11 14:54
 */
@Component
public class LogClientHystrix implements LogClient {
    @Override
    public DataRet addPointLog(PointLog pointLog) {
        return new DataRet("ERROR", "请求失败");
    }
}
