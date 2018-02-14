package com.qulix.panteleevrv.trainingtask.client.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс для работы с настройкам приложения в локальном хранилище. Реализован на основе {@link SharedPreferences}.
 *
 * @author Q-RPA
 */
public class SettingSharedPreferences {

    private static final String LOCAL_STORAGE_FILE = "LOCAL_SETTINGS";

    private SharedPreferences sharedPreferences;

    public SettingSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(LOCAL_STORAGE_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Получает параметр из хранилища.
     *
     * @param key ключ параметра.
     * @param defaultValue значение по умолчанию.
     * @return возвращает значение параметра из хранилища.
     */
    public String getValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Сохраняет значение в хранилище.
     *
     * @param key ключ параметра.
     * @param value значение параметра.
     */
    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
