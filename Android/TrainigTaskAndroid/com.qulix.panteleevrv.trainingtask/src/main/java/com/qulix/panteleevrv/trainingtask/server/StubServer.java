package com.qulix.panteleevrv.trainingtask.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.min;

import com.qulix.panteleevrv.trainingtask.client.utils.ObjectUtils;
import com.qulix.panteleevrv.trainingtask.client.utils.ThreadUtils;
import com.qulix.panteleevrv.trainingtask.model.Employee;
import com.qulix.panteleevrv.trainingtask.model.Project;
import com.qulix.panteleevrv.trainingtask.model.Status;
import com.qulix.panteleevrv.trainingtask.model.Task;

/**
 * Реализация стаб сервера
 *
 * @author Q-RPA
 */
public class StubServer implements Server, HttpConnection {

    private static final int DEFAULT_ITEMS_COUNT = 5;
    private static final int THREAD_SLEEP_TIME = 1000;
    private static final String ERROR_URL = "error";

    private final List<Task> taskList = new ArrayList<>();
    private final List<Project> projectList = new ArrayList<>();
    private final List<Employee> employeeList = new ArrayList<>();

    private final AtomicInteger lastTaskId = new AtomicInteger();
    private final AtomicInteger lastEmployeeId = new AtomicInteger();
    private final AtomicInteger lastProjectId = new AtomicInteger();

    private final ConnectionConfig connectionConfig;

    public StubServer(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;

        fillStub();
    }

    @Override
    public void addTask(Task task) throws ServerException {
        ObjectUtils.checkNull(task, "The task to add to the server can not be null!");
        checkErrorUrl();
        delayServer();

        Task newTask = new Task(task);
        newTask.setId(generateId(lastTaskId));

        synchronized (this) {
            taskList.add(newTask);
        }
    }

    @Override
    public void deleteTask(Task task) throws ServerException {
        ObjectUtils.checkNull(task, "The task to delete to the server can not be null!");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            removeListItem(taskList, task, task.getId());
        }
    }

    @Override
    public void updateTask(Task task) throws ServerException {
        ObjectUtils.checkNull(task, "The task to update to the server can not be null");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            updateListItem(taskList, task, task.getId());
        }
    }

    @Override
    public List<Task> getTasks(int count) throws ServerException {
        delayServer();
        checkErrorUrl();

        synchronized (this) {
            return getSubList(taskList, count);
        }
    }

    @Override
    public List<Task> getProjectTasks(Project project, int count) throws ServerException {
        ObjectUtils.checkNull(project, "Project for task mapping can not be null");
        delayServer();
        checkErrorUrl();

        List<Task> projectTasks = new ArrayList<>();
        synchronized (this) {
            for (Task task : taskList) {
                if (task.getProject().equals(project)) {
                    projectTasks.add(task);
                }
            }
        }

        return getSubList(projectTasks, count);
    }

    @Override
    public void addProject(Project project) throws ServerException {
        ObjectUtils.checkNull(project, "The project to add to the server can not be null");
        checkErrorUrl();
        delayServer();

        Project newProject = new Project(project);
        newProject.setId(generateId(lastProjectId));

        synchronized (this) {
            projectList.add(newProject);
        }
    }

    @Override
    public void deleteProject(Project project) throws ServerException {
        ObjectUtils.checkNull(project, "The project to delete to the server can not be null");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            removeListItem(projectList, project, project.getId());
            removeTaskProject(project);
        }
    }

    @Override
    public void updateProject(Project project) throws ServerException {
        ObjectUtils.checkNull(project, "The project to update to the server can not be null");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            updateListItem(projectList, project, project.getId());
            updateProjectInTasks(project);
        }
    }

    @Override
    public List<Project> getProjects(int count) throws ServerException {
        delayServer();
        checkErrorUrl();

        synchronized (this) {
            return getSubList(projectList, count);
        }
    }

    @Override
    public void addEmployee(Employee employee) throws ServerException {
        ObjectUtils.checkNull(employee, "The employee to add to the server can not be null");
        checkErrorUrl();
        delayServer();

        Employee newEmployee = new Employee(employee);
        newEmployee.setId(generateId(lastEmployeeId));

        synchronized (this) {
            employeeList.add(newEmployee);
        }
    }

    @Override
    public void deleteEmployee(Employee employee) throws ServerException {
        ObjectUtils.checkNull(employee, "The employee to delete to the server can not be null");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            removeListItem(employeeList, employee, employee.getId());
            removeTaskEmployee(employee);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws ServerException {
        ObjectUtils.checkNull(employee, "The employee to update to the server can not be null");
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            updateListItem(employeeList, employee, employee.getId());
        }
    }

    @Override
    public List<Employee> getEmployees(int count) throws ServerException {
        checkErrorUrl();
        delayServer();

        synchronized (this) {
            return getSubList(employeeList, count);
        }
    }

    @Override
    public void update(ConnectionConfig config) {
        connectionConfig.setUrl(config.getUrl());
    }

    private <T> List<T> getSubList(List<T> list, int count) throws ServerException {
        if (count <= 0) {
            throw new ServerException("The number of requested records from the server must be greater than 0");
        }

        int listSize = list.size();
        return new ArrayList<>(list.subList(0, min(listSize, count)));
    }

    private String generateId(AtomicInteger lastId) {
        return String.valueOf(lastId.getAndIncrement());
    }

    private void updateProjectInTasks(Project project) {
        for (Task task : taskList) {
            if (task.getProject().equals(project)) {
                task.setProject(project);
            }
        }
    }

    private void removeTaskEmployee(Employee employee) {
        for (Task task : taskList) {
            if (task.getEmployee().equals(employee)) {
                task.setEmployee(null);
            }
        }
    }

    private void removeTaskProject(Project project) {
        for (Task task : taskList) {
            if (task.getProject().equals(project)) {
                task.setProject(null);
            }
        }
    }

    private <T> void updateListItem(List<T> list, T item, String id) throws ServerException {
        int index = list.indexOf(item);
        if (index == -1) {
            throw new NotFoundException(String.format(new Locale("en"),
                "The %s list item for update with ID %s was not found.", item.getClass().getSimpleName(), id));
        }

        list.set(index, item);
    }

    private <T> void removeListItem(List<T> list, T item, String id) throws ServerException {
        if (!list.remove(item)) {
            throw new NotFoundException(String.format(new Locale("en"),
                "The %s list item for deletion with ID %s was not found.", item.getClass().getSimpleName(), id));
        }
    }

    private void checkErrorUrl() throws ServerException {
        if (connectionConfig.getUrl().contains(ERROR_URL)) {
            throw new ConnectionException(String.format(
                "An error occurred while trying to connect to the server. Check the server address %s",
                connectionConfig.getUrl()));
        }
    }

    private void fillStub() {
        Employee employee;
        Project project;
        Task task;

        for (int i = 0; i < DEFAULT_ITEMS_COUNT; i++) {
            employee = new Employee("Name" + i, "Surname" + i, "Position");
            employee.setId(generateId(lastEmployeeId));

            project = new Project("Project" + i, "pr" + i);
            project.setId(generateId(lastProjectId));

            task = new Task("Task name" + i, 10, new Date(), new Date(), getRandomStatus(), employee, project);
            task.setId(generateId(lastTaskId));

            project.addTask(task);

            employeeList.add(employee);
            taskList.add(task);
            projectList.add(project);
        }
    }

    private Status getRandomStatus() {
        return Status.values()[new Random().nextInt(Status.values().length)];
    }

    private void delayServer() {
        ThreadUtils.sleep(THREAD_SLEEP_TIME);
    }
}
