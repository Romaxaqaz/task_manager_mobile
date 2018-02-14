package com.qulix.panteleevrv.trainingtask.server;

/**
 * Класс конфигурации соединения с сервером
 *
 * @author Q-RPA
 */
public class ServerConnectionConfig implements ConnectionConfig {

    private String url;

    public ServerConnectionConfig(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
