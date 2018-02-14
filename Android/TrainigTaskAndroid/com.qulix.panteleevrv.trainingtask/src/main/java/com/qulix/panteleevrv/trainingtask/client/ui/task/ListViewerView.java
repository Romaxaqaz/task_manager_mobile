package com.qulix.panteleevrv.trainingtask.client.ui.task;

import java.util.List;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseHolder;
import com.qulix.panteleevrv.trainingtask.client.ui.BaseListAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Базовая view для отображения списка данных {@link ListView} в {@link Dialog}.
 * Представляеи собой TextView по нажатию на который отображается диалог с содержимым.
 *
 * <p>Предназначена для замены элемента {@link android.widget.Spinner}</p>
 * Предоставляет следующие возможности:
 * <ul>
 *     <li>Отображение placeholder'a в случае, если элемент в списке не выбран</li>
 *     <li>Отображение заголовка диалога при отображении данных.</li>
 *     <li>Гибкая настройка отображения данных внутри диалога</li>
 * </ul>
 *
 * Основные методы:
 * <ul>
 *     <li>{@link ListViewerView#setData(List, Object)} устанавливает список данных и выбранный элемент</li>
 *     <li>{@link ListViewerView#setAdapter(BaseListAdapter)} устанавливает адаптер для отображения списка</li>
 *     <li>{@link ListViewerView#setEnable(boolean)} устанавливает возможность выбора элементов</li>
 * </ul>
 *
 * @param <T> тип данных списка
 *
 * @author Q-RPA
 */
public class ListViewerView<T> extends FrameLayout {

    /**
     * Адаптер для отображения списка элементов
     */
    private BaseListAdapter<T, BaseHolder<T>> adapter;

    /**
     * TextView для отображения выбранного элемента списка.
     */
    private TextView selectedItemText;

    /**
     * Выбранный элемент списка.
     */
    private T selectedItem;

    /**
     * Текст Placeholder'а в случае, если элемент списка не выбран.
     */
    private String hint;

    /**
     * Заголовок диалога при отображении данных.
     */
    private String header;

    public ListViewerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttributes(attrs);
        initView();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ListViewerView, 0, 0);
        hint = typedArray.getString(R.styleable.ListViewerView_hint);
        header = typedArray.getString(R.styleable.ListViewerView_header);
        typedArray.recycle();
    }

    private void initView() {
        View rootView = inflate(getContext(), R.layout.list_dialog, this);
        selectedItemText = (TextView) rootView.findViewById(R.id.selectedItem);
        selectedItemText.setOnClickListener(new SelectedItemClickListener());
        selectedItemText.setHint(hint);
    }

    /**
     * Устанавливает список данных для отображения.
     *
     * @param list список данных.
     * @param selectedItem выбранное значение списка. Может быть null.
     */
    public void setData(List<T> list, T selectedItem) {
        adapter.setData(list);
        adapter.setSelectedItem(selectedItem);
        notifySelectedItem(selectedItem);
    }

    /**
     * Устанавливает адаптер дял отображения списка элементов.
     *
     * @param adapter адаптер для отображения списка.
     */
    protected void setAdapter(BaseListAdapter<T, BaseHolder<T>> adapter) {
        this.adapter = adapter;
    }

    /**
     * Возвращает выбранный элемент списка, или null, если ничего не выбрано.
     *
     * @return выбранный элемент списка.
     */
    protected T getSelectedItem() {
        return selectedItem;
    }

    /**
     * Устанавливает возможность выбора элементов в списке.
     *
     * @param enable true - есть возможность выбрать элемент, false - нет.
     */
    protected void setEnable(boolean enable) {
        selectedItemText.setEnabled(enable);
    }

    private void show() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.listview_content_dialog);

        ListView listView = (ListView) dialog.findViewById(R.id.listviewDialog);
        listView.setAdapter(adapter);
        listView.setEmptyView(dialog.findViewById(R.id.emptyList));
        listView.setOnItemClickListener(new ListViewItemClick(dialog));

        dialog.setCancelable(true);
        dialog.setTitle(header);
        dialog.show();
    }

    private void notifySelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        if (selectedItem != null) {
            selectedItemText.setText(adapter.getSelectedItemName());
        }
    }

    private class ListViewItemClick implements AdapterView.OnItemClickListener {

        private Dialog dialog;

        ListViewItemClick(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            notifySelectedItem(adapter.getItem(position));
            dialog.dismiss();
        }
    }

    private class SelectedItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            show();
        }
    }
}

