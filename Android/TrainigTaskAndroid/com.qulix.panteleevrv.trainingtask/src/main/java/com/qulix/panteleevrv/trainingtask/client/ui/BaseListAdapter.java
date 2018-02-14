package com.qulix.panteleevrv.trainingtask.client.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Базовый адаптер списка, реализующий все необходимые методы для простоты создания адаптеров.
 *
 * <ul>
 *     <li>{@link BaseListAdapter#createHolder(View) создает холдер элемента</li>
 *     <li>{@link BaseHolder#bind(Object)} устанавливает привязку данных представления view</li>
 * </ul>
 *
 * @param <T> тип отображаемых данных.
 * @param <ViewHolder> холдер представления элментов, реализующий {@link BaseHolder}
 *
 * @author Q-RPA
 */
public abstract class BaseListAdapter<T, ViewHolder extends BaseHolder> extends BaseAdapter {

    private final Context context;
    private List<T> listData = new ArrayList<>();
    private T selectedItem;
    private int itemResource;

    /**
     * Конструктор класса {@link BaseListAdapter}
     *
     * @param context контекст данных
     */
    public BaseListAdapter(Context context) {
        this.context = context;
    }

    protected void setItemLayout(int itemResource) {
        this.itemResource = itemResource;
    }

    protected abstract ViewHolder createHolder(final View view);

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(itemResource, parent, false);
            holder = createHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bind(getItem(position));
        return view;
    }

    /**
     * Устанавливает список данных адаптера
     *
     * @param list список данных для отображения
     */
    public void setData(List<T> list) {
        listData = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public T getItem(int index) {
        selectedItem = listData.get(index);
        return selectedItem;
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    /**
     * Возвращает название выбранного item. Для получения названия необходимо
     * переопределить метод {@link BaseListAdapter#getItemName()} в дочерних классах.
     *
     * @return наименование выбранного item.
     */
    public String getSelectedItemName() {
        return getItemName();
    }

    /**
     * Возвращает выбранный элемент.
     */
    protected T getSelectedItem() {
        return selectedItem;
    }

    /**
     * Метод для реализации в дочерних классах для получения наименования выбранного элемента.
     *
     * @return возвращает наименование выбранного элемента
     */
    protected String getItemName() {
        return null;
    }
}
