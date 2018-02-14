using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using Microsoft.Phone.Controls;

namespace TasksWindowsPhone.CustomControls
{
    /// <summary>
    /// Full screen прогресс бар.
    /// </summary>
    public partial class ModalProgressBar : UserControl
    {
        #region Fields

        private readonly PhoneApplicationFrame _phoneFrame;
        private Popup _popupProgerss;

        #endregion

        #region Property

        private string _progressText = "Loading...";
        /// <summary>
        /// Текст во время индикации
        /// <example>Default: Loading...</example>
        /// </summary>
        public string ProgressText
        {
            get { return _progressText; }
            set { _progressText = value; }
        }

        /// <summary>
        /// Текущая страница приложения
        /// </summary>
        public PhoneApplicationPage PhonePage
        {
            get
            {
                return _phoneFrame.Content as PhoneApplicationPage;
            }
        }

        #endregion

        #region Methods
        
        /// <summary>
        /// Конструктор Full screen прогресс бара
        /// </summary>
        /// <param name="phoneFrame">Frame приложения</param>
        public ModalProgressBar(PhoneApplicationFrame phoneFrame)
        {
            InitializeComponent();

            _phoneFrame = phoneFrame;
            DataContext = this;

            SetFullScreenControl();
            InitializePopupControl();
        }

        /// <summary>
        /// Отображает модальный прогрессбар
        /// </summary>
        public void ShowModal()
        {
            _popupProgerss.IsOpen = true;
        }

        /// <summary>
        /// Скрывает модальный прогрессбар
        /// </summary>
        public void HideModal()
        {
            _popupProgerss.IsOpen = false;
        }

        private void InitializePopupControl()
        {
            _popupProgerss = new Popup { Child = this };
            _popupProgerss.Opened += (s, e) =>
            {
                if (PhonePage.ApplicationBar!=null)
                PhonePage.ApplicationBar.IsVisible = false;
            };
            _popupProgerss.Closed += (s, e) =>
            {
                if (PhonePage.ApplicationBar != null)
                PhonePage.ApplicationBar.IsVisible = true;
            };
        }

        private void SetFullScreenControl()
        {
            // получаем размеры дисплея устройства
            var bounds = Application.Current.Host.Content;
            RootPanel.Width = bounds.ActualWidth;
            RootPanel.Height = bounds.ActualHeight;
        }

        #endregion
    }
}
