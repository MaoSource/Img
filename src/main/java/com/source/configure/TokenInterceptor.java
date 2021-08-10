package com.source.configure;

import com.source.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/03/16:05
 */
@Configuration
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String username = (String) request.getSession().getAttribute("username");

        if (StringUtils.isNotEmpty(username))
            return true;

        String header = request.getHeader("token");

        log.info("token: {}", header);


        if (header == null) {
            throw new ServiceException("请先登录！");
        }

        request.getSession().setAttribute("username", header);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {

    }

}
