package com.qulix.panteleevrv.trainingtask.client.ui.task;

import java.util.HashMap;
import java.util.Map;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.model.Status;

/**
 * Класс конвертер для получения иконки статуса задачи
 *
 * @author Q-RPA
 */
class StatusIconConverter {

    private static final Map<Status, Integer> STATUS_HASH_MAP = new HashMap<>();

    StatusIconConverter() {
        STATUS_HASH_MAP.put(Status.NOT_STARTED, R.drawable.not_started);
        STATUS_HASH_MAP.put(Status.IN_PROCESS, R.drawable.in_progress);
        STATUS_HASH_MAP.put(Status.COMPLETED, R.drawable.completed);
        STATUS_HASH_MAP.put(Status.REJECTED, R.drawable.rejected);
    }

    /**
     * Возвращает идентификатор ресурса статуса из словаря {@link StatusIconConverter#STATUS_HASH_MAP}
     *
     * @param status статус задачи
     * @return идентификатор ресурса статуса
     */
    int convert(Status status) {
        return STATUS_HASH_MAP.get(status);
    }
}
