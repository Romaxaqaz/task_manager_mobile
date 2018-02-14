package com.qulix.panteleevrv.trainingtask.client.ui.employee;

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
import com.qulix.panteleevrv.trainingtask.client.utils.UiUtils;
import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.server.Server;
import com.qulix.panteleevrv.trainingtask.server.ServerException;

/**
 * Страница для отображения списка сотрудников.
 *
 * @author Q-RPA
 */
public class EmployeeListActivity extends Activity {

    private static final int EMPLOYEE_ACTIVITY_CODE = 1;

    private Server server;
    private ExceptionHandler exceptionHandler;
    private SettingsService settingsService;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        UiUtils.configureActionBar(this, R.string.employee_activity_header);

        TrainingApplication application = ((TrainingApplication) getApplicationContext());
        server = application.getServer();
        settingsService = application.getSettingsService();
        exceptionHandler = new ServerExceptionHandler(this);

        initView();
        loadEmployees();
    }

    private void initView() {
        ListView employees = (ListView) findViewById(R.id.employees);
        employees.setEmptyView(findViewById(R.id.emptyEmployee));
        employees.setOnItemClickListener(new EmployeeItemClick());
        employees.setOnItemLongClickListener(new EmployeeItemLongClick());

        employeeAdapter = new EmployeeAdapter(this);
        employees.setAdapter(employeeAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EMPLOYEE_ACTIVITY_CODE:
                if (resultCode == RESULT_OK) {
                    loadEmployees();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empoyee_activity_menu, menu);
        menu.findItem(R.id.update).setOnMenuItemClickListener(new UpdateMenuEmployees());
        menu.findItem(R.id.addEmployee).setOnMenuItemClickListener(new AddEmployeeMenuListener());
        return true;
    }

    private void showPopupMenu(View view, Employee employee) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.list_menu);
        popupMenu.getMenu().findItem(R.id.update).setOnMenuItemClickListener(new EmployeeItemUpdateListener(employee));
        popupMenu.getMenu().findItem(R.id.delete).setOnMenuItemClickListener(new EmployeeItemDeleteListener(employee));
        popupMenu.show();
    }

    private void editEmployee(Employee employee) {
        DetailEmployeeActivity.startUpdate(EmployeeListActivity.this, employee, EMPLOYEE_ACTIVITY_CODE);
    }

    private void loadEmployees() {
        new LoadEmployeesAsync().execute(settingsService.getSettings().getItemsCount());
    }

    private class EmployeeItemUpdateListener implements MenuItem.OnMenuItemClickListener {

        private Employee employee;

        private EmployeeItemUpdateListener(Employee employee) {
            this.employee = employee;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            editEmployee(employee);
            return true;
        }
    }

    private class EmployeeItemDeleteListener implements MenuItem.OnMenuItemClickListener {

        private Employee employee;

        private EmployeeItemDeleteListener(Employee employee) {
            this.employee = employee;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            UiUtils.showAlertDialog(EmployeeListActivity.this,
                R.string.delete_employee_header,
                R.string.delete_employee_message,
                new DeleteDialogListener(employee));
            return true;
        }
    }

    private class DeleteDialogListener implements DialogInterface.OnClickListener {

        private Employee employee;

        DeleteDialogListener(Employee employee) {
            this.employee = employee;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    new DeleteEmployeesAsync().execute(employee);
                    break;
            }
        }
    }

    private class UpdateMenuEmployees implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            loadEmployees();
            return true;
        }
    }

    private class AddEmployeeMenuListener implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            DetailEmployeeActivity.startCreate(EmployeeListActivity.this, EMPLOYEE_ACTIVITY_CODE);
            return true;
        }
    }

    private class EmployeeItemLongClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            showPopupMenu(view, employeeAdapter.getItem(position));
            return true;
        }
    }

    private class EmployeeItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            editEmployee(employeeAdapter.getItem(position));
        }
    }

    private class LoadEmployeesAsync extends ProgressAsyncTask<Integer, List<Employee>> {

        private LoadEmployeesAsync() {
            super(EmployeeListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Employee> runTask(Integer... param) throws ServerException {
            return server.getEmployees(param[0]);
        }

        @Override
        public void onCompleted(List<Employee> response) {
            employeeAdapter.setData(response);
        }
    }

    private class DeleteEmployeesAsync extends ProgressAsyncTask<Employee, List<Employee>> {

        private DeleteEmployeesAsync() {
            super(EmployeeListActivity.this, exceptionHandler);
        }

        @Override
        protected List<Employee> runTask(Employee... param) throws ServerException {
            server.deleteEmployee(param[0]);
            return server.getEmployees(settingsService.getSettings().getItemsCount());
        }

        @Override
        public void onCompleted(List<Employee> response) {
            employeeAdapter.setData(response);
        }
    }
}
