package com.qulix.panteleevrv.trainingtask.client.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * Базовый класс валидатора сущности. Реализует основные методы для добавления и получения списка ошибок.
 *
 * <ul>
 *     <li>{@link Validator#addError(int)} добавляет ошибку валидации в список ошибок</li>
 *     <li>{@link Validator#getErrors()} получает список ошибок</li>
 *     <li>{@link Validator#isValid()} проверяет валидна ли сущность</li>
 * </ul>
 *
 * @author Q-RPA
 */
public abstract class Validator {

    private final Context context;
    private final List<String> errors;

    public Validator(Context context) {
        this.context = context;
        errors = new ArrayList<>();
    }

    /**
     * Возвращает список ошибок.
     *
     * @return список ошибок.
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Проверяет валидна ли проверяемая сущность.
     *
     * @return true - сущность валидна.
     */
    protected boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Проверяет объект на null
     *
     * @param object объект для проверки
     * @param errorId идентификатор сообщения ошибки
     * @param <T> тип объекта
     * @return возвращает проверяеимый объект
     */
    protected <T> T checkNullOrEmpty(T object, int errorId) {
        if (isNullOrEmpty(object)) {
            addError(errorId);
        }
        return object;
    }

    private boolean isNullOrEmpty(Object object) {
        return object == null || String.valueOf(object).isEmpty();
    }

    /**
     * Добавляет сообщение ошибки в список ошибок.
     *
     * @param errorId сообщени ошибки.
     */
    protected void addError(int errorId) {
        errors.add(context.getString(errorId));
    }
}
