using System;
using System.Windows;
using TasksWindowsPhone.Converters;
using TasksWindowsPhone.CustomControls.MessageDialog;
using TasksWindowsPhone.Helpers;
using TasksWindowsPhone.Resources;
using TasksWindowsPhone.Settings;

namespace TasksWindowsPhone.Views
{
    /// <summary>
    ///     Страница для работы с настройками приложения.
    /// </summary>
    public partial class ConfigurationPage
    {
        #region Constructor

        /// <summary>
        ///     Конструктор класса ConfigurationPage
        /// </summary>
        public ConfigurationPage()
        {
            InitializeComponent();
            DataContext = this;

            RestartAppTextBlock.Visibility = BooleanToVisibilityConverter.Convert(SettingService.IsRestartApp);

            UrlServerText.Text = SettingService.UrlServer;
            NumberTaskTextBox.Text = SettingService.NumberTasks.ToString();
        }

        #endregion

        #region Methods

        private void SaveButton_Click(object sender, EventArgs e)
        {
            SettingService.UrlServer = UrlServerText.Text.Trim();
            SettingService.NumberTasks = NumberTaskTextBox.Text.ToPositiveInt("Incorrect Count tasks on page");
            
            ShowSettingDialog();
        }

        private void ShowSettingDialog()
        {
            var messageDialog = new MessageDialog(AppResources.RestartAppContent, AppResources.OkButton, AppResources.NotNowButton).Show();
            messageDialog.Dismissed += (dialog, dialogArgs) =>
            {
                if (dialogArgs.Result.Equals(MessageDialogResult.LeftButton))
                {
                    // закрываем приложение
                    Application.Current.Terminate();
                }
                else
                {
                    SetRestartApp();
                }
            };
        }

        private void SetRestartApp()
        {
            SettingService.IsRestartApp = true;
            RestartAppTextBlock.Visibility = Visibility.Visible;
        }

        #endregion
    }
}