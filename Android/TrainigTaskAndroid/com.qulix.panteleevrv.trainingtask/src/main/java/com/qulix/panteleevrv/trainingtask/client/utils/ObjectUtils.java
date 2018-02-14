package com.qulix.panteleevrv.trainingtask.client.utils;

/**
 * Утилитный класс дял работы c объектами.
 *
 * @author Q-RPA
 */
public class ObjectUtils {

    /**
     * Проверяет объект на null с выбрасыванием исключения.
     *
     * @param object объект для проверки.
     * @param error сообщение об ошибке.
     * @exception NullPointerException в случае, если объект равен null
     */
    public static void checkNull(Object object, String error) {
        if (object == null) {
            throw new NullPointerException(error);
        }
    }
}
