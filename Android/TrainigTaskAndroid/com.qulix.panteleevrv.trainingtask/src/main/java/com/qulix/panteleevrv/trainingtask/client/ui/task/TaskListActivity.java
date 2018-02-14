package com.qulix.panteleevrv.trainingtask.client.ui.task;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.client.ui.ExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.ProgressAsyncTask;
import com.qulix.panteleevrv.trainingtask.client.ui.ServerExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.settings.SettingsService;
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.client.TrainingApplication;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.model.Task;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerException;

/**
 * Страница для отображения списка задач.
 *
 * @author Q-RPA
 */
public class TaskListActivity extends Activity {

    private static final String PROJECT_ID = TaskListActivity.class + ".PROJECT_ID";
    private static final int TASK_LIST_CODE = 4;

    private Server server;
    private SettingsService settingsService;
    private Project project;
    private BaseListAdapter<Task, BaseHolder<Task>> taskAdapter;
    private ExceptionHandler exceptionHandler;
    private boolean isProjectTasks;

    /**
     * Переход на страницу для отображения задач проекта.
     *
     * @param activity страница с которой происходит переход.
     * @param project проект для отображения задач.
     * @param requestCode код страницы перехода.
     */
    public static void start(Activity activity, Project project, int requestCode) {
        Intent intent = new Intent(activity, TaskListActivity.class);
        intent.putExtra(PROJECT_ID, project);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        UiUtils.configureActionBar(this, R.string.activity_task_title);

        TrainingApplication application = ((TrainingApplication) getApplicationContext());
        server = application.getServer();
        settingsService = application.getSettingsService();
        exceptionHandler = new ServerExceptionHandler(this);
        project = UiUtils.getSerializableIntentParam(getIntent(), PROJECT_ID);
        isProjectTasks = project != null;

        initView();
        loadTasks();
    }

    private void initView() {
        ListView tasks = (ListView) findViewById(R.id.tasks);
        tasks.setEmptyView(findViewById(R.id.emptyTask));
        tasks.setOnItemClickListener(new TaskItemClick());
        tasks.setOnItemLongClickListener(new TaskItemLongClick());

        if (isProjectTasks) {
            hideProjectColumn();
            taskAdapter = new CompactTaskAdapter(this);
        } else {
            taskAdapter = new TaskAdapter(this);
        }
        tasks.setAdapter(taskAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TASK_LIST_CODE:
                if (resultCode == RESULT_OK) {
                    loadTasks();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_activity_menu, menu);
        menu.findItem(R.id.update).setOnMenuItemClickListener(new UpdateMenuEmployees());
        menu.findItem(R.id.addTask).setOnMenuItemClickListener(new AddTaskMenuListener());
        return true;
    }

    private void showPopupMenu(View view, Task task) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.list_menu);

        popupMenu.getMenu().findItem(R.id.update).setOnMenuItemClickListener(new TaskAddItemListener(task));
        popupMenu.getMenu().findItem(R.id.delete).setOnMenuItemClickListener(new TaskDeleteItemListener(task));
        popupMenu.show();
    }

    private void editTask(Task task) {
        DetailTaskActivity.startUpdate(TaskListActivity.this, task, isProjectTasks, TASK_LIST_CODE);
    }

    private List<Task> getTasks(boolean isProjectTasks, int count) throws ServerException {
        return isProjectTasks ? server.getProjectTasks(project, count) : server.getTasks(count);
    }

    private void loadTasks() {
        new LoadTasksAsync().execute(settingsService.getSettings().getItemsCount());
    }

    private void hideProjectColumn() {
        TextView taskProjectHeader = (TextView) findViewById(R.id.taskProjectHeader);
        taskProjectHeader.setVisibility(View.GONE);
    }

    private class UpdateMenuEmployees implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            loadTasks();
            return true;
        }
    }

    private class AddTaskMenuListener implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            DetailTaskActivity.startCreate(TaskListActivity.this, project, TASK_LIST_CODE);
            return true;
        }
    }

    private class TaskAddItemListener implements MenuItem.OnMenuItemClickListener {

        private Task task;

        private TaskAddItemListener(Task task) {
            this.task = task;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            editTask(task);
            return true;
        }
    }

    private class TaskDeleteItemListener implements MenuItem.OnMenuItemClickListener {

        private Task task;

        private TaskDeleteItemListener(Task task) {
            this.task = task;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            UiUtils.showAlertDialog(TaskListActivity.this,
                R.string.delete_task_header,
                R.string.delete_task_message,
                new DeleteDialogListener(task));
            return true;
        }
    }

    private class DeleteDialogListener implements DialogInterface.OnClickListener {

        private Task task;

        DeleteDialogListener(Task task) {
            this.task = task;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    new DeleteTaskAsync().execute(task);
                    break;
            }
        }
    }

    private class TaskItemLongClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            showPopupMenu(view, taskAdapter.getItem(position));
            return true;
        }
    }

    private class TaskItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            editTask(taskAdapter.getItem(position));
        }
    }

    private class LoadTasksAsync extends ProgressAsyncTask<Integer, List<Task>> {

        private LoadTasksAsync() {
            super(TaskListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Task> runTask(Integer... param) throws ServerException {
            return getTasks(isProjectTasks, param[0]);
        }

        @Override
        protected void onCompleted(List<Task> response) {
            taskAdapter.setData(response);
        }
    }

    private class DeleteTaskAsync extends ProgressAsyncTask<Task, List<Task>> {

        private DeleteTaskAsync() {
            super(TaskListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Task> runTask(Task... param) throws ServerException {
            server.deleteTask(param[0]);
            return getTasks(isProjectTasks, settingsService.getSettings().getItemsCount());
        }

        @Override
        protected void onCompleted(List<Task> response) {
            taskAdapter.setData(response);
        }
    }
}
