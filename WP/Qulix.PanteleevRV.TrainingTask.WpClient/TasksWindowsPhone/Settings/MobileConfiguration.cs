using System.Collections.Generic;
using System.Xml;

namespace TasksWindowsPhone.Settings
{
    /// <summary>
    ///     Класс для получения информации о конфигурации приложения. Файл App.config
    /// </summary>
    public static class MobileConfiguration
    {
        private const string AppConfigFileName = "App.config";

        private static readonly Dictionary<string, string> _settings = new Dictionary<string, string>();
        /// <summary>
        ///     Все параметры в файле App.config
        /// </summary>
        public static Dictionary<string, string> AppSettings
        {
            get { return _settings; }
        }

        static MobileConfiguration()
        {
            var myXmlReader = XmlReader.Create(AppConfigFileName);

            while (myXmlReader.ReadToFollowing("add"))
                _settings.Add(myXmlReader.GetAttribute("key"), myXmlReader.GetAttribute("value"));
        }
    }
}
