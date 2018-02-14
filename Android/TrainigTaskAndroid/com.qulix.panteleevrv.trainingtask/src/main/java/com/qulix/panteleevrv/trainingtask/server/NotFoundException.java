package com.qulix.panteleevrv.trainingtask.server;

/**
 * Класс исключения указывающий, что запрашиваемая сущность не найдена.
 *
 * @author Q-RPA
 */
public class NotFoundException extends ServerException {

    /**
     * Конструктор класса {@link NotFoundException}
     */
    public NotFoundException() {
    }

    /**
     * Конструктор класса {@link NotFoundException}
     *
     * @param message сообщение об ошибке
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Конструктор класса {@link NotFoundException}
     *
     * @param message сообщение об ошибке
     * @param throwable причина вызова исключения
     */
    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
