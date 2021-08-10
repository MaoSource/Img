package com.source.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.source.bean.ImageUpdateInfo;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/02/10:31
 */
public interface ImageUpdateInfoMapper extends BaseMapper<ImageUpdateInfo> {

    /**
     * 通过IP获取当天上传总数
     * @param ip ip地址
     * @return 返回总和
     */
    Long findTodayUploadCount(String username);

    Long findIpTodayUploadCount(String ip);
}