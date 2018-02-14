using System;
using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;
using TasksWindowsPhone.Helpers;

namespace TaskWindowsPhone.UnitTest
{
    [TestClass]
    public class ObjectValidation
    {
        [TestMethod]
        public void Test_DateTimeFormatValidation()
        {
            const string date = "2021-10-10";

            DateTimeValidation.CheckDateStringFormat(date);
        }

        [TestMethod]
        public void Test_DateTimeFormatValidationError()
        {
            const string date = "2021-10";

            Assert.IsFalse(DateTimeValidation.CheckDateStringFormat(date));
        }

        [TestMethod]
        public void Test_UrlValidation()
        {
            const string url = "https://test.com";

            Assert.IsTrue(UrlValidation.Validate(url));
        }

        [TestMethod]
        public void Test_UrlValidationError()
        {
            const string url = "test.com";

            Assert.IsFalse(UrlValidation.Validate(url));
        }
    }
}
