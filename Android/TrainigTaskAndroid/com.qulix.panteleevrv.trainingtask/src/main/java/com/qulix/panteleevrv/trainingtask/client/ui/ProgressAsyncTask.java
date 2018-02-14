package com.qulix.panteleevrv.trainingtask.client.ui;

import com.qulix.panteleevrv.trainingtask.R;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Базовый класс для упрощения реализации асинхронных запросов с отображением индикатора выполнения {@link ProgressDialog
 *
 * @param <Params> тип входного параметра
 * @param <Result> тип возвращаемого параметра при успешном выполнении задачи
 *
 * @author Q-RPA
 */
public abstract class ProgressAsyncTask<Params, Result> extends BaseAsyncTask<Params, Result> {

    private ProgressDialog progressDialog;

    public ProgressAsyncTask(Context context, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        initProgressDialog(context);
    }

    private void initProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.please_wait));
        progressDialog.setMessage(context.getString(R.string.loading_data));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        progressDialog.show();
    }

    @Override
    protected void onFinish() {
        progressDialog.hide();
    }
}
