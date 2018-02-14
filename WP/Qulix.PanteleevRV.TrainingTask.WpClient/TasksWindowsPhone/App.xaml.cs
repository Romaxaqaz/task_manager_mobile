using System;
using System.Threading;
using System.Windows;
using System.Windows.Markup;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using TasksWindowsPhone.CustomControls;
using TasksWindowsPhone.Helpers;
using TasksWindowsPhone.Resources;
using TasksWindowsPhone.Server;
using TasksWindowsPhone.Settings;

namespace TasksWindowsPhone
{
    /// <summary>
    ///     Класс инициализации приложения
    /// </summary>
    public partial class App
    {
        #region Fields

        private bool _phoneApplicationInitialized;

        #endregion

        #region Properties

        private static PhoneApplicationFrame _rootFrame;
        /// <summary>
        ///     Frame приложения.
        /// </summary>
        public static PhoneApplicationFrame RootFrame
        {
            get { return _rootFrame ?? (_rootFrame = new TransitionFrame()); }
        }

        private static IServer _server;
        /// <summary>
        ///     Интерфейс сервера.
        /// </summary>
        public static IServer Server
        {
            get { return _server ?? (_server = new StubServer(SettingService.UrlServer, SettingService.NumberTasks)); }
        }

        private static ModalProgressBar _progressBar;
        /// <summary>
        ///     Индикатор выполнения
        /// </summary>
        public static ModalProgressBar ProgressBar
        {
            get { return _progressBar ?? (_progressBar = new ModalProgressBar(RootFrame)); }
        }

        #endregion

        #region Constructor

        public App()
        {
            UnhandledException += Application_UnhandledException;

            // XAML инициализация
            InitializeComponent();

            // Инициализация приложения. Установка стартовой страницы.
            InitializePhoneApplication();

            // Инициализация языка приложения
            InitializeLanguage();

            Current.Host.Settings.EnableFrameRateCounter = false;
            PhoneApplicationService.Current.UserIdleDetectionMode = IdleDetectionMode.Disabled;
        }

        #endregion

        #region Phone application initialization

        private void InitializePhoneApplication()
        {
            if (_phoneApplicationInitialized) return;

            RootFrame.Navigated += CompleteInitializePhoneApplication;
            RootFrame.Navigated += CheckForResetNavigation;
            _phoneApplicationInitialized = true;

            RootFrame.Navigate(new Uri("/MainPage.xaml", UriKind.RelativeOrAbsolute));
        }

        private void CompleteInitializePhoneApplication(object sender, NavigationEventArgs e)
        {
            if (RootVisual != RootFrame)
                RootVisual = RootFrame;

            RootFrame.Navigated -= CompleteInitializePhoneApplication;
        }

        private void CheckForResetNavigation(object sender, NavigationEventArgs e)
        {
            if (e.NavigationMode == NavigationMode.Reset)
                RootFrame.Navigated += ClearBackStackAfterReset;
        }

        private void ClearBackStackAfterReset(object sender, NavigationEventArgs e)
        {
            RootFrame.Navigated -= ClearBackStackAfterReset;
        }

        #endregion

        #region Phone language initialization

        private void InitializeLanguage()
        {
            RootFrame.Language = XmlLanguage.GetLanguage(AppResources.ResourceLanguage);
            Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo(AppResources.ResourceLanguage);
            Thread.CurrentThread.CurrentUICulture = new System.Globalization.CultureInfo(AppResources.ResourceLanguage);
            var flow = EnumUtils.Parse<FlowDirection>(AppResources.ResourceFlowDirection);
            RootFrame.FlowDirection = flow;
        }

        #endregion

        #region Launching App

        private void PhoneApplicationService_OnLaunching(object sender, LaunchingEventArgs e)
        {
            if (SettingService.IsRestartApp)
                SettingService.IsRestartApp = false;
        }

        #endregion

        #region Exception

        private void Application_UnhandledException(object sender, ApplicationUnhandledExceptionEventArgs e)
        {
            MessageBox.Show(e.ExceptionObject.Message, string.Empty, MessageBoxButton.OK);
            e.Handled = true;
        }

        #endregion
    }
}