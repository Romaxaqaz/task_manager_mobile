using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using TasksWindowsPhone.CustomControls.MessageDialog;
using TasksWindowsPhone.Helpers;
using TasksWindowsPhone.Model;
using TasksWindowsPhone.Resources;

namespace TasksWindowsPhone
{
    /// <summary>
    ///     Главная страница приложения. Список задач.
    /// </summary>
    public partial class MainPage
    {
        #region Constructor

        public MainPage()
        {
            // задержка для отображения Splash Screen
            Thread.Sleep(1000);

            InitializeComponent();
            DataContext = this;
            Loaded += MainPage_Loaded;
        }

        #endregion

        #region Page Navigation

        private void MainPage_Loaded(object sender, RoutedEventArgs e)
        {
            UpdateTaskList();
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            // После ухода с главной страницы очищаем список задач
            ListViewTasks.ItemsSource = null;
            ApplicationBar.IsVisible = false;
        }

        #endregion

        #region Control events

        private void UpdateButton_Click(object sender, EventArgs e)
        {
            UpdateTaskList();
        }

        private void AddNewTaskButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri("/Views/ActionsTaskPage.xaml", UriKind.Relative));
        }

        private void SettingButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri("/Views/ConfigurationPage.xaml", UriKind.Relative));
        }

        private void AboutButton_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate(new Uri("/Views/AboutAppPage.xaml", UriKind.Relative));
        }

        private void EditContextMenu_OnClick(object sender, RoutedEventArgs e)
        {
            var task = ((FrameworkElement)sender).DataContext as Task;
            NavigationService.Navigate(new Uri(string.Format("/Views/ActionsTaskPage.xaml?taskId={0}", task.Id), UriKind.Relative));
        }

        private void DeleteContextMenu_OnClick(object sender, RoutedEventArgs e)
        {
            var messageDialog = new MessageDialog(AppResources.DeleteMessage, AppResources.OkButton, AppResources.CancelButton).Show();
            messageDialog.Dismissed += (dialog, dialogArgs) =>
            {
                if (dialogArgs.Result.Equals(MessageDialogResult.LeftButton))
                {
                    var task = ((FrameworkElement)sender).DataContext as Task;

                    DispatcherHelper.ExecuteOnUIThreadAsync(() =>
                    {
                        App.Server.Delete(task.Id);
                        UpdateTaskListIndicator(App.Server.GetList());
                    },
                    () => App.ProgressBar.ShowModal(),
                    () => App.ProgressBar.HideModal());
                }
            };
        }

        private void ListViewTasks_OnSelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var taskItem = (sender as ListBox).SelectedItem;
            if (taskItem == null) return;

            NavigationService.Navigate(new Uri(string.Format("/Views/ActionsTaskPage.xaml?taskId={0}", (taskItem as Task).Id), UriKind.Relative));
        }

        #endregion

        #region Methods

        private void UpdateTaskList()
        {
            ListContainIndicator.Visibility = Visibility.Collapsed;

            DispatcherHelper.ExecuteOnUIThreadAsync(() =>
            {
                UpdateTaskListIndicator(App.Server.GetList());
            },
            () => App.ProgressBar.ShowModal(),
            () => App.ProgressBar.HideModal());
        }

        private void UpdateTaskListIndicator(List<Task> taskList)
        {
            if (taskList.Any())
            {
                ListContainIndicator.Visibility = Visibility.Collapsed;
                ListViewTasks.ItemsSource = taskList;
            }
            else
            {
                ListViewTasks.ItemsSource = null;
                ListContainIndicator.Visibility = Visibility.Visible;
            }
        }

        #endregion
    }
}