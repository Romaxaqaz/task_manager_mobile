using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using TasksWindowsPhone.CustomControls.MessageDialog;
using TasksWindowsPhone.Helpers;
using TasksWindowsPhone.Model;
using TasksWindowsPhone.Resources;

namespace TasksWindowsPhone.Views
{
    /// <summary>
    ///     Страница для работы с задачей.
    ///     Позволяет добавлять и редактировать задачи
    /// </summary>
    public partial class ActionsTaskPage : INotifyPropertyChanged
    {
        #region Fields

        private string _id;                                                 // id задачи.
        private double _scrollViewerHeight;                                 // высота ScrollViewer для возможности прокрутки элементов при открытой клавиатуре.
        private double _listPickerHeight;                                   // высота элемента выбора статуса, для учитывания общей высоты ScrollViewer.
        private bool _isFirsLoad = true;                                    // для установки высоты ScrollViewer только при первоначальной загрузке страницы.
        private readonly List<string> _errorList = new List<string>();      // список полей с ошибками

        #endregion

        #region Constructor

        public ActionsTaskPage()
        {
            InitializeComponent();
            DataContext = this;
            StartDatePicker.ValueChanged += DatePicker_ValueChanged;
            EndDatePicker.ValueChanged += DatePicker_ValueChanged;
        }

        #endregion

        #region Properties

        private string _operationName = AppResources.NewTaskButton;
        /// <summary>
        ///     Наименование операции.
        /// </summary>
        public string OperationName
        {
            get { return _operationName; }
            set
            {
                _operationName = value;
                OnPropertyChanged();
            }
        }

        private bool _changeTask;
        /// <summary>
        ///     Определение типа запроса. true - запрос на обновление, false - запрос на добавление задачи.
        /// </summary>
        public bool ChangeTask
        {
            get { return _changeTask; }
            set
            {
                _changeTask = value;
                OnPropertyChanged();
            }
        }

        private Task _newUserTask;
        /// <summary>
        ///     Задача для обновления
        /// </summary>
        public Task Task
        {
            get { return _newUserTask; }
            set
            {
                _newUserTask = value;
                OnPropertyChanged();
            }
        }

        private DateTime _startDate = DateTime.Now;
        /// <summary>
        ///     Дата начала задачи
        /// </summary>
        public DateTime StartDate
        {
            get { return _startDate; }
            set
            {
                _startDate = value;
                OnPropertyChanged();
            }
        }

        private DateTime _endDate = DateTime.Now;
        /// <summary>
        ///     Дата завершения задачи
        /// </summary>
        public DateTime EndDate
        {
            get { return _endDate; }
            set
            {
                _endDate = value;
                OnPropertyChanged();
            }
        }


        #endregion

        #region Page navigation

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            // если страница не кэширована
            if (e.NavigationMode.Equals(NavigationMode.New))
            {
                // проверяем наличие taskId для редактирования задачи
                if (NavigationContext.QueryString.TryGetValue("taskId", out _id))
                {
                    InitializeEditPage();
                }
            }
        }

        #endregion

        #region Methods

        private void InitializeEditPage()
        {
            OperationName = AppResources.EditTaskButton;
            ChangeTask = true;

            DispatcherHelper.ExecuteOnUIThreadAsync(() =>
            {
                Task = App.Server.Get(_id.ToPositiveInt());
                StartDate = Task.StartDate;
                EndDate = Task.EndDate;
                StatusListPicker.SelectedIndex = (int)Task.Status;
            },
            () => App.ProgressBar.ShowModal(),
            () => App.ProgressBar.HideModal());
        }

        private Task CreateTask()
        {
            var task = new Task();
            task.Name = TaskNameTextBox.Text;
            task.JobTime = JobTimeTextBox.Text.ToPositiveInt("Incorrect Job time");

            if (_errorList.Any())
                GenerateArgumentException(string.Format("Incorrect {0}", _errorList.FirstOrDefault()));

            if (StartDate > EndDate)
                GenerateArgumentException(AppResources.LongerStartDate);

            task.StartDate = StartDate;
            task.EndDate = EndDate;
            task.Status = (TaskStatus)StatusListPicker.SelectedIndex;
            return task;
        }

        #endregion

        #region Controls events

        private void ActionTaskButton_Click(object sender, RoutedEventArgs e)
        {
            var task = CreateTask();

            DispatcherHelper.ExecuteOnUIThreadAsync(() =>
            {
                if (ChangeTask)
                {
                    task.Id = _id.ToPositiveInt();
                    App.Server.Update(task);
                }
                else
                {
                    App.Server.Add(task);
                }
            },
            () => App.ProgressBar.ShowModal(),
            () => NavigationService.GoBack());
        }

        private void CancelButton_Click(object sender, RoutedEventArgs e)
        {
            NavigationService.GoBack();
        }

        private void DeleteButton_Click(object sender, EventArgs e)
        {
            var messageDialog = new MessageDialog(AppResources.DeleteMessage, AppResources.OkButton, AppResources.CancelButton).Show();
            messageDialog.Dismissed += (dialog, dialogArgs) =>
            {
                if (dialogArgs.Result.Equals(MessageDialogResult.LeftButton))
                {
                    DispatcherHelper.ExecuteOnUIThreadAsync(() =>
                    {
                        App.Server.Delete(Task.Id);
                    },
                    () => App.ProgressBar.ShowModal(),
                    () => NavigationService.GoBack());
                }
            };
        }

        private void DatePicker_ValueChanged(object sender, DateTimeValueChangedEventArgs e)
        {
            // после выбора даты в DatePicker заполняем поля ввода дат
            var datePicker = sender as DatePicker;
            if (datePicker.Name.Equals("StartDatePicker"))
            {
                StartDateTextBox.Text = datePicker.ValueString;
            }
            else
            {
                DateEndTextBox.Text = datePicker.ValueString;
            }
            RemoveErrorField(datePicker.Tag.ToString());
        }

        private void ScrollViewer_OnLoaded(object sender, RoutedEventArgs e)
        {
            if (!_isFirsLoad) return;

            // устанавливаем высоту scrollViewer для корректной 
            // прокрутки содержимого в режиме редактирования
            _scrollViewerHeight = (sender as ScrollViewer).ExtentHeight + _listPickerHeight;
            _isFirsLoad = false;
        }

        private void TestBox_GotFocus(object sender, RoutedEventArgs e)
        {
            MainStackPanel.Height = _scrollViewerHeight * 1.55;
        }

        private void TestBox_LostFocus(object sender, RoutedEventArgs e)
        {
            var textBox = sender as TextBox;
            if (textBox.Tag == null) return;
            // после потери фокуса поля ввода даты проверяем корректность заполнения
            // и устанавливаем дату в DatePicker
            ValidateTexBoxDate(textBox);
            if (textBox.Tag.Equals(AppResources.StartDateHeader))
            {
                StartDate = DateTime.Parse(textBox.Text);
            }
            else
            {
                EndDate = DateTime.Parse(textBox.Text);
            }
            MainStackPanel.Height = _scrollViewerHeight + _listPickerHeight;
        }

        private void ValidateTexBoxDate(TextBox textBox)
        {
            var tag = textBox.Tag.ToString();

            // проверяем корректность формата даты
            if (!DateTimeValidation.CheckDateStringFormat(textBox.Text))
            {
                InsertErrorField(tag);
                throw new FormatException(string.Format("Incorrect {0}", tag));
            }
            RemoveErrorField(tag);
        }

        private void StatusListPicker_OnLoaded(object sender, RoutedEventArgs e)
        {
            var listPicker = sender as ListPicker;
            // получаем высоту открытого списка статусов. 
            // 50 - высота элемента в списке.
            _listPickerHeight = listPicker.Items.Count * 50;
        }

        private void GenerateArgumentException(string message)
        {
            throw new ArgumentException(message);
        }

        private void InsertErrorField(string name)
        {
            if (!_errorList.Contains(name))
            {
                _errorList.Add(name);
            }
        }

        private void RemoveErrorField(string name)
        {
            _errorList.Remove(name);
        }

        #endregion

        #region NotifyPropertyChanged

        /// <summary>
        ///     Событие для обновления состояния свойства.
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;
        // метод обновления свойства в представлении
        protected void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            var handler = PropertyChanged;
            if (handler != null) handler(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion
    }
}