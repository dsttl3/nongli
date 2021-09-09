package cn.dsttl3.harmonyos.nongli.slice;

import cn.dsttl3.harmonyos.nongli.ResourceTable;
import cn.dsttl3.harmonyos.nongli.bean.wandate.WanDate;
import cn.dsttl3.harmonyos.nongli.utils.DateUtil;
import cn.dsttl3.harmonyos.nongli.utils.WanUtil;
import com.google.gson.Gson;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_layout_nong);
        Text lunarYear = (Text) findComponentById(ResourceTable.Id_l_lunarYear);
        Text lunar = (Text) findComponentById(ResourceTable.Id_l_lunar);
        Text weekday = (Text) findComponentById(ResourceTable.Id_l_weekday);
        Text dateText = (Text) findComponentById(ResourceTable.Id_l_date);
        Text suit = (Text) findComponentById(ResourceTable.Id_l_suit);
        Text avoid = (Text) findComponentById(ResourceTable.Id_l_avoid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new DateUtil().getDateString("yyyy-M-d");
                String wan = new WanUtil().getWan(date);
                Gson gson = new Gson();
                WanDate wanDate = gson.fromJson(wan, WanDate.class);
                if(wanDate != null ){
                    getUITaskDispatcher().asyncDispatch(new Runnable() {
                        @Override
                        public void run() {
                            lunarYear.setText(wanDate.getResult().getData().getLunarYear());
                            lunar.setText(wanDate.getResult().getData().getLunar());
                            weekday.setText(wanDate.getResult().getData().getWeekday());
                            dateText.setText(date);
                            suit.setText(wanDate.getResult().getData().getSuit());
                            avoid.setText(wanDate.getResult().getData().getAvoid());
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
