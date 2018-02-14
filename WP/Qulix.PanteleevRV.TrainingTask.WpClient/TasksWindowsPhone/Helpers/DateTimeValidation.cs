using System;
using System.Globalization;

namespace TasksWindowsPhone.Helpers
{
    /// <summary>
    ///     Класс для валидации даты.
    /// </summary>
    public class DateTimeValidation
    {
        private const string DateFormat = "yyyy-MM-dd";

        /// <summary>
        ///     Проверяет строку на корректность формата даты. Формат - ГГГГ-ММ-ДД.
        /// </summary>
        /// <param name="date">Строковое представление даты</param>
        /// <returns>true/false</returns>
        public static bool CheckDateStringFormat(string date)
        {
            if (string.IsNullOrEmpty(date))
                return false;

            DateTime dateTime;
            return DateTime.TryParseExact(date, DateFormat, null, DateTimeStyles.None, out dateTime);
        }
    }
}
