package com.qulix.panteleevrv.trainingtask.client.ui;

import android.os.AsyncTask;

/**
 * Базовый класс для упрощения реализации асинхронных запросов.
 *
 * <p>Основные методы класса.</p>
 * <ul>
 *     <li>{@link BaseAsyncTask#onStart()} метод для обработки начала операции</li>
 *     <li>{@link BaseAsyncTask#onFinish()} метод для обработки завершения выполнения операции</li>
 *     <li>{@link BaseAsyncTask#runTask(Object[])} выполняет задачу в фоне</li>
 *     <li>{@link BaseAsyncTask#onCompleted(Object)} выполняется после успешного выполнения задачи</li>
 * </ul>
 *
 * @param <Params> тип входного параметра
 * @param <Result> тип возвращаемого параметра при успешном выполнении задачи
 *
 * @author Q-RPA
 */
abstract class BaseAsyncTask<Params, Result> extends AsyncTask<Params, Void, Result> {

    private ExceptionHandler exceptionHandler;
    private Exception exception = null;

    BaseAsyncTask(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Метод для обработки начала операции
     */
    protected void onStart() {
    }

    /**
     * Метод выполнения операции
     *
     * @param param параметры для выполнения операции
     */
    protected abstract Result runTask(Params... param) throws Exception;

    /**
     * Метод для обработки завершения операции
     *
     * @param param результат выполения
     */
    protected abstract void onCompleted(Result param);

    /**
     * Метод для обработки завершения выполнения операции
     */
    protected void onFinish() {
    }

    private void onError(Exception exception) {
        if (exceptionHandler != null) {
            exceptionHandler.handle(exception);
        }
    }

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return runTask(params);
        }
        catch (Exception ex) {
            exception = ex;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        onStart();
    }

    @Override
    protected void onPostExecute(Result response) {
        if (exception == null) {
            onCompleted(response);
        } else {
            onError(exception);
        }
        onFinish();
    }
}
