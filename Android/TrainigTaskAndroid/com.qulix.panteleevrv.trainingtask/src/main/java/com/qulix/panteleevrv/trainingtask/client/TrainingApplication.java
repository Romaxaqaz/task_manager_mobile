package com.qulix.panteleevrv.trainingtask.client;

import android.app.Application;

import com.qulix.panteleevrv.trainingtask.client.ui.settings.SettingsService;
import com.qulix.panteleevrv.trainingtask.server.ConnectionConfig;
import com.qulix.panteleevrv.trainingtask.server.HttpConnection;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerConnectionConfig;
import com.qulix.panteleevrv.trainingtask.server.StubServer;

/**
 * Вспомогательный класс приложения для установки настроек.
 *
 * @author Q-RPA
 */
public class TrainingApplication extends Application {

    private Server server;
    private SettingsService settingsService;
    private ConnectionConfig connectionConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        settingsService = new SettingsService(this);
        connectionConfig = new ServerConnectionConfig(settingsService.getSettings().getUrl());
        server = new StubServer(connectionConfig);
    }

    /**
     * Интерфейс взаимодействия с сервером.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Класс для работы с настройками приложения.
     */
    public SettingsService getSettingsService() {
        return settingsService;
    }

    /**
     * Обновляет url адрес сервера.
     * @param url адрес сервера.
     */
    public void updateServerUrl(String url) {
        connectionConfig.setUrl(url);
        ((HttpConnection) server).update(connectionConfig);
    }
}
