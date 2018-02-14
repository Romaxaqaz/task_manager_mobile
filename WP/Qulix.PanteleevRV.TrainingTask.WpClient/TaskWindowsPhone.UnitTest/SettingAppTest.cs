using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;
using TasksWindowsPhone.Settings;

namespace TaskWindowsPhone.UnitTest
{
    [TestClass]
    public class SettingAppTest
    {
        [TestMethod]
        public void Test_SaveNumberToSettingStorage()
        {
            const int number = 10;
            SettingService.NumberTasks = number;

            Assert.AreEqual(number, SettingService.NumberTasks);
        }

        [TestMethod]
        public void Test_SaveUrlToSettingStorage()
        {
            const string url = "https://testsave.com";
            SettingService.UrlServer = url;

            Assert.AreEqual(url, SettingService.UrlServer);
        }
    }
}
