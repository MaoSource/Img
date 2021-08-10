package com.source.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.source.bean.TImgLog;
import com.source.mapper.TImgLogMapper;
import com.source.service.TImgLogService;
import com.source.utils.AddressUtil;
import com.source.utils.Util;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/04/10:47
 */
@Service
public class TImgLogServiceImpl extends ServiceImpl<TImgLogMapper, TImgLog> implements TImgLogService{

    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     */
    @Override
    public void saveLoginLog(TImgLog loginLog) {
        loginLog.setLoginTime(new Date());
        String ip = Util.getHttpServletRequestIpAddress();
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }
}
