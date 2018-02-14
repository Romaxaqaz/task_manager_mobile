package com.qulix.panteleevrv.trainingtask.server;

import java.util.List;

import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.model.Task;

/**
 * Интерфейс взаимодействия с сервером.
 *
 * <p>Предоставляем методы добавления, удаления, редактирования и получения списков сущностей:</p>
 * <ul>
 * <li>Проект</li>
 * <li>Задача</li>
 * <li>Исполнитель</li>
 * </ul>
 *
 * @author Q-RPA
 */
public interface Server {

    /**
     * Добавляет сущность {@link Task} на сервер.
     *
     * @param task задача для добавления на сервер. Передается без идентификатора. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при добавлении задачи.
     * @throws ConnectionException в случае недоступности сервереа.
     */
    void addTask(Task task) throws ServerException;

    /**
     * Удаляет сущность {@link Task} с сервера.
     *
     * @param task задача для удаления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при удалении задачи.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случает, когда запрашиваемая задача не найдена.
     */
    void deleteTask(Task task) throws ServerException;

    /**
     * Обновляет информацию о сущности {@link Task}.
     *
     * @param task задача для обновления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при обновелнии задачи.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случает, когда запрашиваемая задача не найдена.
     */
    void updateTask(Task task) throws ServerException;

    /**
     * Возвращает список задач.
     *
     * @param count количество возвращаемых задач. (<code>count > 0</code>).
     * @return Возвращает указанное количество задач.
     * @throws ServerException в случае ошибки при получении списка задач.
     * @throws ConnectionException в случае недоступности сервера.
     */
    List<Task> getTasks(int count) throws ServerException;

    /**
     * Возвращает список задач проекта.
     *
     * @param project проект для выборки задач. <u>Не может быть null!</u>.
     * @param count количество возвращаемых задач.
     * @return Возвращает указанное количество задач проекта.
     * @throws ServerException в случае ошибки при получении списка задач.
     * @throws ConnectionException в случае недоступности сервера.
     */
    List<Task> getProjectTasks(Project project, int count) throws ServerException;

    /**
     * Добавляет сущность {@link Project} на сервер.
     *
     * @param project проект для добавления. Передается без идентификатора. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при добавлении проекта.
     * @throws ConnectionException в случае недоступности сервера.
     */
    void addProject(Project project) throws ServerException;

    /**
     * Удаляет сущность {@link Project} с сервера.
     *
     * @param project проект для удаления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при удалении проекта.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случае, когда запрашиваемый проект не найден.
     */
    void deleteProject(Project project) throws ServerException;

    /**
     * Обновляет информацию о сущности {@link Project}.
     *
     * @param project проект для обновления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при обновлении проекта.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случае, когда запрашиваемый проект не найден.
     */
    void updateProject(Project project) throws ServerException;

    /**
     * Возвращает список проектов.
     *
     * @param count количество возвращаемых проектов. (<code>count > 0</code>).
     * @return Возвращает указанное количество проектов.
     * @throws ServerException в случае ошибки при получении списка проектов.
     * @throws ConnectionException в случае недоступности сервера.
     */
    List<Project> getProjects(int count) throws ServerException;

    /**
     * Добавляет сущность {@link Employee} на сервер.
     *
     * @param employee сотрудник для добавления. Передается без идентификатора. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при добавлении сотрудника.
     * @throws ConnectionException в случае недоступности сервера.
     */
    void addEmployee(Employee employee) throws ServerException;

    /**
     * Удаляет сущность {@link Employee} с сервера.
     *
     * @param employee сотрудник для удаления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при удалении сотрудника.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случает, когда запрашиваемый сотрудник не найден.
     */
    void deleteEmployee(Employee employee) throws ServerException;

    /**
     * Обновляет информацию о сущности {@link Employee}.
     *
     * @param employee сотрудник для обновления. <u>Не может быть null!</u>.
     * @throws ServerException в случае ошибки при обновлении сотрудника.
     * @throws ConnectionException в случае недоступности сервера.
     * @throws NotFoundException в случает, когда запрашиваемый сотрудник не найден.
     */
    void updateEmployee(Employee employee) throws ServerException;

    /**
     * Возвращает список сотрудников.
     *
     * @param count количество возвращаемых сотрудников. (<code>count > 0</code>).
     * @return Возвращает указанное количество сотрудников.
     * @throws ServerException в случае ошибки при получении списка сотрудников.
     * @throws ConnectionException в случае недоступности сервера.
     */
    List<Employee> getEmployees(int count) throws ServerException;
}
