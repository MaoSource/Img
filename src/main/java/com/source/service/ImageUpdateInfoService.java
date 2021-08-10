package com.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.source.bean.ImageUpdateInfo;
import com.source.utils.QueryRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/02/10:31
 */
public interface ImageUpdateInfoService extends IService<ImageUpdateInfo>{

    /**
     * 上传图片
     * @param file 文件
     * @param imageUpdateInfo 文件对象
     * @return 返回文件信息
     * @throws Exception 错误信息
     */
    String uploadFile(MultipartFile file, ImageUpdateInfo imageUpdateInfo) throws Exception;

    /**
     * 删除文件
     * @param imageUpdateInfo 文件对象
     * @return 返回
     * @throws Exception 错误信息
     */
    int deleteFile(ImageUpdateInfo imageUpdateInfo) throws Exception;

    /**
     * 修改文件信息
     * @param imageUpdateInfo 文件对象
     * @throws Exception 错误信息
     */
    void updateFile(ImageUpdateInfo imageUpdateInfo) throws Exception;

    /**
     * 查询所有
     * @param imageUpdateInfo 文件对象
     * @param request request对象
     * @return 返回IPage集合
     */
    IPage<ImageUpdateInfo> findImages(ImageUpdateInfo imageUpdateInfo, QueryRequest request);

}
