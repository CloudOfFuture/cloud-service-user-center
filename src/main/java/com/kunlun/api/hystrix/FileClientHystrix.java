package com.kunlun.api.hystrix;

import com.kunlun.api.client.FileClient;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-27 02:31
 */
@Component
public class FileClientHystrix implements FileClient {
    @Override
    public DataRet add(MallImg mallImg) {
        return new DataRet("Error", "新增失败");
    }

    @Override
    public DataRet deleteByUrl(String url) {
        return new DataRet("Error", "删除失败");
    }

    @Override
    public DataRet findByUrl(String url) {
        return new DataRet("Error", "查询失败");
    }

    @Override
    public DataRet deleteById(Long id) {
        return new DataRet("Error", "删除失败");
    }
}
