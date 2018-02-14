using System;
using System.Collections.Generic;
using System.Globalization;
using System.Windows.Data;
using TasksWindowsPhone.Helpers;
using TasksWindowsPhone.Model;

namespace TasksWindowsPhone.Converters
{
    /// <summary>
    ///     Класс-конвертер для преобразования статуса в иконку.
    /// </summary>
    public class StatusToIcoConverter : IValueConverter
    {
        public static Dictionary<TaskStatus, string> StatusIcoDictionary = new Dictionary<TaskStatus, string>
        {
            {TaskStatus.NotStarted, "/Assets/Ico/questionmark.png"},
            {TaskStatus.InProcess, "/Assets/Ico/feature.alarm.png"},
            {TaskStatus.Completed, "/Assets/Ico/check.png"},
            {TaskStatus.Postponed, "/Assets/Ico/refresh.png"}
        };

        /// <summary>
        ///     Преобразовывает статус в иконку.
        /// </summary>
        /// <param name="value">Статус задачи</param>
        /// <param name="targetType">Тип, к которому надо преобразовать значение value</param>
        /// <param name="parameter">Вспомогательный параметр</param>
        /// <param name="culture">Текущая культура приложения</param>
        /// <returns>Ссылка на иконку</returns>
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return StatusIcoDictionary[EnumUtils.Parse<TaskStatus>(value.ToString())];
        }

        /// <summary>
        ///    Не реализован
        /// </summary>
        /// <param name="value"></param>
        /// <param name="targetType"></param>
        /// <param name="parameter"></param>
        /// <param name="culture"></param>
        /// <returns></returns>
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
