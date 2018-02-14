package com.qulix.panteleevrv.trainingtask.client.utils;

import android.util.Log;

/**
 * Утилитный класс для работы с потоками
 *
 * @author Q-RPA
 */
public class ThreadUtils {

    private static final String TAG = "ThreadUtils";

    /**
     * Устанавливает задержку UI потока на указанное время.
     *
     * @param time время задержки (миллисекунды).
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException exception) {
            Log.e(TAG, "Thread interrupted exception", exception);
            Thread.currentThread().interrupt();
        }
    }

}
