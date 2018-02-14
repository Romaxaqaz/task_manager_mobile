using System;
using System.Globalization;
using System.Windows;
using System.Windows.Data;

namespace TasksWindowsPhone.Converters
{
    /// <summary>
    ///     Класс-конвертер для преобразования bool в Visibility.
    /// </summary>
    public class BooleanToVisibilityConverter : IValueConverter
    {
        /// <summary>
        ///     Преобразовывает bool в Visibility.
        /// </summary>
        /// <param name="value">Булевское значение</param>
        /// <param name="targetType">Тип, к которому надо преобразовать значение value</param>
        /// <param name="parameter">Вспомогательный параметр</param>
        /// <param name="culture">Текущая культура приложения</param>
        /// <returns>Visibility</returns>
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return Convert((bool)value);
        }

        /// <summary>
        ///     Не реализован
        /// </summary>
        /// <exception cref="NotImplementedException"></exception>
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        ///     Преобразовывает bool в Visibility.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static Visibility Convert(bool value)
        {
            return value ? Visibility.Visible : Visibility.Collapsed;
        }
    }
}
