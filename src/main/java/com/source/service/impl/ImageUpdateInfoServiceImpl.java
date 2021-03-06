package com.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.source.bean.ImageUpdateInfo;
import com.source.constant.Constant;
import com.source.constant.ImgConstant;
import com.source.exception.ServiceException;
import com.source.mapper.ImageUpdateInfoMapper;
import com.source.minio.MinioTemplate;
import com.source.service.ImageUpdateInfoService;
import com.source.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/02/10:31
 * @Description: 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageUpdateInfoServiceImpl extends ServiceImpl<ImageUpdateInfoMapper, ImageUpdateInfo> implements ImageUpdateInfoService{

    private final ImageUpdateInfoMapper imageUpdateInfoMapper;

    private final MinioTemplate minioTemplate;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.endPoint}")
    private String baseUrl;

    @Value("${minio.imgUrl}")
    private String imgUrl;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String uploadFile(MultipartFile file, ImageUpdateInfo imageUpdateInfo) throws Exception {
        String ip = Util.getHttpServletRequestIpAddress();
        Long count = imageUpdateInfoMapper.findTodayUploadCount(imageUpdateInfo.getUsername());
        Long ipCount = imageUpdateInfoMapper.findIpTodayUploadCount(ip);
        if (count < 10 && ipCount < 30) {
            if (file == null || file.isEmpty()) {
                throw new ServiceException("?????????????????????");
            }
            // ?????????????????????   yyyyMMddHHmmss
            String s = DateUtil.formatFullTime(LocalDateTime.now());
            // ????????????
            String targetPath = bucketName;
            // ???????????????
            String originalFilename = file.getOriginalFilename();
            // ??????????????????
            assert originalFilename != null;
            String fileType = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
            // ??????????????????
            long size = file.getSize();
            // ??????????????????
            log.info("????????????:{}",fileType);
            if (!FilesUtils.files(fileType)) {
                throw new ServiceException("??????????????????");
            } else if (size/1024/1024 > 5) {
                throw new ServiceException("????????????????????????5M");
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            String fileName = uuid + "." + fileType;

            log.info("???????????????" + targetPath);
            log.info("????????????" + fileName);
            log.info("???????????????" + size);
            // ????????????
            String desc = UUID.randomUUID().toString().replaceAll("-", "s");
            String url = imgUrl + bucketName + ImgConstant.SEPARATOR + s + ImgConstant.SEPARATOR + fileName;
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            ImageUpdateInfo img = ImageUpdateInfo.builder()
                    .username(imageUpdateInfo.getUsername())
                    .fileName(fileName)
                    .type(fileType)
                    .url(url)
                    .md5(md5)
                    .filesize((int) size)
                    .status("0")
                    .createTime(LocalDateTime.now())
                    .desc(desc)
                    .ip(ip)
                    .system(imageUpdateInfo.getSystem())
                    .browser(imageUpdateInfo.getBrowser()).build();
            log.info("???????????????");
            log.info(img.toString());
            save(img);
            try {
                minioTemplate.putObject(targetPath,s + ImgConstant.SEPARATOR + fileName,file);
                log.info(fileName + " ????????????!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("??????????????????????????????,?????????????????????");
            }
            count++;
            return "??????????????????????????????" + (10 - count) + "???";
        }
        throw new ServiceException("????????????????????????");
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int deleteFile(ImageUpdateInfo imageUpdateInfo) throws Exception {
        try {
            // ?????????????????????   yyyyMMddHH
            String s = DateUtil.formatFullTime(imageUpdateInfo.getCreateTime());
            minioTemplate.removeObject(bucketName, s + ImgConstant.SEPARATOR + imageUpdateInfo.getFileName());
            imageUpdateInfo.setStatus("1");
            updateById(imageUpdateInfo);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("????????????");
        }

    }

    @Override
    public void updateFile(ImageUpdateInfo imageUpdateInfo) throws Exception {

    }

    @Override
    public IPage<ImageUpdateInfo> findImages(ImageUpdateInfo imageUpdateInfo, QueryRequest request) {
        QueryWrapper<ImageUpdateInfo> queryWrapper = new QueryWrapper<>();
        String ip = Util.getHttpServletRequestIpAddress();

        log.info("IP:{}",ip);
        int i = ip.lastIndexOf(".");
        String substring = ip.substring(0, i);
//        imageUpdateInfo.setIp(substring);
        imageUpdateInfo.setStatus("0");
        if (StringUtils.isNotBlank(imageUpdateInfo.getUsername())) {
            queryWrapper.lambda().eq(ImageUpdateInfo::getUsername, imageUpdateInfo.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(imageUpdateInfo.getDesc())) {
            imageUpdateInfo.setIp(null);
            queryWrapper.lambda().eq(ImageUpdateInfo::getDesc, imageUpdateInfo.getDesc());
        }
        if (StringUtils.isNotBlank(imageUpdateInfo.getIp())){
            queryWrapper.lambda().like(ImageUpdateInfo::getIp, imageUpdateInfo.getIp());
        }
        if (StringUtils.isNotBlank(imageUpdateInfo.getStatus())){
            queryWrapper.lambda().eq(ImageUpdateInfo::getStatus, imageUpdateInfo.getStatus());
        }
        Page<ImageUpdateInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", Constant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }
}
