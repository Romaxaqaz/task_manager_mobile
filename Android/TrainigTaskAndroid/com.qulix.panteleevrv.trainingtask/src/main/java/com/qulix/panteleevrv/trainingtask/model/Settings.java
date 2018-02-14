package com.qulix.panteleevrv.trainingtask.model;

/**
 * Модель настроек риложения
 * <p>Содержит следующие настройки приложения:</p>
 * <ul>
 *     <li>Адрес сервера</li>
 *     <li>Количество отображаемых элементов списков на страницах</li>
 * </ul>
 *
 * @author Q-RPA
 */
public class Settings {

    /**
     * Адрес сервера
     */
    private String url;

    /**
     * Количество выводимых элементов списка
     */
    private int itemsCount;

    public Settings(String url, int itemsCount) {
        this.url = url;
        this.itemsCount = itemsCount;
    }

    public String getUrl() {
        return url;
    }

    public int getItemsCount() {
        return itemsCount;
    }
}
