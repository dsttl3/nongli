package cn.dsttl3.harmonyos.nongli;

import cn.dsttl3.harmonyos.nongli.bean.wandate.WanDate;
import cn.dsttl3.harmonyos.nongli.slice.MainAbilitySlice;
import cn.dsttl3.harmonyos.nongli.utils.DateUtil;
import cn.dsttl3.harmonyos.nongli.utils.WanUtil;
import cn.dsttl3.harmonyos.nongli.widget.controller.*;
import com.google.gson.Gson;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.FormException;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentProvider;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbility extends Ability {
    private static final int INVALID_FORM_ID = -1;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, MainAbility.class.getName());
    private String topWidgetSlice;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        if (intentFromWidget(intent)) {
            topWidgetSlice = getRoutePageSlice(intent);
            if (topWidgetSlice != null) {
                setMainRoute(topWidgetSlice);
            }
        }
        stopAbility(intent);
    }

    @Override
    protected ProviderFormInfo onCreateForm(Intent intent) {
        HiLog.info(TAG, "onCreateForm");
        String date = new DateUtil().getDateString("yyyy-M-d");
        String wan = new WanUtil().getWan(date);
        Gson gson = new Gson();
        WanDate wanDate = gson.fromJson(wan, WanDate.class);
        ProviderFormInfo formInfo = new ProviderFormInfo(ResourceTable.Layout_form_grid_pattern_widget_2_2, this);
        ComponentProvider componentProvider = new ComponentProvider();
        componentProvider.setText(ResourceTable.Id_lunarYear, wanDate.getResult().getData().getLunarYear());
        componentProvider.setText(ResourceTable.Id_lunar, wanDate.getResult().getData().getLunar());
        componentProvider.setText(ResourceTable.Id_weekday, wanDate.getResult().getData().getWeekday());
        componentProvider.setText(ResourceTable.Id_date, date);
        formInfo.mergeActions(componentProvider);
        return formInfo;
    }

    @Override
    protected void onUpdateForm(long formId) {
        HiLog.info(TAG, "onUpdateForm");
        super.onUpdateForm(formId);
        String date = new DateUtil().getDateString("yyyy-M-d");
        String wan = new WanUtil().getWan(date);
        Gson gson = new Gson();
        WanDate wanDate = gson.fromJson(wan, WanDate.class);
        ComponentProvider componentProvider = new ComponentProvider(ResourceTable.Layout_form_grid_pattern_widget_2_2, this);
        componentProvider.setText(ResourceTable.Id_lunarYear, wanDate.getResult().getData().getLunarYear());
        componentProvider.setText(ResourceTable.Id_lunar, wanDate.getResult().getData().getLunar());
        componentProvider.setText(ResourceTable.Id_weekday, wanDate.getResult().getData().getWeekday());
        componentProvider.setText(ResourceTable.Id_date, date);
        try {
            updateForm(formId,componentProvider);
        } catch (FormException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDeleteForm(long formId) {
        HiLog.info(TAG, "onDeleteForm: formId=" + formId);
        super.onDeleteForm(formId);
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        formControllerManager.deleteFormController(formId);
    }

    @Override
    protected void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "onTriggerFormEvent: " + message);
        super.onTriggerFormEvent(formId, message);
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        formController.onTriggerFormEvent(formId, message);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intentFromWidget(intent)) { // Only response to it when starting from a service widget.
            String newWidgetSlice = getRoutePageSlice(intent);
            if (topWidgetSlice == null || !topWidgetSlice.equals(newWidgetSlice)) {
                topWidgetSlice = newWidgetSlice;
                restart();
            }
        }
    }

    private boolean intentFromWidget(Intent intent) {
        long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        return formId != INVALID_FORM_ID;
    }

    private String getRoutePageSlice(Intent intent) {
        long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        if (formId == INVALID_FORM_ID) {
            return null;
        }
        FormControllerManager formControllerManager = FormControllerManager.getInstance(this);
        FormController formController = formControllerManager.getController(formId);
        if (formController == null) {
            return null;
        }
        Class<? extends AbilitySlice> clazz = formController.getRoutePageSlice(intent);
        if (clazz == null) {
            return null;
        }
        return clazz.getName();
    }
}
