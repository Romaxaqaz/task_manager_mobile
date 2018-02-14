package com.qulix.panteleevrv.trainingtask.client.ui.project;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.Validator;
import com.qulix.panteleevrv.trainingtask.model.Project;

import static com.qulix.panteleevrv.trainingtask.client.utils.TextUtils.*;

import android.content.Context;
import android.widget.TextView;

/**
 * Валидатор сущности {@link Project}.
 *
 * <p>Валидирует поля ввода страницы {@link DetailProjectActivity} с добавлением ошибок в общий список ошибок валидации</p>.
 *
 * @author Q-RPA
 */
class ProjectValidator extends Validator {

    ProjectValidator(Context context) {
        super(context);
    }

    /**
     * Валидирует поля ввода сущности {@link Project} страницы {@link DetailProjectActivity}.
     *
     * @param nameView поле ввода названия проекта.
     * @param shortNameView поле ввода сокращенного названия проекта.
     * @return в случае успешной валидации возвращает сформированыый экземпляр сущности {@link Project}, иначе - null.
     */
    Project validate(TextView nameView, TextView shortNameView) {

        String name = checkNullOrEmpty(getText(nameView), R.string.project_name_error);
        String shortName = checkNullOrEmpty(getText(shortNameView), R.string.project_short_name_error);

        return isValid() ? new Project(name, shortName) : null;
    }
}
