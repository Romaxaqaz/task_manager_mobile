package com.qulix.panteleevrv.trainingtask.client.ui;

import com.qulix.panteleevrv.trainingtask.client.utils.ThreadUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Страница отображения SplashScreen
 *
 * @author Q-RPA
 */
public class SplashActivity extends Activity {

    private static final int SLEEP_TIME = 2000;

    private boolean isActiveView;
    private boolean isLoadedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadFakeDataAsync(new ServerExceptionHandler(this)).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActiveView = true;

        if (isLoadedData) {
            toMainPage();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActiveView = false;
    }

    private void toMainPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private final class LoadFakeDataAsync extends BaseAsyncTask<Void, Void> {

        LoadFakeDataAsync(ExceptionHandler exceptionHandler) {
            super(exceptionHandler);
        }

        @Override
        protected Void runTask(Void... voids) {
            ThreadUtils.sleep(SLEEP_TIME);
            return null;
        }

        @Override
        protected void onCompleted(Void result) {
            if (isActiveView) {
                toMainPage();
            }
            isLoadedData = true;
        }
    }
}
