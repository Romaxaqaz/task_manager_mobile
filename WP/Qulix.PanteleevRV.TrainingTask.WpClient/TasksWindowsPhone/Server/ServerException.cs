using System;

namespace TasksWindowsPhone.Server
{
    /// <summary>
    /// Класс серверной ошибки.
    /// </summary>
    public class ServerException : Exception
    {
        /// <summary>
        ///     Конструктор класса
        /// </summary>
        /// <param name="message">Текст сообщения ошибки</param>
        public ServerException(string message) : base(message)
        {
        }
    }
}
