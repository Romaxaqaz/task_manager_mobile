using System;

namespace TasksWindowsPhone.CustomControls.MessageDialog
{
    /// <summary>
    ///     Класс данных события MessageDialog Dismissed
    /// </summary>
    public class MessageDialogEventArgs : EventArgs
    {
        /// <summary>
        ///     Конструктор класса MessageDialogEventArgs
        /// </summary>
        /// <param name="result">Результат выбора действия MessageDialog</param>
        public MessageDialogEventArgs(MessageDialogResult result)
        {
            Result = result;
        }

        /// <summary>
        ///     Выбранное действие
        /// </summary>
        public MessageDialogResult Result { get; private set; }
    }
}
