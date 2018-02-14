package com.qulix.panteleevrv.trainingtask.server;

/**
 * Класс исключения в случае невозможности подключения к серверу.
 *
 * @author Q-RPA
 */
public class ConnectionException extends ServerException {

    /**
     * Конструктор класса {@link ConnectionException}
     */
    public ConnectionException() {
    }

    /**
     * Конструктор класса {@link ConnectionException}
     *
     * @param message сообщение об ошибке
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Конструктор класса {@link ConnectionException}
     *
     * @param message сообщение об ошибке
     * @param throwable причина вызова исключения
     */
    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
