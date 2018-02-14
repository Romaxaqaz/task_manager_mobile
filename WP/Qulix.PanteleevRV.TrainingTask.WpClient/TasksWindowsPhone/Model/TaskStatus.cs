namespace TasksWindowsPhone.Model
{
    /// <summary>
    ///     Cтатус задачи
    /// </summary>
    public enum TaskStatus
    {
        /// <summary>
        ///     Статус 'Не начата'
        /// </summary>
        NotStarted,

        /// <summary>
        ///     Статус 'В процессе'
        /// </summary>
        InProcess,

        /// <summary>
        ///     Статус 'Завершена'
        /// </summary>
        Completed,

        /// <summary>
        ///     Статус 'Отложена'
        /// </summary>
        Postponed
    }
}
