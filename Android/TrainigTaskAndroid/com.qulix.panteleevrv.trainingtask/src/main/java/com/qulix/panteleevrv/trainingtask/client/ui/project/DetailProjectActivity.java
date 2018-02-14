package com.qulix.panteleevrv.trainingtask.client.ui.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.ExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.ProgressAsyncTask;
import com.qulix.panteleevrv.trainingtask.client.ui.ServerExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.client.utils.TextUtils;
import com.qulix.panteleevrv.trainingtask.client.TrainingApplication;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerException;

import static com.qulix.panteleevrv.trainingtask.client.utils.UiUtils.showToast;

import static android.text.TextUtils.*;

/**
 * Страница для работы с сущностью {@link Project}.
 *
 * <p>Позволяет добавлять проект или редактировать данные о проекте</p>
 *
 * @author Q-RPA
 */
public class DetailProjectActivity extends Activity {

    private static final String PROJECT_ID = DetailProjectActivity.class + ".PROJECT_ID";

    private Server server;
    private Project project;
    private ExceptionHandler exceptionHandler;
    private boolean isUpdateProject = true;

    private TextView projectId;
    private TextView projectName;
    private TextView projectShortName;

    /**
     * Переход на страницу добавления проекта.
     *
     * @param activity страница с которой происходит переход.
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startCreate(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, DetailProjectActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Переход на страницу редактирования информации о проекте.
     *
     * @param activity страница с которой происходит переход.
     * @param project проект для обновления информации.
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startUpdate(Activity activity, int requestCode, Project project) {
        Intent intent = new Intent(activity, DetailProjectActivity.class);
        intent.putExtra(DetailProjectActivity.PROJECT_ID, project);

        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        setTitle(getString(R.string.add_project_header));

        server = ((TrainingApplication) getApplication()).getServer();
        project = UiUtils.getSerializableIntentParam(getIntent(), PROJECT_ID);
        exceptionHandler = new ServerExceptionHandler(this);
        isUpdateProject = project != null;

        setActivityHeader(isUpdateProject);
        initActivity();
        setProject(project);
    }

    private void initActivity() {
        projectId = (EditText) findViewById(R.id.projectId);
        projectName = (EditText) findViewById(R.id.projectName);
        projectShortName = (EditText) findViewById(R.id.projectShortName);

        Button saveProject = (Button) findViewById(R.id.saveProject);
        saveProject.setOnClickListener(new SaveButtonClick());

        Button backProjectList = (Button) findViewById(R.id.backProjectList);
        backProjectList.setOnClickListener(new CancelButtonClick());
    }

    private void setProject(Project project) {
        project = checkProjectParam(project);
        projectId.setText(project.getId());
        projectName.setText(project.getName());
        projectShortName.setText(project.getShortName());
    }

    private void sendProject(Project project) {
        ProgressAsyncTask<Project, Void> task = isUpdateProject ? new UpdateProjectAsync() : new AddProjectAsync();
        task.execute(project);
    }

    private void saveProject() {
        ProjectValidator validator = new ProjectValidator(this);
        Project project = validator.validate(projectName, projectShortName);
        if (project != null) {
            project.setId(this.project.getId());
            sendProject(project);
        } else {
            showToast(DetailProjectActivity.this, join(TextUtils.NEW_LINE, validator.getErrors()));
        }
    }

    private void setActivityHeader(boolean isUpdate) {
        setTitle(isUpdate ? getString(R.string.detail_project_header) : getString(R.string.add_project_header));
    }

    private Project checkProjectParam(Project project) {
        return project == null ? new Project() : project;
    }

    private class SaveButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            saveProject();
        }
    }

    private class CancelButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class AddProjectAsync extends ProgressAsyncTask<Project, Void> {

        private AddProjectAsync() {
            super(DetailProjectActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Project... param) throws ServerException {
            server.addProject(param[0]);
            return null;
        }

        @Override
        public void onCompleted(Void response) {
            UiUtils.finishActivity(DetailProjectActivity.this, RESULT_OK);
        }
    }

    private class UpdateProjectAsync extends ProgressAsyncTask<Project, Void> {

        private UpdateProjectAsync() {
            super(DetailProjectActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Project... param) throws ServerException {
            server.updateProject(param[0]);
            return null;
        }

        @Override
        public void onCompleted(Void response) {
            UiUtils.finishActivity(DetailProjectActivity.this, RESULT_OK);
        }
    }
}
