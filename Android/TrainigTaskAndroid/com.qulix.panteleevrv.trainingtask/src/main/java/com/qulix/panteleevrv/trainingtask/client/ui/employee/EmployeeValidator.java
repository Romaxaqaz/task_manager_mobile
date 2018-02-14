package com.qulix.panteleevrv.trainingtask.client.ui.employee;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.Validator;
import com.qulix.panteleevrv.trainingtask.model.Employee;

import static com.qulix.panteleevrv.trainingtask.client.utils.TextUtils.*;

import android.content.Context;
import android.widget.TextView;

/**
 * Класс валидации сущности {@link Employee}.
 *
 * <p>Валидирует поля ввода страницы {@link DetailEmployeeActivity} с добавлением ошибок в общий список ошибок валидации.</p>
 *
 * @author Q-RPA
 */
class EmployeeValidator extends Validator {

    EmployeeValidator(Context context) {
        super(context);
    }

    /**
     * Валидирует поля ввода сущности {@link Employee} страницы {@link DetailEmployeeActivity}.
     *
     * @param nameView поле ввода имени сотрудника.
     * @param surnameView поле ввода фамилии сотрудника.
     * @param positionView поле ввода должности сотрудника.
     * @return в случае успешной валидации возвращает сформированыый экземпляр сущности {@link Employee}, иначе - null.
     */
    Employee validate(TextView nameView, TextView surnameView, TextView positionView) {

        String name = checkNullOrEmpty(getText(nameView), R.string.employee_name_error);
        String surname = checkNullOrEmpty(getText(surnameView), R.string.employee_surname_error);
        String position = checkNullOrEmpty(getText(positionView), R.string.employee_position_error);

        return isValid() ? new Employee(name, surname, position) : null;
    }
}
