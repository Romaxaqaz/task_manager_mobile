using System;

namespace TasksWindowsPhone.Helpers
{
    /// <summary>
    ///     Класс методов расширений
    /// </summary>
    public static class ExtensionMethods
    {
        /// <summary>
        ///     Метод расширения для проверки объекта на null
        /// </summary>
        /// <typeparam name="T">Тип объекта</typeparam>
        /// <param name="validationObject">Объект проверки</param>
        /// <param name="errorMessage">Сообщение исключения</param>
        /// <exception cref="ArgumentException"></exception>
        /// <returns>Возвращает текущий объект</returns>
        public static T NotNull<T>(this object validationObject, string errorMessage = "")
        {
            if (validationObject == null || string.IsNullOrWhiteSpace(validationObject.ToString()))
                throw new ArgumentException(errorMessage);

            return (T)validationObject;
        }

        /// <summary>
        ///     Метод расширения для конвертации string в int
        /// </summary>
        /// <param name="number">Число в строковом представлении</param>
        /// <param name="message">Сообщение ошибки</param>
        /// <exception cref="ArgumentException">В случае невозможности конвертации или при отрицательном значении</exception>
        /// <returns>Значение типа int</returns>
        public static int ToPositiveInt(this string number, string message = "")
        {
            int countTask;

            if (!int.TryParse(number, out countTask) || countTask < 0)
                throw new ArgumentException(message);

            return countTask;
        }
    }
}
