using System.Collections.Generic;
using System.Linq;
using TasksWindowsPhone.Model;

namespace TasksWindowsPhone.Server
{
    /// <summary>
    ///     Класс стаб сервер.
    /// </summary>
    public class StubServer : IServer
    {
        #region Fields

        private readonly List<Task> _userTasks = new List<Task>();          // список задач
        private string _urlServer;                                          // адрес сервера
        private readonly int _countTaskView;                                // количество выводимых задач

        #endregion

        #region Constructors

        /// <summary>
        ///     Конструктор класса StubServer
        /// </summary>
        /// <param name="urlServer">Адрес сервера</param>
        /// <param name="taskCountView">Количество выводимых задач</param>
        public StubServer(string urlServer, int taskCountView)
        {
            _urlServer = urlServer;
            _countTaskView = taskCountView;
        }

        #endregion

        #region Methods

        /// <summary>
        ///     Добавляет задачу на сервер
        /// </summary>
        /// <exception cref="ServerException">В случае возникновения ошибки при добавлении задачи</exception>
        /// <param name="task">Новая задача</param>
        public void Add(Task task)
        {
            if (task == null) throw new ServerException("Added task can not be null");

            task.Id = GenerateId();
            _userTasks.Add(task);
        }

        /// <summary>
        ///     Обновляет задачу на сервере
        /// </summary>
        /// <exception cref="ServerException">В случае возникновения ошибки при обновлении задачи</exception>
        /// <param name="task">Задача, которую необходимо обновить</param>
        public void Update(Task task)
        {
            if (task == null) throw new ServerException("Edited task can not be null");

            var taskIndex = _userTasks.FindIndex(x => x.Id.Equals(task.Id));
            _userTasks[taskIndex] = task;
        }

        /// <summary>
        ///     Удаляет задачу на сервере.
        /// </summary>
        /// <param name="id">Идентификатор задачи, которую необходимо удалить</param>
        /// <exception cref="ServerException">В случае, если задача не найдена</exception>
        public void Delete(int id)
        {
            var taskItem = _userTasks.FindIndex(x => x.Id.Equals(id));
            if (taskItem == -1) throw new ServerException("Task not found");

            _userTasks.RemoveAt(taskItem);
        }

        /// <summary>
        ///     Возвращает задачу по ID.
        /// </summary>
        /// <param name="id">Идентификатор задачи, которую нужно найти</param>
        /// <returns>Возвращает найденную задачу</returns>
        /// <exception cref="ServerException">В случае, если задача не найдена</exception>
        public Task Get(int id)
        {
            var task = _userTasks.FirstOrDefault(x => x.Id.Equals(id));
            if (task == null) throw new ServerException("Task not found");

            return task;
        }

        /// <summary>
        ///     Возвращает список задач.
        /// </summary>
        /// <returns>Список задач</returns>
        public List<Task> GetList()
        {
            return _userTasks.Take(_countTaskView).ToList();
        }

        private int GenerateId()
        {
            if (!_userTasks.Any()) return 0;

            var taskIndex = _userTasks.Max(x => x.Id);
            return ++taskIndex;
        }

        #endregion
    }
}
