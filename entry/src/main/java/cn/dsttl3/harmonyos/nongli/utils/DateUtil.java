package cn.dsttl3.harmonyos.nongli.utils;

import ohos.global.icu.text.SimpleDateFormat;

import java.util.Date;

/**
 * 时间处理类
 */
public class DateUtil {
    /**
     * 获取时间：时间戳
     */
    public long getDate(){
        return new Date().getTime();
    }

    /**
     * 获取时间：自动义格式
     * @param date_type 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public String getDateString(String date_type){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date_type);
        return simpleDateFormat.format(getDate());
    }

    /**
     * 获取年月日
     * @return
     */
    public String getYDT(){
        return getDateString("yyyy-MM-dd");
    }

    /**
     * 获取时分秒
     * @return
     */
    public String getTime(){
        return getDateString("HH:mm:ss");
    }

}
