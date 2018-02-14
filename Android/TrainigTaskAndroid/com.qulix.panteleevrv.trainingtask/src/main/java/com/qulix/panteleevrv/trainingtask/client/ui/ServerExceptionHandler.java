package com.qulix.panteleevrv.trainingtask.client.ui;

import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;

import android.content.Context;

/**
 * Класс для реализации обработки серверных исключений.
 *
 * <p>Отображает сообшение ошибки с помощью {@link android.widget.Toast}</p>
 *
 * @author Q-RPA
 */
public class ServerExceptionHandler implements ExceptionHandler {

    private final Context context;

    public ServerExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(Exception exception) {
        UiUtils.showToast(context, exception.getMessage());
    }
}
