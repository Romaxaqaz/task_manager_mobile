using System.Windows.Navigation;
using TasksWindowsPhone.Settings;

namespace TasksWindowsPhone.Views
{
    /// <summary>
    ///     Страница дополнительной информации о приложении
    /// </summary>
    public partial class AboutAppPage
    {
        public AboutAppPage()
        {
            InitializeComponent();
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            // получаем версию приложения из файла конфигурации.
            VersionTextBlock.Text = MobileConfiguration.AppSettings["Version"];
        }
    }
}