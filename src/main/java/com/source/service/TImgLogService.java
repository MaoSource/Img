package com.source.service;

import com.source.bean.TImgLog;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/04/10:47
 */
public interface TImgLogService extends IService<TImgLog>{

        /**
         * 保存登录日志
         *
         * @param loginLog 登录日志
         */
        void saveLoginLog(TImgLog loginLog);

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
