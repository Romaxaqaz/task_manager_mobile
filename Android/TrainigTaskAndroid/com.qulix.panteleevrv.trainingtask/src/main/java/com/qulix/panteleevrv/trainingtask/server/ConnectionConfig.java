package com.qulix.panteleevrv.trainingtask.server;

/**
 * Интерфейс для конфигурации соединения с сервером.
 *
 * @author Q-RPA
 */
public interface ConnectionConfig {

    /**
     * Получает установленный адресс сервера.
     */
    String getUrl();

    /**
     * Устанавливает адрес сервера.
     */
    void setUrl(String url);
}
