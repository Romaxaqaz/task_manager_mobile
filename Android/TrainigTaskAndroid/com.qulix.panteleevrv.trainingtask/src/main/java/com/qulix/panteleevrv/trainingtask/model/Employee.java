package com.qulix.panteleevrv.trainingtask.model;

import java.io.Serializable;

/**
 * Класс, описывающий сущность "Сотрудник".
 * <p>Содержит следующую информацию о сотруднике:</p>
 * <ul>
 *     <li>Имя</li>
 *     <li>Фамилия</li>
 *     <li>Должность</li>
 * </ul>
 *
 * @author Q-RPA
 */
public class Employee implements Serializable {

    /**
     * Уникальный идентификатор сотрудника
     */
    private String id;

    /**
     * Имя сотрудника.
     * <u>Не может быть пустым</u>
     */
    private String name;

    /**
     * Фамилия сотрудника
     * <u>Не может быть пустым</u>
     */
    private String surname;

    /**
     *  Занимаемая должность сотрудника
     *  <u>Не может быть пустым</u>
     */
    private String position;

    public Employee() {
    }

    public Employee(String name, String surname, String position) {
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public Employee(Employee employee) {
        this(employee.getName(), employee.getSurname(), employee.getPosition());
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;

        return id != null ? id.equals(employee.id) : employee.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
