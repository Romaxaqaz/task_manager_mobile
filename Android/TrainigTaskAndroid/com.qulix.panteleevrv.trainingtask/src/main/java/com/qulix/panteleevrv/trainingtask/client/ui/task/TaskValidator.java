package com.qulix.panteleevrv.trainingtask.client.ui.task;

import java.util.Date;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.Validator;
import com.qulix.panteleevrv.trainingtask.client.utils.DateFormatException;
import com.qulix.panteleevrv.trainingtask.client.converters.DateToStringConverter;
import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.model.Status;
import com.qulix.panteleevrv.trainingtask.model.Task;

import static com.qulix.panteleevrv.trainingtask.client.utils.TextUtils.getText;

import android.content.Context;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Валидатор сущности {@link Task}.
 *
 * <p>Валидирует поля ввода страницы {@link DetailTaskActivity} с добавлением ошибок в общий список ошибок валидации</p>.
 *
 * @author Q-RPA
 */
class TaskValidator extends Validator {

    TaskValidator(Context context) {
        super(context);
    }

    /**
     * Валидирует поля ввода сущности {@link Task} страницы {@link DetailTaskActivity}.
     *
     * @param nameView поле ввода названия задачи.
     * @param timeView поле ввода времени выполнения задачи.
     * @param startDateView поле ввода даты начала задачи.
     * @param endDateView поле ввода даты завершения задачи.
     * @param statuses статусы задачи из спиннера статусов.
     * @param employees сотрудники из спиннера сотрудников.
     * @param projects проекты из спиннера проектов.
     * @return в случае успешной валидации возвращает сформированыый экземпляр сущности {@link Task}, иначе - null.
     */
    Task validate(TextView nameView, TextView timeView, TextView startDateView, TextView endDateView, Spinner statuses,
        ListViewerView employees, ListViewerView projects) {

        String name = checkNullOrEmpty(getText(nameView), R.string.task_name_error);
        int time = getTaskTime(getText(timeView));

        Date startDate = getDate(getText(startDateView), R.string.task_start_date_error, R.string.task_start_date_incorrect);
        Date endDate = getDate(getText(endDateView), R.string.task_end_date_error, R.string.task_end_date_incorrect);

        compareDateEnd(startDate, endDate);

        Employee employee = (Employee) checkNullOrEmpty(employees.getSelectedItem(), R.string.task_employee_error);
        Project project = (Project) checkNullOrEmpty(projects.getSelectedItem(), R.string.task_project_error);
        Status status = (Status) checkNullOrEmpty(statuses.getSelectedItem(), R.string.task_status_error);

        return isValid() ? new Task(name, time, startDate, endDate, status, employee, project) : null;
    }

    private int getTaskTime(String value) {
        try {
            return checkPositiveInt(value);
        }
        catch (NumberFormatException exception) {
            addError(R.string.task_time_error);
        }
        return -1;
    }

    private Date getDate(String value, int emptyErrorId, int formatErrorId) {
        if (value.isEmpty()) {
            addError(emptyErrorId);
            return null;
        }

        Date date = null;
        try {
            DateToStringConverter converter = new DateToStringConverter();
            checkDateMatches(value, DateToStringConverter.DEFAULT_FORMAT);
            date = converter.convertBack(value);
        }
        catch (DateFormatException exception) {
            addError(formatErrorId);
        }
        return date;
    }

    private int checkPositiveInt(String value) {
        int number = Integer.parseInt(value);
        if (number <= 0) {
            addError(R.string.task_time_nil_error);
        }
        return number;
    }

    private void compareDateEnd(Date startDate, Date endDate) {
        if (startDate != null & endDate != null && endDate.before(startDate)) {
            addError(R.string.task_start_before_error);
        }
    }

    private static void checkDateMatches(String date, String dateFormat) throws DateFormatException {
        String dateFormatReg = dateFormat.replaceAll("[yMd]", "\\\\d");
        if (!date.matches(dateFormatReg)) {
            String message = String.format("The string representation of the date '%s' has an incorrect format", date);
            throw new DateFormatException(message);
        }
    }
}
