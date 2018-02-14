package com.qulix.panteleevrv.trainingtask.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс, описывающий сущность "Задача".
 * <p>Содержит следующую информацию о задаче:</p>
 * <ul>
 *     <li>Название</li>
 *     <li>Длительность выполнения задачи</li>
 *     <li>Дата начала задачи</li>
 *     <li>Дата завершения задачи</li>
 *     <li>Статус задачи. {@link Status}</li>
 *     <li>Сотрудник, назначенный на выполнение задачи. {@link Employee}</li>
 *     <li>Проект для выполнения задачи. {@link Project}</li>
 * </ul>
 *
 * @author Q-RPA
 */
public class Task implements Serializable {

    /**
     * Уникальный идентификатор задачи
     */
    private String id;

    /**
     * Название задачи
     * <u>Не может быть пустым</u>
     */
    private String name;

    /**
     * Длительность задачи
     * <u>Не может быть равным 0</u>
     */
    private int time;

    /**
     * Дата начала задачи
     * <u>Не может быть null</u>
     */
    private Date start;

    /**
     * Дата завершения задачи
     * <u>Не может быть null</u>
     */
    private Date end;

    /**
     * Статус задачи
     */
    private Status status;

    /**
     * Сотрудник, назначенный на выполнение задачи
     * <u>Не может быть null</u>
     */
    private Employee employee;

    /**
     * Проект для выполнения задачи
     * <u>Не может быть null</u>
     */
    private Project project;

    public Task() {
    }

    public Task(String name, int time, Date start, Date end, Status status, Employee employee, Project project) {
        this.name = name;
        this.time = time;
        this.start = start;
        this.end = end;
        this.status = status;
        this.employee = employee;
        this.project = project;
    }

    public Task(Task task) {
        this(task.getName(), task.getTime(), task.getStart(), task.getEnd(), task.getStatus(),
            task.getEmployee(), task.getProject());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        return id != null ? id.equals(task.id) : task.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
