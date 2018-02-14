package com.qulix.panteleevrv.trainingtask.client.ui.settings;

import java.io.IOException;
import java.util.Properties;

import android.content.Context;

/**
 * Класс для работы с настройками из файла application.properties.
 *
 * <p>Данные настройки являются настройками по умолчанию</p>
 *
 * @author Q-RPA
 */
public class ApplicationProperties {

    private static final String PROPERTY_FILE = "application.properties";
    private Properties properties;

    /**
     * Конструктор класса {@link ApplicationProperties}
     *
     * @param context контекст данных
     */
    public ApplicationProperties(Context context) {
        properties = new Properties();
        try {
            properties.load(context.getAssets().open(PROPERTY_FILE));
        }
        catch (IOException exception) {
            throw new RuntimeException(String.format("File %s not found in assets folder!", PROPERTY_FILE), exception);
        }
    }

    /**
     * Получает значение из файла по ключу.
     *
     * <p>Параметры в файле хранятся в формате <b>KEY=VALUE</b></p>.
     *
     * @param key ключ параметра в файле.
     * @return возвращает значение параметра в строковом представлении.
     */
    public String get(String key) {
        return properties.getProperty(key);
    }
}
