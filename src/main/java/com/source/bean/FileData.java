package com.source.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/03/17:10
 */
public class FileData {

    private Integer id;

    @JSONField(serialize = false)
    private MultipartFile file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
