using System;

namespace TasksWindowsPhone.Helpers
{
    /// <summary>
    /// Класс для работы с перечислением
    /// </summary>
    public static class EnumUtils
    {
        /// <summary>
        ///     Преобразует строковое представление в перечислимый объект
        /// </summary>
        /// <typeparam name="T">Enum к которому нужно привести значение</typeparam>
        /// <param name="value">строка для преобразования в Enum</param>
        /// <returns>Enum</returns>
        public static T Parse<T>(string value)
        {
            return (T)Enum.Parse(typeof(T), value);
        }
    }
}
