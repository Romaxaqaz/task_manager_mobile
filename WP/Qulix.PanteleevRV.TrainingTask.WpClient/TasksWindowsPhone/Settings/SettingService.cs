using System;
using System.IO.IsolatedStorage;
using TasksWindowsPhone.Helpers;

namespace TasksWindowsPhone.Settings
{
    /// <summary>
    ///     Класс для работы с настройками в локальном хранилище.
    /// </summary>
    public static class SettingService
    {
        private static readonly IsolatedStorageSettings IsolatedStorageSettings = IsolatedStorageSettings.ApplicationSettings;

        private const string UrlServerKey = "URL_SERVER_KEY";
        private const string NumberTaskKey = "NUMBER_TASK_KEY";
        private const string IsRestartAppKey = "IS_RESTART_APP_KEY";

        private const string DefaultServerUrl = "ServerUrl";
        private const string DefaultCountTask = "MaxTaskCounInPage";

        /// <summary>
        ///     URL адрес сервера, хранимый в настройках приложения.
        /// </summary>
        /// <remarks>По умолчанию возвращает значение из файла App.config</remarks>
        public static string UrlServer
        {
            set
            {
                if (!UrlValidation.Validate(value))
                    throw new ArgumentException("Incorrect Server Url");

                SetParamsSetting(UrlServerKey, value);
            }
            get
            {
                var urlServer = GetParamsSetting(UrlServerKey);
                return urlServer != null ? urlServer.ToString() : MobileConfiguration.AppSettings[DefaultServerUrl];
            }
        }

        /// <summary>
        ///     Количество задач для отображения в списке, хранимое в настройках приложения.
        /// </summary>
        /// <remarks>По умолчанию возвращает значение из файла App.config</remarks>
        public static int NumberTasks
        {
            set { SetParamsSetting(NumberTaskKey, value); }
            get
            {
                var number = GetParamsSetting(NumberTaskKey);
                return number != null
                    ? Convert.ToInt32(number)
                    : int.Parse(MobileConfiguration.AppSettings[DefaultCountTask]);
            }
        }

        /// <summary>
        ///     Свойство для определения необходимости перезапуска приложения в случае изменения настроек.
        /// </summary>
        public static bool IsRestartApp
        {
            set { SetParamsSetting(IsRestartAppKey, value); }
            get
            {
                var isRestart = GetParamsSetting(IsRestartAppKey) ?? "false";
                return bool.Parse(isRestart.ToString());
            }
        }

        /// <summary>
        ///     Сохраняет значение параметра в изолированное хранилище.
        /// </summary>
        /// <param name="key">ключ параметра</param>
        /// <param name="value">значение параметра</param>
        public static void SetParamsSetting(string key, object value)
        {
            if (IsolatedStorageSettings.Contains(key))
            {
                IsolatedStorageSettings.Remove(key);
                IsolatedStorageSettings.Add(key, value);
            }
            else
            {
                IsolatedStorageSettings.Add(key, value);
            }
            IsolatedStorageSettings.Save();
        }

        /// <summary>
        ///     Получает значение параметра из изолированного хранилища.
        /// </summary>
        /// <param name="key">ключ параметра</param>
        /// <returns></returns>
        public static object GetParamsSetting(string key)
        {
            return IsolatedStorageSettings.Contains(key) ? IsolatedStorageSettings[key] : null;
        }

        /// <summary>
        ///     Удаляет значение из изолированного хранилища.
        /// </summary>
        /// <param name="key">ключ параметра</param>
        public static void RemoveparamsSetting(string key)
        {
            IsolatedStorageSettings.Remove(key);
        }
    }
}
