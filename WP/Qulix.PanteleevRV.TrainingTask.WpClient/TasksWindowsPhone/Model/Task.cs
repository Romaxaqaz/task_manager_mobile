using System;
using TasksWindowsPhone.Helpers;

namespace TasksWindowsPhone.Model
{
    /// <summary>
    ///     Модель задачи
    /// </summary>
    public class Task
    {
        #region Properties

        /// <summary>
        ///     Уникальный идентификатор задачи
        /// </summary>
        public int Id { get; set; }

        private string _name = string.Empty;
        /// <summary>
        ///     Название задачи
        /// </summary>
        public string Name
        {
            get { return _name.Trim(); }
            set
            {
                _name = value.NotNull<string>("Name is required");
            }
        }

        /// <summary>
        ///     Время выполнения задачи
        /// </summary>
        public int JobTime { get; set; }

        /// <summary>
        ///     Дата начала задачи
        /// </summary>
        public DateTime StartDate { get; set; }

        /// <summary>
        ///     Дата завершения задачи
        /// </summary>
        public DateTime EndDate { get; set; }

        /// <summary>
        ///     Статус задачи
        /// </summary>
        public TaskStatus Status { get; set; }

        #endregion

        #region Constructors

        /// <summary>
        ///     Конструктор класса Task
        /// </summary>
        public Task()
        {
        }

        #endregion
    }
}
