package com.qulix.panteleevrv.trainingtask.client.ui.settings;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.Validator;
import com.qulix.panteleevrv.trainingtask.model.Settings;

import static com.qulix.panteleevrv.trainingtask.client.utils.TextUtils.*;

import android.content.Context;
import android.util.Patterns;
import android.widget.TextView;

import static android.text.TextUtils.isEmpty;

/**
 * Класс валидации сущности {@link Settings}.
 *
 * <p>Валидирует поля ввода страницы {@link SettingsActivity} с добавлением ошибок в общий список ошибок валидации</p>.
 *
 * @author Q-RPA
 */
class SettingsValidator extends Validator {

    SettingsValidator(Context context) {
        super(context);
    }

    Settings validate(TextView urlTextView, TextView itemsCountTextView) {
        String url = getUrl(getText(urlTextView));
        Integer itemsCount = getItemsCount(getText(itemsCountTextView));

        return isValid() ? new Settings(url, itemsCount) : null;
    }

    private String getUrl(String value) {
        if (isEmpty(value)) {
            addError(R.string.setting_url_error);
        } else if (!Patterns.WEB_URL.matcher(value).matches()) {
            addError(R.string.incorrect_url_error);
        }
        return value;
    }

    private Integer getItemsCount(String value) {
        if (value.isEmpty()) {
            addError(R.string.setting_count_error);
            return null;
        }

        int count = Integer.parseInt(value);
        if (count <= 0) {
            addError(R.string.setting_count_required_error);
        }
        return count;
    }
}
