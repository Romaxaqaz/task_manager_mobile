package com.qulix.panteleevrv.trainingtask.client.ui;

/**
 * Интерфес для реализации ViewHolder для адаптеров
 *
 * @param <T> модель данных элемента списка
 *
 * @author Q-RPA
 */
public interface BaseHolder<T> {

    /**
     * Связывает модель данных с элементами на странице
     *
     * @param item элемент списка
     */
    void bind(T item);
}
