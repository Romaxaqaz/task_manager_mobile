package com.qulix.panteleevrv.trainingtask.client.ui;

/**
 * Интерфейс для реализации обработки исключения
 *
 * @author Q-RPA
 */
public interface ExceptionHandler {

    /**
     * Метод для обработки исключения.
     *
     * @param exception возникшее исключение
     */
    void handle(Exception exception);
}
