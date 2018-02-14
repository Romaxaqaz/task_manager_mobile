package com.qulix.panteleevrv.trainingtask.client.ui.employee;

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
import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.server.Server;

import com.qulix.panteleevrv.trainingtask.server.ServerException;

import static com.qulix.panteleevrv.trainingtask.client.utils.UiUtils.*;

import static android.text.TextUtils.*;

/**
 * Страница для работы с сущностью {@link Employee}.
 *
 * <p>Позволяет добавлять сотрудника или редактировать данные о сотруднике.</p>
 *
 * @author Q-RPA
 */
public class DetailEmployeeActivity extends Activity {

    private static final String EMPLOYEE_ID = DetailEmployeeActivity.class + ".EMPLOYEE_ID";

    private Server server;
    private Employee employee;
    private ExceptionHandler exceptionHandler;
    private boolean isUpdateEmployee = true;

    private TextView employeeId;
    private TextView employeeName;
    private TextView employeeSurname;
    private TextView employeePosition;

    /**
     * Переход на страницу добавления сотрудника.
     *
     * @param activity страница с которой происходит переход.
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startCreate(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, DetailEmployeeActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Переход на страницу редактирования информации о сотрудннике.
     *
     * @param activity страница с которой происходит переход.
     * @param employee сотрудник для обновления информации.
     * @param requestCode код страницы с которой происходит переход.
     */
    public static void startUpdate(Activity activity, Employee employee, int requestCode) {
        Intent intent = new Intent(activity, DetailEmployeeActivity.class);
        intent.putExtra(DetailEmployeeActivity.EMPLOYEE_ID, employee);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        setTitle(getString(R.string.add_employee_header));

        exceptionHandler = new ServerExceptionHandler(this);
        server = ((TrainingApplication) getApplication()).getServer();
        employee = UiUtils.getSerializableIntentParam(getIntent(), EMPLOYEE_ID);
        isUpdateEmployee = employee != null;

        setActivityHeader(isUpdateEmployee);
        initActivity();
        setEmployee(employee);
    }

    private void initActivity() {
        employeeId = (EditText) findViewById(R.id.employeeId);
        employeeName = (EditText) findViewById(R.id.employeeName);
        employeeSurname = (EditText) findViewById(R.id.employeeSurname);
        employeePosition = (EditText) findViewById(R.id.employeePosition);

        Button saveEmployee = (Button) findViewById(R.id.saveEmployee);
        saveEmployee.setOnClickListener(new SaveButtonClick());

        Button backEmployeeList = (Button) findViewById(R.id.backEmployeeList);
        backEmployeeList.setOnClickListener(new CancelButtonClick());
    }

    private void setEmployee(Employee employee) {
        employee = checkEmployeeParam(employee);
        employeeId.setText(employee.getId());
        employeeName.setText(employee.getName());
        employeeSurname.setText(employee.getSurname());
        employeePosition.setText(employee.getPosition());
    }

    private void sendEmployee(Employee employee) {
        ProgressAsyncTask<Employee, Void> task = isUpdateEmployee ? new UpdateEmployeeAsync() : new AddEmployeeAsync();
        task.execute(employee);
    }

    private void saveEmployee() {
        EmployeeValidator validator = new EmployeeValidator(this);
        Employee employee = validator.validate(employeeName, employeeSurname, employeePosition);
        if (employee != null) {
            employee.setId(this.employee.getId());
            sendEmployee(employee);
        } else {
            showToast(DetailEmployeeActivity.this, join(TextUtils.NEW_LINE, validator.getErrors()));
        }
    }

    private void setActivityHeader(boolean isUpdate) {
        setTitle(isUpdate ? getString(R.string.update_employee_header) : getString(R.string.add_employee_header));
    }

    private Employee checkEmployeeParam(Employee employee) {
        return employee == null ? new Employee() : employee;
    }

    private class SaveButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            saveEmployee();
        }
    }

    private class CancelButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class UpdateEmployeeAsync extends ProgressAsyncTask<Employee, Void> {

        UpdateEmployeeAsync() {
            super(DetailEmployeeActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Employee... param) throws ServerException {
            server.updateEmployee(param[0]);
            return null;
        }

        @Override
        public void onCompleted(Void response) {
            UiUtils.finishActivity(DetailEmployeeActivity.this, RESULT_OK);
        }
    }

    private class AddEmployeeAsync extends ProgressAsyncTask<Employee, Void> {

        AddEmployeeAsync() {
            super(DetailEmployeeActivity.this, exceptionHandler);
        }

        @Override
        protected Void runTask(Employee... param) throws ServerException {
            server.addEmployee(param[0]);
            return null;
        }

        @Override
        public void onCompleted(Void response) {
            UiUtils.finishActivity(DetailEmployeeActivity.this, RESULT_OK);
        }
    }
}
