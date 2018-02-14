package com.qulix.panteleevrv.trainingtask.server;

/**
 * Интерфейс обновления соединения с сервером
 *
 * @author Q-RPA
 */
public interface HttpConnection {

    /**
     * Обновляет соединение с сервером путем обновлениея url адреса
     *
     * @param url адрес сервера
     */
    void update(ConnectionConfig connectionConfig);
}
