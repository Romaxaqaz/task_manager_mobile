package com.qulix.panteleevrv.trainingtask.server;

/**
 * Класс серверной ошибки.
 *
 * @author Q-RPA
 */
public class ServerException extends Exception {

    /**
     * Конструктор класса {@link ServerException}
     */
    public ServerException() {
    }

    /**
     * Конструктор класса {@link ServerException}
     *
     * @param message сообщение об ошибке
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * Конструктор класса {@link ServerException}
     *
     * @param message сообщение об ошибке
     * @param throwable причина вызова исключения
     */
    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
