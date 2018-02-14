using System;
using Microsoft.Phone.Controls;

namespace TasksWindowsPhone.CustomControls.MessageDialog
{
    /// <summary>
    ///     Диалоговое окно на основе CustomMessageBox
    /// </summary>
    public class MessageDialog : CustomMessageBox
    {
        private static CustomMessageBox _customMessageBox;
        /// <summary>
        ///     Событие выбора варианта
        /// </summary>
        public new EventHandler<MessageDialogEventArgs> Dismissed;

        /// <summary>
        ///     Конструктор класса MessageDialog
        /// </summary>
        /// <param name="title">Заголовок сообщения</param>
        /// <param name="leftContent">Текст левой кнопки</param>
        /// <param name="rightContent">Текст правой кнопки</param>
        public MessageDialog(string title, string leftContent, string rightContent)
        {
            _customMessageBox = new CustomMessageBox
            {
                Caption = title,
                LeftButtonContent = leftContent,
                RightButtonContent = rightContent
            };
            _customMessageBox.Dismissed += (messageBox, dialogResult) =>
            {
                var button = dialogResult.Result == CustomMessageBoxResult.LeftButton
                    ? MessageDialogResult.LeftButton
                    : MessageDialogResult.RightButton;
                Dismissed.Invoke(messageBox, new MessageDialogEventArgs(button));
            };
        }

        /// <summary>
        ///     Отображение диалогового окна
        /// </summary>
        /// <returns></returns>
        public new MessageDialog Show()
        {
            _customMessageBox.Show();
            return this;
        }
    }
}

