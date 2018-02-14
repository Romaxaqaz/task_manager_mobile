package com.qulix.panteleevrv.trainingtask.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, описывающий сущность "Проект".
 * <p>Содержит следующую информацию о проекте:</p>
 * <ul>
 *     <li>Название</li>
 *     <li>Сокращенное название</li>
 *     <li>Список задач принадлежащих проекту</li>
 * </ul>
 *
 * @author Q-RPA
 */
public class Project implements Serializable {

    /**
     * Уникальный идентификатор проекта
     */
    private String id;

    /**
     * Название проекта
     * <u>Не может быть пустым</u>
     */
    private String name;

    /**
     *  Короткое название проекта
     *  <u>Не может быть пустым</u>
     */
    private String shortName;

    /**
     * Задачи проекта
     */
    private List<Task> projectTasks = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public Project(Project project) {
        this(project.getName(), project.getShortName());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Task> getProjectTasks() {
        return this.projectTasks;
    }

    public void setProjectTasks(List<Task> projectTasks) {
        this.projectTasks = projectTasks;
    }

    public void addTask(Task task) {
        this.projectTasks.add(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        return id != null ? id.equals(project.id) : project.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
