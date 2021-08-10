package com.source.controller;

import com.source.bean.TImgLog;
import com.source.service.TImgLogService;
import com.source.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/04/10:47
 */
@CrossOrigin
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class ImgLogController {

    private final TImgLogService service;

    @GetMapping("success")
    public void loginSuccess(HttpServletRequest request) {
        TImgLog tImgLog = new TImgLog();
        tImgLog.setUsername((String) request.getSession().getAttribute("username"));
        tImgLog.setSystemBrowserInfo(request.getHeader("user-agent"));
        this.service.saveLoginLog(tImgLog);
    }

    @GetMapping("index")
    public Result index() {
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
        Long totalVisitCount = service.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        // 今日总反问量
        Long todayVisitCount = service.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = service.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        return new Result().data(data);
    }
}
