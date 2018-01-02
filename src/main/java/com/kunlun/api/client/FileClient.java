package com.kunlun.api.client;

import com.kunlun.api.hystrix.FileClientHystrix;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-27 02:29
 */
@FeignClient(value = "cloud-service-common", fallback = FileClientHystrix.class)
public interface FileClient {
    /**
     * 图片添加
     *
     * @param mallImg 图片
     * @return DataRet
     */
    @PostMapping("/file/add")
    DataRet add(@RequestBody MallImg mallImg);

    /**
     * 图片删除
     *
     * @param url 图片url
     * @return DataRet
     */
    @PostMapping("/file/deleteByUrl")
    DataRet deleteByUrl(@RequestParam(value = "url") String url);


    /**
     * 根据url查询图片
     *
     * @param url 图片url
     * @return DataRet
     */
    @GetMapping("/file/findByUrl")
    DataRet findByUrl(@RequestParam(value = "url") String url);

    /**
     * 图片删除
     *
     * @param id 图片id
     * @return DataRet
     */
    @PostMapping("/file/deleteById")
    DataRet deleteById(@RequestParam(value = "id") Long id);
}