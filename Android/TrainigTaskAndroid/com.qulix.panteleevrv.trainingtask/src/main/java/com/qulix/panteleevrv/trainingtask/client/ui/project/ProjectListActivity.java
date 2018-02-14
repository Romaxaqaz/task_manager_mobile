package com.qulix.panteleevrv.trainingtask.client.ui.project;

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

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.TrainingApplication;
import com.qulix.panteleevrv.trainingtask.client.ui.ExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.ProgressAsyncTask;
import com.qulix.panteleevrv.trainingtask.client.ui.ServerExceptionHandler;
import com.qulix.panteleevrv.trainingtask.client.ui.settings.SettingsService;
import com.qulix.panteleevrv.trainingtask.client.ui.task.TaskListActivity;
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerException;

/**
 * Страница для отображения списка проектов.
 *
 * @author Q-RPA
 */
public class ProjectListActivity extends Activity {

    private static final int PROJECT_ACTIVITY_CODE = 2;

    private Server server;
    private SettingsService settingsService;
    private ProjectAdapter projectAdapter;
    private ExceptionHandler exceptionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        UiUtils.configureActionBar(this, R.string.project_activity_header);

        TrainingApplication application = ((TrainingApplication) getApplicationContext());
        server = application.getServer();
        settingsService = application.getSettingsService();
        exceptionHandler = new ServerExceptionHandler(this);

        initView();
        loadProjects();
    }

    private void initView() {
        ListView projects = (ListView) findViewById(R.id.projects);
        projects.setEmptyView(findViewById(R.id.emptyProjects));
        projects.setOnItemLongClickListener(new ProjectItemLongClick());
        projects.setOnItemClickListener(new ProjectItemClick());

        projectAdapter = new ProjectAdapter(this);
        projects.setAdapter(projectAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PROJECT_ACTIVITY_CODE:
                if (resultCode == RESULT_OK) {
                    loadProjects();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project_activity_menu, menu);
        menu.findItem(R.id.update).setOnMenuItemClickListener(new UpdateMenuProjects());
        menu.findItem(R.id.addProject).setOnMenuItemClickListener(new AddProjectMenuListener());
        return true;
    }

    private void showPopupMenu(View view, Project project) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.project_list_menu);
        popupMenu.getMenu().findItem(R.id.taskList).setOnMenuItemClickListener(new ProjectTasksItemListener(project));
        popupMenu.getMenu().findItem(R.id.update).setOnMenuItemClickListener(new UpdateProjectItemListener(project));
        popupMenu.getMenu().findItem(R.id.delete).setOnMenuItemClickListener(new DeleteProjectItemListener(project));
        popupMenu.show();
    }

    private void editProject(Project project) {
        DetailProjectActivity.startUpdate(ProjectListActivity.this, PROJECT_ACTIVITY_CODE, project);
    }

    private void loadProjects() {
        new LoadProjectsAsync().execute(settingsService.getSettings().getItemsCount());
    }

    private class UpdateProjectItemListener implements MenuItem.OnMenuItemClickListener {

        private Project project;

        private UpdateProjectItemListener(Project project) {
            this.project = project;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            editProject(project);
            return true;
        }
    }

    private class DeleteProjectItemListener implements MenuItem.OnMenuItemClickListener {

        private Project project;

        private DeleteProjectItemListener(Project project) {
            this.project = project;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            UiUtils.showAlertDialog(ProjectListActivity.this,
                R.string.delete_project_header,
                R.string.delete_project_message,
                new DeleteDialogListener(project));
            return true;
        }
    }

    private class DeleteDialogListener implements DialogInterface.OnClickListener {

        private Project project;

        DeleteDialogListener(Project project) {
            this.project = project;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    new DeleteProjectAsync().execute(project);
                    break;
            }
        }
    }

    private class ProjectTasksItemListener implements MenuItem.OnMenuItemClickListener {

        private Project project;

        private ProjectTasksItemListener(Project project) {
            this.project = project;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            TaskListActivity.start(ProjectListActivity.this, project, PROJECT_ACTIVITY_CODE);
            return true;
        }
    }

    private class UpdateMenuProjects implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            loadProjects();
            return true;
        }
    }

    private class AddProjectMenuListener implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            DetailProjectActivity.startCreate(ProjectListActivity.this, PROJECT_ACTIVITY_CODE);
            return true;
        }
    }

    private class ProjectItemLongClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            showPopupMenu(view, projectAdapter.getItem(position));
            return true;
        }
    }

    private class ProjectItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            editProject(projectAdapter.getItem(position));
        }
    }

    private class LoadProjectsAsync extends ProgressAsyncTask<Integer, List<Project>> {

        private LoadProjectsAsync() {
            super(ProjectListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Project> runTask(Integer... param) throws ServerException {
            return server.getProjects(param[0]);
        }

        @Override
        public void onCompleted(List<Project> response) {
            projectAdapter.setData(response);
        }
    }

    private class DeleteProjectAsync extends ProgressAsyncTask<Project, List<Project>> {

        private DeleteProjectAsync() {
            super(ProjectListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Project> runTask(Project... param) throws ServerException {
            server.deleteProject(param[0]);
            return server.getProjects(settingsService.getSettings().getItemsCount());
        }

        @Override
        public void onCompleted(List<Project> response) {
            projectAdapter.setData(response);
        }
    }
}
