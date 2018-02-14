package com.qulix.panteleevrv.trainingtask.client.utils;

import java.util.Calendar;

import com.qulix.panteleevrv.trainingtask.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Утилитный класс для работы с UI.
 *
 * @author Q-RPA
 */
public class UiUtils {

    /**
     * Отображает toast сообщение с установленным временем отображения {@link Toast#LENGTH_LONG}.
     *
     * @param context контекст данных. Не может быть null.
     * @param text текст сообщения.
     * */
    public static void showToast(Context context, String text) {
        ObjectUtils.checkNull(context, "Context for message display can not be null");

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Отображает диалоговое окно для выбора дыты.
     *
     * @param activity страница для отображения DatePicker. Не может быть null.
     * @param listener слушатель выбора даты.
     */
    public static void openDatePicker(final Activity activity, DatePickerDialog.OnDateSetListener listener) {
        ObjectUtils.checkNull(activity, "Activity for DatePicker display can not be null");

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int dialogStyle = android.R.style.Theme_Holo_Light_Dialog;
        DatePickerDialog dialog = new DatePickerDialog(activity, dialogStyle, listener, year, month, day);
        dialog.show();
    }

    /**
     * Получает параметр переданный при навигации.
     *
     * @param intent текущий интент. Не может быть null.
     * @param key ключ передаваемого параметра.
     * @param <T> тип параметра.
     * @return возвращает переданный параметр.
     */
    public static <T> T getSerializableIntentParam(Intent intent, String key) {
        ObjectUtils.checkNull(intent, "Intent for the parameter can not be Null");

        return (T) intent.getSerializableExtra(key);
    }

    /**
     * Заканчивает работу со страницей с установкой результата.
     *
     * @param activity страница на которой необходимо завершить работу.
     * @param resultCode код результата.
     */
    public static void finishActivity(final Activity activity, int resultCode) {
        ObjectUtils.checkNull(activity, "Activity for completion can not be null.");

        activity.setResult(resultCode);
        activity.finish();
    }

    /**
     * Отображает диалог с подтверждением действия.
     *
     * @param activity страница на которой нужно отобразить диалог.
     * @param header заголовок диалога.
     * @param message сообщение диалога.
     * @param listener слушатель подтверждения действия.
     */
    public static void showAlertDialog(final Activity activity, int header, int message,
        DialogInterface.OnClickListener listener) {
        ObjectUtils.checkNull(activity, "Activity for AlertDialog display can not be null");

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(header);
        dialog.setMessage(message);
        dialog.setPositiveButton(R.string.yes, listener);
        dialog.setNegativeButton(R.string.no, null);
        dialog.create();
        dialog.show();
    }

    /**
     * Устанавливает кастомный ActionBar с кнопкой возврата на предыдущую страницу.
     *
     * @param activity страница на которой нужно отобразить кастомный ActionBar.
     * @param headerId идентификатор строки заголовка ActionBar.
     */
    public static void configureActionBar(final Activity activity, int headerId) {
        ObjectUtils.checkNull(activity, "Activity for configure ActionBar can not be null");

        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = LayoutInflater.from(activity).inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(headerId);

        ImageButton backButton = (ImageButton) view.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        actionBar.setCustomView(view);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}

