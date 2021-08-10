package com.source.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.source.bean.TImgLog;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/04/10:47
 */
public interface TImgLogMapper extends BaseMapper<TImgLog> {

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount();

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp();
}