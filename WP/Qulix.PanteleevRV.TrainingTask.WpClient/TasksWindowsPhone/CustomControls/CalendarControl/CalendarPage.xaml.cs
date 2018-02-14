using System;
using System.ComponentModel;
using System.Windows;
using System.Windows.Navigation;
using Microsoft.Phone.Controls.Primitives;

namespace TasksWindowsPhone.CustomControls.CalendarControl
{
    /// <summary>
    ///     Страница для работы с календарем
    /// </summary>
    public partial class CalendarPage : INotifyPropertyChanged, IDateTimePickerPage
    {
        #region Fields

        private bool _isFirst = true;

        #endregion

        #region Properties

        /// <summary>
        ///     Дата для передачи в DatePicker
        /// </summary>
        public DateTime? Value { get; set; }

        private DateTime _selectedDate;
        /// <summary>
        ///     Выбранная в календаре дата
        /// </summary>
        public DateTime SelectedDate
        {
            get { return _selectedDate; }
            set
            {
                _selectedDate = value;
                NotifyPropertyChanged("SelectedDate");
            }
        }

        #endregion

        #region Constructor

        public CalendarPage()
        {
            InitializeComponent();
            DataContext = this;
        }

        #endregion

        #region Page navigation

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            if (Value.GetValueOrDefault().Date != DateTime.Now.Date)
            {
                var date = Value.GetValueOrDefault();
                SelectedDate = date;
                mainCalendar.SelectedMonth = date.Month;
                mainCalendar.SelectedYear = date.Year;
            }
        }

        #endregion

        #region Methods

        /// <summary>
        ///     Задает направление, в котором текстовые и другие элементы пользовательского интерфейса следуют в родительском элементе
        /// </summary>
        /// <param name="flowDirection"></param>
        public void SetFlowDirection(FlowDirection flowDirection)
        {
            FlowDirection = flowDirection;
        }

        private void SelectDate_OnClick(object sender, EventArgs e)
        {
            Value = SelectedDate;
            NavigationService.GoBack();
        }

        private void UpdateButton_OnClick(object sender, EventArgs e)
        {
            NavigationService.GoBack();
        }

        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        #endregion
    }
}