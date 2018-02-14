package com.qulix.panteleevrv.trainingtask.client.ui.task;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.model.Task;

/**
 * Адаптер для отображения элементов списка задач
 *
 * @author Q-RPA
 */
class TaskAdapter extends BaseListAdapter<Task, BaseHolder<Task>> {

    /**
     * Конструктор класса {@link TaskAdapter}
     * @param context контекст данных
     */
    TaskAdapter(Context context) {
        super(context);
        setItemLayout(R.layout.task_item);
    }

    private class ViewHolder implements BaseHolder<Task> {

        private TextView taskItemId;
        private ImageView taskItemStatus;
        private TextView taskItemProject;
        private TextView taskName;

        ViewHolder(View view) {
            taskItemId = (TextView) view.findViewById(R.id.taskItemId);
            taskItemStatus = (ImageView) view.findViewById(R.id.taskItemStatus);
            taskItemProject = (TextView) view.findViewById(R.id.taskItemProject);
            taskName = (TextView) view.findViewById(R.id.taskName);
        }

        @Override
        public void bind(Task item) {
            taskItemId.setText(item.getId());
            taskItemStatus.setImageResource(new StatusIconConverter().convert(item.getStatus()));
            taskItemProject.setText(item.getProject() != null ? item.getProject().getShortName() : "-");
            taskName.setText(item.getName());
        }
    }

    @Override
    protected TaskAdapter.ViewHolder createHolder(View view) {
        return new ViewHolder(view);
    }
}
