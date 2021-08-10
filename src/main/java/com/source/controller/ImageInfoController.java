package com.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.source.bean.ImageUpdateInfo;
import com.source.service.ImageUpdateInfoService;
import com.source.utils.QueryRequest;
import com.source.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/02/14:31
 */
@CrossOrigin
@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
@Slf4j
public class ImageInfoController {

    private final ImageUpdateInfoService infoService;

    @GetMapping
    public Result getAll(ImageUpdateInfo imageUpdateInfo, QueryRequest queryRequest, HttpServletRequest request) throws Exception {
        try {
            imageUpdateInfo.setUsername((String) request.getSession().getAttribute("username"));
            IPage<ImageUpdateInfo> images = infoService.findImages(imageUpdateInfo, queryRequest);
            return new Result().data(images);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("查询失败");
        }
    }

    @PutMapping
    public Result uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            ImageUpdateInfo imageUpdateInfo = new ImageUpdateInfo();
            imageUpdateInfo.setUsername((String) request.getSession().getAttribute("username"));
            imageUpdateInfo.setSystemBrowserInfo(request.getHeader("user-agent"));
            String s = infoService.uploadFile(file, imageUpdateInfo);
            return new Result().data(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping
    public Result deleteFile(ImageUpdateInfo imageUpdateInfo, HttpServletRequest request) throws Exception {
        try {
            imageUpdateInfo.setUsername((String) request.getSession().getAttribute("username"));
            infoService.deleteFile(imageUpdateInfo);
            return new Result().data("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
