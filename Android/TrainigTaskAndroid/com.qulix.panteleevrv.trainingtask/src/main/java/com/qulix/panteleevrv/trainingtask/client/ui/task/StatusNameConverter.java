package com.qulix.panteleevrv.trainingtask.client.ui.task;

import java.util.HashMap;
import java.util.Map;

import com.qulix.panteleevrv.trainingtask.R;
import com.qulix.panteleevrv.trainingtask.model.Status;

/**
 * Класс конвертер для получения названия статуса задачи
 *
 * @author Q-RPA
 */
class StatusNameConverter {

    private static final Map<Status, Integer> STATUS_NAME_HASH_MAP = new HashMap<>();

    StatusNameConverter() {
        STATUS_NAME_HASH_MAP.put(Status.NOT_STARTED, R.string.status_not_started);
        STATUS_NAME_HASH_MAP.put(Status.IN_PROCESS, R.string.status_in_process);
        STATUS_NAME_HASH_MAP.put(Status.COMPLETED, R.string.status_completed);
        STATUS_NAME_HASH_MAP.put(Status.REJECTED, R.string.status_postponed);
    }

    /**
     * Возвращает идентификатор ресурса названия статуса из словаря {@link StatusNameConverter#STATUS_NAME_HASH_MAP}
     *
     * @param status статус задачи
     * @return идентификатор ресурса названия статуса
     */
    int convert(Status status) {
        return STATUS_NAME_HASH_MAP.get(status);
    }
}
