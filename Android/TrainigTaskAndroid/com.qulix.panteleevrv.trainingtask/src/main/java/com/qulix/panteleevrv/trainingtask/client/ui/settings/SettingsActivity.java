package com.qulix.panteleevrv.trainingtask.client.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.client.utils.TextUtils;
import com.qulix.panteleevrv.trainingtask.client.TrainingApplication;
import com.qulix.panteleevrv.trainingtask.model.Settings;

import static android.text.TextUtils.*;

/**
 * Страница для работы с настройками приложения.
 *
 * <p>Позволяет устанавливать адрес сервера и количество отображаемых элементов списков на страницах</p>
 *
 * @author Q-RPA
 */
public class SettingsActivity extends Activity {

    private TrainingApplication application;
    private SettingsService settingsService;

    private TextView url;
    private TextView itemsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings_app_header));

        application = (TrainingApplication) getApplicationContext();
        settingsService = application.getSettingsService();

        initView();
        setSettingsFields(settingsService);
    }

    private void initView() {
        url = (EditText) findViewById(R.id.url);
        itemsCount = (EditText) findViewById(R.id.itemsCount);

        Button saveSettings = (Button) findViewById(R.id.saveSettings);
        saveSettings.setOnClickListener(new SaveSettingsListener());
    }

    private void setSettingsFields(SettingsService settingsService) {
        url.setText(settingsService.getSettings().getUrl());
        itemsCount.setText(String.valueOf(settingsService.getSettings().getItemsCount()));
    }

    private void saveSetting() {
        SettingsValidator validator = new SettingsValidator(this);
        Settings settings =  validator.validate(url, itemsCount);
        if (settings != null) {
            settingsService.saveSettings(settings);
            updateSettings();
        } else {
            UiUtils.showToast(SettingsActivity.this, join(TextUtils.NEW_LINE, validator.getErrors()));
        }
    }

    private void updateSettings() {
        application.updateServerUrl(settingsService.getSettings().getUrl());
        UiUtils.showToast(SettingsActivity.this, getString(R.string.settings_saved));
        UiUtils.finishActivity(SettingsActivity.this, RESULT_OK);
    }

    private class SaveSettingsListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            saveSetting();
        }
    }
}
