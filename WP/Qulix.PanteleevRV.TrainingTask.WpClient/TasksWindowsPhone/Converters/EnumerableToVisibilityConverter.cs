using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Windows;
using System.Windows.Data;

namespace TasksWindowsPhone.Converters
{
    /// <summary>
    /// Класс-конвертер для преобразования коллекций в Visibility
    /// </summary>
    public class EnumerableToVisibilityConverter : IValueConverter
    {
        /// <summary>
        /// Преобразует коллекцию в Visibility
        /// </summary>
        /// <param name="value">Коллекция</param>
        /// <param name="targetType">Тип, к которому надо преобразовать значение value</param>
        /// <param name="parameter">Вспомогательный параметр</param>
        /// <param name="culture">Текущая культура приложения</param>
        /// <returns></returns>
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            var collection = value as IEnumerable<object>;
            if (collection == null) return Visibility.Collapsed;
            return collection.Any() ? Visibility.Collapsed : Visibility.Visible;
        }

        /// <summary>
        ///     Не реализован
        /// </summary>
        /// <exception cref="NotImplementedException"></exception>
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
