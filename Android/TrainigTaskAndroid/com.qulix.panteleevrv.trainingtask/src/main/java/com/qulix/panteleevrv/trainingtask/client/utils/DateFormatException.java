package com.qulix.panteleevrv.trainingtask.client.utils;

/**
 * Класс исключения при некорректном формате даты.
 *
 * @author Q-RPA
 */
public class DateFormatException extends Exception {

    /**
     * Конструктор класса {@link DateFormatException}
     */
    public DateFormatException() {
    }

    /**
     * Конструктор класса {@link DateFormatException}
     *
     * @param message сообщение об ошибке
     */
    public DateFormatException(String message) {
        super(message);
    }

    /**
     * Конструктор класса {@link DateFormatException}
     *
     * @param message сообщение об ошибке
     * @param throwable причина вызова исключения
     */
    public DateFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
