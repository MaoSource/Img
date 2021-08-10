package com.source.utils;

import com.source.constant.ImgConstant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/02/11:52
 */
public class FilesUtils {

    public static Boolean files(String files){
        for(int i = 0; i< ImgConstant.IMG_ARRAY.length; i++) {
            // 判断单个类型文件的场合
            if (ImgConstant.IMG_ARRAY[i].equals(files.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
