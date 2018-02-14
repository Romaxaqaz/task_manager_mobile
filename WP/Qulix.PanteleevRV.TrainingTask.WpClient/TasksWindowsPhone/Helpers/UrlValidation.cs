using System;

namespace TasksWindowsPhone.Helpers
{
    /// <summary>
    ///     Класс для валидации URL адресов.
    /// </summary>
    public static class UrlValidation
    {
        /// <summary>
        ///     Проверяет url адрес на корректность.
        /// </summary>
        /// <param name="url">url адрес</param>
        /// <returns></returns>
        public static bool Validate(string url)
        {
            Uri uri;
            return Uri.TryCreate(url, UriKind.Absolute, out uri);
        }
    }
}
