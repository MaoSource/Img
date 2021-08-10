package com.source.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/08/04/10:47
 */
/**
    * 图床日志表
    */
@Data
@TableName(value = "t_img_log")
@NoArgsConstructor
@Slf4j
public class TImgLog implements Serializable {
    /**
     * id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "USERNAME")
    private String username;

    /**
     * 登录时间
     */
    @TableField(value = "LOGIN_TIME")
    private Date loginTime;

    /**
     * 登录地点
     */
    @TableField(value = "`LOCATION`")
    private String location;

    /**
     * IP地址
     */
    @TableField(value = "IP")
    private String ip;

    /**
     * 操作系统
     */
    @TableField(value = "`SYSTEM`")
    private String system;

    /**
     * 浏览器
     */
    @TableField(value = "BROWSER")
    private String browser;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "ID";

    public static final String COL_USERNAME = "USERNAME";

    public static final String COL_LOGIN_TIME = "LOGIN_TIME";

    public static final String COL_LOCATION = "LOCATION";

    public static final String COL_IP = "IP";

    public static final String COL_SYSTEM = "SYSTEM";

    public static final String COL_BROWSER = "BROWSER";

    public void setSystemBrowserInfo(String ua) {
        try {
            StringBuilder userAgent = new StringBuilder("[");
            userAgent.append(ua);
            userAgent.append("]");
            int indexOfMac = userAgent.indexOf("Mac OS X");
            int indexOfWindows = userAgent.indexOf("Windows NT");
            int indexOfIe = userAgent.indexOf("MSIE");
            int indexOfIe11 = userAgent.indexOf("rv:");
            int indexOfFirefox = userAgent.indexOf("Firefox");
            int indexOfSogou = userAgent.indexOf("MetaSr");
            int indexOfChrome = userAgent.indexOf("Chrome");
            int indexOfSafari = userAgent.indexOf("Safari");
            boolean isMac = indexOfMac > 0;
            boolean isWindows = indexOfWindows > 0;
            boolean isLinux = userAgent.indexOf("Linux") > 0;
            boolean containIe = indexOfIe > 0 || (isWindows && (indexOfIe11 > 0));
            boolean containFireFox = indexOfFirefox > 0;
            boolean containSogou = indexOfSogou > 0;
            boolean containChrome = indexOfChrome > 0;
            boolean containSafari = indexOfSafari > 0;
            String browser = "";
            if (containSogou) {
                if (containIe) {
                    browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                } else if (containChrome) {
                    browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
                }
            } else if (containChrome) {
                browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            } else if (containSafari) {
                int indexOfSafariVersion = userAgent.indexOf("Version");
                browser = "Safari "
                        + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
            } else if (containFireFox) {
                browser = userAgent.substring(indexOfFirefox, indexOfFirefox + "Firefox/xx".length());
            } else if (containIe) {
                if (indexOfIe11 > 0) {
                    browser = "IE 11";
                } else {
                    browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                }
            }
            String os = "";
            if (isMac) {
                os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
            } else if (isLinux) {
                os = "Linux";
            } else if (isWindows) {
                os = "Windows ";
                String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                        + "Windows NTx.x".length());
                switch (version.trim()) {
                    case "5.0":
                        os += "2000";
                        break;
                    case "5.1":
                        os += "XP";
                        break;
                    case "5.2":
                        os += "2003";
                        break;
                    case "6.0":
                        os += "Vista";
                        break;
                    case "6.1":
                        os += "7";
                        break;
                    case "6.2":
                        os += "8";
                        break;
                    case "6.3":
                        os += "8.1";
                        break;
                    case "10":
                        os += "10";
                    default:
                        break;
                }
            }
            this.system = os;
            this.browser = StringUtils.replace(browser, "/", " ");
        } catch (Exception e) {
            log.error("获取登录信息失败：{}", e.getMessage());
            this.system = "";
            this.browser = "";
        }

    }
}