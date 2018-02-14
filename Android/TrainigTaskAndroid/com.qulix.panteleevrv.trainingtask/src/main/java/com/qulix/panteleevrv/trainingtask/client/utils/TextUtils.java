package com.qulix.panteleevrv.trainingtask.client.utils;

import android.widget.TextView;

/**
 * Утилитный класс для работы с текстом.
 *
 * @author Q-RPA
 */
public class TextUtils {

    /**
     * Символ перехода на новую строку
     */
    public static final String NEW_LINE = "\n";

    /**
     * Получает содержимое TextView c удалением незначащих символов.
     *
     * @param textView TextView контрол. Не может быть null.
     */
    public static String getText(TextView textView) {
        ObjectUtils.checkNull(textView, "TextView to get text can not be null");

        return textView.getText().toString().trim();
    }
}
