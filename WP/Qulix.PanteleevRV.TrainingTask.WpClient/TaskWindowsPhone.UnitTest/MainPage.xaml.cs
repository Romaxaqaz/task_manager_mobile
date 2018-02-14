using System.Threading;
using Microsoft.Phone.Controls;
using Microsoft.VisualStudio.TestPlatform.Core;
using Microsoft.VisualStudio.TestPlatform.TestExecutor;
using vstest_executionengine_platformbridge;

namespace TaskWindowsPhone.UnitTest
{
    public partial class MainPage : PhoneApplicationPage
    {
        public MainPage()
        {
            InitializeComponent();

            var wrapper = new TestExecutorServiceWrapper();
            new Thread(new ServiceMain((paramOne, paramTwo) => wrapper.SendMessage((ContractName)paramOne, paramTwo)).Run).Start();
        }
    }
}