using System.Collections.Generic;
using TasksWindowsPhone.Model;

namespace TasksWindowsPhone.Server
{
    /// <summary>
    ///     Интерфейс взаимодействия с сервером.
    /// </summary>
    public interface IServer
    {
        /// <summary>
        ///     Добавляет задачу
        /// </summary>
        /// <param name="task">Задача, которую необходимо добавить</param>
        void Add(Task task);

        /// <summary>
        ///     Возвращает список задач
        /// </summary>
        /// <returns>Список задач</returns>
        List<Task> GetList();

        /// <summary>
        ///     Обновляет задачу на сервере
        /// </summary>
        /// <param name="task">Задача, которую необходимо обновить</param>
        void Update(Task task);

        /// <summary>
        ///     Удаляет задачу на сервере
        /// </summary>
        /// <param name="id">ID задачи, которую необходимо удалить</param>
        void Delete(int id);

        /// <summary>
        ///     Возвращает задачу по Id
        /// </summary>
        /// <param name="id">ID задачи, которую необходимо получить</param>
        /// <returns>Задачу</returns>
        Task Get(int id);
    }
}
