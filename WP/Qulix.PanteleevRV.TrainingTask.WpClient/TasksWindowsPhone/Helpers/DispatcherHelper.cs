using System;
using System.Threading.Tasks;
using System.Windows;

namespace TasksWindowsPhone.Helpers
{
    /// <summary>
    /// Класс для работы с потоками приложения
    /// </summary>
    public class DispatcherHelper
    {
        /// <summary>
        ///     Асинхронное выполнение кода в потоке пользовательского интерфейса
        /// </summary>
        /// <param name="action">Действие для асинхронного выполнения</param>
        /// <param name="onStartAction">Действие во время выполнения задачи</param>
        /// <param name="onCompleted">Дейсвие по завершению выполнения задачи</param>
        public static async void ExecuteOnUIThreadAsync(Action action, Action onStartAction = null, Action onCompleted = null)
        {
            if (onStartAction != null) onStartAction();

            await Task.Run(async () =>
            {
                // эмулирует некоторую работу посредством секундной задержки
                await Task.Delay(1000);
                // получаем диспетчер от текущего окна и получаем доступ к элементам UI из потока
                Deployment.Current.Dispatcher.BeginInvoke(action);
            });

            if (onCompleted != null) onCompleted();
        }
    }
}
