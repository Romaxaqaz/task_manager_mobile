package com.qulix.panteleevrv.trainingtask.client.ui.project;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.model.Project;

/**
 * Адаптер для отображения элементов списка проектов.
 *
 * @author Q-RPA
 */
public class ProjectAdapter extends BaseListAdapter<Project, BaseHolder<Project>> {

    public ProjectAdapter(Context context) {
        super(context);
        setItemLayout(R.layout.project_item);
    }

    @Override
    protected ProjectAdapter.ViewHolder createHolder(View view) {
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    protected String getItemName() {
        Project selectedProject = getSelectedItem();
        return selectedProject != null ? selectedProject.getName() : null;
    }

    private static class ViewHolder implements BaseHolder<Project> {

        private TextView projectItemId;
        private TextView projectItemName;
        private TextView projectItemShortName;

        ViewHolder(View view) {
            projectItemId = (TextView) view.findViewById(R.id.projectItemId);
            projectItemName = (TextView) view.findViewById(R.id.projectItemName);
            projectItemShortName = (TextView) view.findViewById(R.id.projectItemShortName);
        }

        @Override
        public void bind(Project item) {
            projectItemId.setText(item.getId());
            projectItemName.setText(item.getName());
            projectItemShortName.setText(item.getShortName());
        }
    }
}
