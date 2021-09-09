package cn.dsttl3.harmonyos.nongli.utils;

import java.io.IOException;

public class WanUtil {
    public String getWan(long date){
        String URL = "https://v.juhe.cn/calendar/day?date="+new DateUtil().getDateString("yyyy-M-d")+"&key=fd996afa04d8c480d9457be220cfedd7";
        try {
            return new OKUtil().getData(URL);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getWan(String date){
        String URL = "https://v.juhe.cn/calendar/day?date="+date+"&key=fd996afa04d8c480d9457be220cfedd7";
        try {
            return new OKUtil().getData(URL);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
