package com.source.configure;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.source.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2021/03/16/11:01
 * @Description: 自定义打印配置类
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN)
                + " | 耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : StringUtils.EMPTY;
    }
}
