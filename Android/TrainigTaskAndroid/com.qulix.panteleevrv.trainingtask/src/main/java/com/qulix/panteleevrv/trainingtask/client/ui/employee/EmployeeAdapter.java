package com.qulix.panteleevrv.trainingtask.client.ui.employee;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.model.Employee;

/**
 * Адаптер для отображения элементов списка сотрудников.
 *
 * @author Q-RPA
 */
public class EmployeeAdapter extends BaseListAdapter<Employee, BaseHolder<Employee>> {

    public EmployeeAdapter(Context context) {
        super(context);
        setItemLayout(R.layout.employee_item);
    }

    @Override
    protected EmployeeAdapter.ViewHolder createHolder(View view) {
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    protected String getItemName() {
        Employee selectedEmployee = getSelectedItem();
        return selectedEmployee != null ? selectedEmployee.getName() : null;
    }

    private static class ViewHolder implements BaseHolder<Employee> {

        private TextView employeeItemId;
        private TextView employeeItemName;
        private TextView employeeItemSurname;
        private TextView employeeItemPosition;

        ViewHolder(View view) {
            employeeItemId = (TextView) view.findViewById(R.id.employeeItemId);
            employeeItemName = (TextView) view.findViewById(R.id.employeeItemName);
            employeeItemSurname = (TextView) view.findViewById(R.id.employeeItemSurname);
            employeeItemPosition = (TextView) view.findViewById(R.id.employeeItemPosition);
        }

        @Override
        public void bind(Employee item) {
            employeeItemId.setText(item.getId());
            employeeItemName.setText(item.getName());
            employeeItemSurname.setText(item.getSurname());
            employeeItemPosition.setText(item.getPosition());
        }
    }
}
