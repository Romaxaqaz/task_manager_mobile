package com.qulix.panteleevrv.trainingtask.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.employee.EmployeeListActivity;
import com.qulix.panteleevrv.trainingtask.client.ui.project.ProjectListActivity;
import com.qulix.panteleevrv.trainingtask.client.ui.settings.SettingsActivity;
import com.qulix.panteleevrv.trainingtask.client.ui.task.TaskListActivity;

/**
 * Главная страница приложения. Отображает формы для перехода в разделы:
 *
 * <ul>
 *     <li>Список проектов</li>
 *     <li>Список задач</li>
 *     <li>Список сотрудников</li>
 *     <li>Настройки приложения</li>
 * </ul>
 *
 * @author Q-RPA
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.main_activity_header));

        initActivity();
    }

    private void initActivity() {
        initNavigationButton(R.id.projectsPage, new NavigateListener(ProjectListActivity.class));
        initNavigationButton(R.id.tasksPage, new NavigateListener(TaskListActivity.class));
        initNavigationButton(R.id.employeesPage, new NavigateListener(EmployeeListActivity.class));
    }

    private void initNavigationButton(int id, View.OnClickListener listener) {
        Button navigatedButton = (Button) findViewById(id);
        navigatedButton.setOnClickListener(listener);
    }

    private void navigateTo(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        menu.findItem(R.id.settingsService).setOnMenuItemClickListener(new SettingsListener());
        return true;
    }

    private class NavigateListener implements View.OnClickListener {

        private Class<?> activity;

        NavigateListener(Class<?> activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            navigateTo(activity);
        }
    }

    private class SettingsListener implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            navigateTo(SettingsActivity.class);
            return true;
        }
    }
}
