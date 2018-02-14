package com.qulix.panteleevrv.trainingtask.client.ui.task;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.ExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.ProgressAsyncTask;
import com.qulix.panteleevrv.trainingtask.client.ui.ServerExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.employee.EmployeeAdapter;
import com.qulix.panteleevrv.trainingtask.client.ui.project.ProjectAdapter;
import com.qulix.panteleevrv.trainingtask.client.ui.settings.SettingsService;
import com.qulix.panteleevrv.trainingtask.client.converters.DateToStringConverter;
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.client.utils.TextUtils;
import com.qulix.panteleevrv.trainingtask.client.TrainingApplication;
import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.model.Status;
import com.qulix.panteleevrv.trainingtask.model.Task;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerException;

import static com.qulix.panteleevrv.trainingtask.client.utils.UiUtils.*;
import static android.text.TextUtils.*;

/**
 * Страница для работы с сущностью {@link Task}
 *
 * <p>Позволяет добавлять задачу или редактировать информацию о задаче</p>
 *
 * @author Q-RPA
 */
public class DetailTaskActivity extends Activity {

    private static final String TASK_ID = DetailTaskActivity.class + ".TASK_ID";
    private static final String IS_PROJECT_TASK_ID = DetailTaskActivity.class + ".PROJECT_ID";
    private static final String PROJECT_TASK_ID = DetailTaskActivity.class + ".PROJECT_TASK_ID";

    private static final List<Status> STATUS_LIST = Arrays.asList(Status.values());

    private Server server;
    private SettingsService settingsService;
    private Task task;
    private Project projectForTask;
    private ExceptionHandler exceptionHandler;
    private boolean isUpdateTask = true;
    private boolean isProjectTask;

    private TextView taskId;
    private TextView taskName;
    private TextView taskTime;
    private TextView taskStart;
    private TextView taskEnd;
    private Spinner statuses;
    private ListViewerView<Employee> employees;
    private ListViewerView<Project> projects;

    /**
     * Переход на страницу создания задачи. Если project != null переход осуществлен со страницы задач проекта.
     *
     * @param activity страница с которой происходит переход.
     * @param project проект для добавления задачи. Null - если нужно добавить задачу в список задач.
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startCreate(Activity activity, Project project, int requestCode) {
        Intent intent = new Intent(activity, DetailTaskActivity.class);
        intent.putExtra(PROJECT_TASK_ID, project);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Переход на страницу редактирования информации о задаче.
     *
     * @param activity страница с которой происходит переход.
     * @param task задача для обновления информации.
     * @param isProjectTask определяет, что переход осуществлен со страницы задач проекта
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startUpdate(Activity activity, Task task, boolean isProjectTask, int requestCode) {
        Intent intent = new Intent(activity, DetailTaskActivity.class);
        intent.putExtra(TASK_ID, task);
        intent.putExtra(IS_PROJECT_TASK_ID, isProjectTask);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setTitle(getString(R.string.add_task_header));

        TrainingApplication application = ((TrainingApplication) getApplicationContext());
        server = application.getServer();
        settingsService = application.getSettingsService();
        exceptionHandler = new ServerExceptionHandler(this);

        task = UiUtils.getSerializableIntentParam(getIntent(), TASK_ID);
        projectForTask = getSerializableIntentParam(getIntent(), PROJECT_TASK_ID);
        isProjectTask = getIntent().getBooleanExtra(IS_PROJECT_TASK_ID, false);
        isUpdateTask = task != null;

        setActivityHeader(isUpdateTask);
        initView();
        setProjectsEnable();
        setTask(task);
        loadData();
    }

    private void initView() {
        taskId = (EditText) findViewById(R.id.taskId);
        taskName = (EditText) findViewById(R.id.taskName);
        taskTime = (EditText) findViewById(R.id.taskTime);
        taskStart = (EditText) findViewById(R.id.taskStart);
        taskEnd = (EditText) findViewById(R.id.taskEnd);
        statuses = (Spinner) findViewById(R.id.statuses);
        employees = (ListViewerView<Employee>) findViewById(R.id.taskEmployees);
        employees.setAdapter(new EmployeeAdapter(this));
        projects = (ListViewerView<Project>) findViewById(R.id.taskProjects);
        projects.setAdapter(new ProjectAdapter(this));

        setDatePickerButton(R.id.taskDateStartButton, taskStart);
        setDatePickerButton(R.id.taskDateEndButton, taskEnd);

        setPageButton(R.id.saveTask, new SaveButtonClick());
        setPageButton(R.id.backTaskList, new CancelButtonClick());

        StatusAdapter statusAdapter = new StatusAdapter(this);
        statusAdapter.setData(STATUS_LIST);
        statuses.setAdapter(statusAdapter);
    }

    private void loadData() {
        int count = settingsService.getSettings().getItemsCount();
        new LoadProjectSpinnerAsync().execute(count);
        new LoadEmployeeSpinnerAsync().execute(count);
    }

    private void setTask(Task task) {
        task = checkTaskParam(task);
        taskId.setText(task.getId());
        taskName.setText(task.getName());
        taskTime.setText(String.valueOf(task.getTime()));
        taskStart.setText(new DateToStringConverter().convert(task.getStart()));
        taskEnd.setText(new DateToStringConverter().convert(task.getEnd()));
        statuses.setSelection(STATUS_LIST.indexOf(task.getStatus()));
    }

    private void sendTask(Task task) {
        ProgressAsyncTask<Task, Void> asyncTask = isUpdateTask ? new UpdateTaskAsync() : new AddTaskAsync();
        asyncTask.execute(task);
    }

    private void saveTask() {
        TaskValidator validator = new TaskValidator(this);
        Task task = validator.validate(taskName, taskTime, taskStart, taskEnd, statuses, employees, projects);
        if (task != null) {
            task.setId(this.task.getId());
            sendTask(task);
        } else {
            showToast(DetailTaskActivity.this, join(TextUtils.NEW_LINE, validator.getErrors()));
        }
    }

    private void setDatePickerButton(int imageView, TextView textField) {
        ImageView dateView = (ImageView) findViewById(imageView);
        dateView.setOnClickListener(new DateDialogButtonClick(textField));
    }

    private void setPageButton(int buttonId, View.OnClickListener listener) {
        Button pageButton = (Button) findViewById(buttonId);
        pageButton.setOnClickListener(listener);
    }

    private void setActivityHeader(boolean isUpdate) {
        setTitle(isUpdate ? getString(R.string.edit_project_header) : getString(R.string.add_task_header));
    }

    private void setProjectsEnable() {
        // если задача открыта из списка проектов или нужно добавить задачу
        // в проект - отключаем возможность выбора проекта из списка проектов
        if (isProjectTask || projectForTask != null) {
            projects.setEnable(false);
        }
    }

    private Task checkTaskParam(Task task) {
        return task == null ? new Task() : task;
    }

    private class SaveButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            saveTask();
        }
    }

    private class CancelButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class DateDialogButtonClick implements View.OnClickListener {

        private TextView editText;

        DateDialogButtonClick(TextView editText) {
            this.editText = editText;
        }

        @Override
        public void onClick(View view) {
            openDatePicker(DetailTaskActivity.this, new SetDatePickerListener(editText));
        }
    }

    private class SetDatePickerListener implements DatePickerDialog.OnDateSetListener {

        private TextView text;

        SetDatePickerListener(TextView text) {
            this.text = text;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            text.setText(new DateToStringConverter().convert(calendar.getTime()));
        }
    }

    private class AddTaskAsync extends ProgressAsyncTask<Task, Void> {

        private AddTaskAsync() {
            super(DetailTaskActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Task... param) throws ServerException {
            server.addTask(param[0]);
            return null;
        }

        @Override
        protected void onCompleted(Void param) {
            UiUtils.finishActivity(DetailTaskActivity.this, RESULT_OK);
        }
    }

    private class UpdateTaskAsync extends ProgressAsyncTask<Task, Void> {

        private UpdateTaskAsync() {
            super(DetailTaskActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Task... param) throws ServerException {
            server.updateTask(param[0]);
            return null;
        }

        @Override
        protected void onCompleted(Void param) {
            UiUtils.finishActivity(DetailTaskActivity.this, RESULT_OK);
        }
    }

    private class LoadProjectSpinnerAsync extends ProgressAsyncTask<Integer, List<Project>> {

        private LoadProjectSpinnerAsync() {
            super(DetailTaskActivity.this, exceptionHandler);
        }

        @Override
        protected List<Project> runTask(Integer... param) throws ServerException {
            return server.getProjects(param[0]);
        }

        @Override
        public void onCompleted(List<Project> response) {
            projects.setData(response, isUpdateTask ? task.getProject() : projectForTask);
        }
    }

    private class LoadEmployeeSpinnerAsync extends ProgressAsyncTask<Integer, List<Employee>> {

        private LoadEmployeeSpinnerAsync() {
            super(DetailTaskActivity.this, exceptionHandler);
        }

        @Override
        protected List<Employee> runTask(Integer... param) throws ServerException {
            return server.getEmployees(param[0]);
        }

        @Override
        public void onCompleted(List<Employee> response) {
            employees.setData(response, isUpdateTask ? task.getEmployee() : null);
        }
    }
}
