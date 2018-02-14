package com.qulix.panteleevrv.trainingtask.client.ui.settings;

import com.qulix.panteleevrv.trainingtask.model.Settings;

import android.content.Context;

/**
 * Класс для работы с настройками приложения.
 *
 * @author Q-RPA
 */
public class SettingsService {

    private static final String URL_KEY = "URL_KEY";
    private static final String COUNT_KEY = "COUNT_KEY";

    private SettingSharedPreferences sharedPreferences;
    private ApplicationProperties applicationProperties;
    private Settings settings;

    public SettingsService(Context context) {
        applicationProperties = new ApplicationProperties(context);
        sharedPreferences = new SettingSharedPreferences(context);

        initParams();
    }

    private void initParams() {
        String url = sharedPreferences.getValue(URL_KEY, applicationProperties.get(URL_KEY));
        int itemCount = Integer.parseInt(sharedPreferences.getValue(COUNT_KEY, applicationProperties.get(COUNT_KEY)));

        settings = new Settings(url, itemCount);
    }

    /**
     * Сохраняет настройки приложения.
     *
     * @param settings модель настроек {@link Settings}.
     */
    void saveSettings(Settings settings) {
        this.settings = settings;

        sharedPreferences.setValue(URL_KEY, settings.getUrl());
        sharedPreferences.setValue(COUNT_KEY, String.valueOf(settings.getItemsCount()));
    }

    /**
     * Получает настройки приложения {@link Settings}.
     *
     * @return возвращает модель настроек приложения.
     */
    public Settings getSettings() {
        return settings;
    }
}
