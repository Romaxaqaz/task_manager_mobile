package com.qulix.panteleevrv.trainingtask.client.ui.task;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;
import com.qulix.panteleevrv.trainingtask.model.Status;

/**
 * Адаптер для отображения списка статусов
 *
 * @author Q-RPA
 */
class StatusAdapter extends BaseListAdapter<Status, BaseHolder<Status>> {

    StatusAdapter(Context context) {
        super(context);
        setItemLayout(R.layout.spinner_status_item);
    }

    private static class ViewHolder implements BaseHolder<Status> {

        private TextView statusName;
        private ImageView statusImage;

        ViewHolder(View view) {
            statusName = (TextView) view.findViewById(R.id.statusName);
            statusImage = (ImageView) view.findViewById(R.id.statusImage);
        }

        @Override
        public void bind(Status item) {
            statusName.setText(new StatusNameConverter().convert(item));
            statusImage.setImageResource(new StatusIconConverter().convert(item));
        }
    }

    @Override
    protected StatusAdapter.ViewHolder createHolder(View view) {
        return new ViewHolder(view);
    }
}