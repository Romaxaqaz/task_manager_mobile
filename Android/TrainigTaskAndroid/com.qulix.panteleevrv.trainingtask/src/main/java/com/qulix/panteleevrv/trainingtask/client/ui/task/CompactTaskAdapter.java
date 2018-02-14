package com.qulix.panteleevrv.trainingtask.client.ui.task;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.model.Task;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Адаптер для отображения элементов списка задач в компактном режиме.
 *
 * <p>Не отображается столбец "Проект"</p>
 *
 * @author Q-RPA
 */
class CompactTaskAdapter extends BaseListAdapter<Task, BaseHolder<Task>> {

    /**
     * Конструктор класса {@link TaskAdapter}
     * @param context контекст данных
     */
    CompactTaskAdapter(Context context) {
        super(context);
        setItemLayout(R.layout.task_item_compact);
    }

    @Override
    protected CompactTaskAdapter.ViewHolder createHolder(View view) {
        return new CompactTaskAdapter.ViewHolder(view);
    }

    private class ViewHolder implements BaseHolder<Task> {

        private TextView taskItemId;
        private ImageView taskItemStatus;
        private TextView taskProjectShortName;

        ViewHolder(View view) {
            taskItemId = (TextView) view.findViewById(R.id.taskItemId);
            taskItemStatus = (ImageView) view.findViewById(R.id.taskItemStatus);
            taskProjectShortName = (TextView) view.findViewById(R.id.taskName);
        }

        @Override
        public void bind(Task item) {
            taskItemId.setText(item.getId());
            taskItemStatus.setImageResource(new StatusIconConverter().convert(item.getStatus()));
            taskProjectShortName.setText(item.getName());
        }
    }
}
