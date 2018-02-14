using System;
using System.Windows;
using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;
using TasksWindowsPhone.Converters;
using TasksWindowsPhone.Helpers;

namespace TaskWindowsPhone.UnitTest
{
    [TestClass]
    public class ConvertersTest
    {
        [TestMethod]
        public void Test_BooleanToVisibilityConverter()
        {
            var converter = new BooleanToVisibilityConverter();

            var convertResult = converter.Convert(false, typeof(Visibility), null, null);
            var vivibilityResult = EnumUtils.Parse<Visibility>(convertResult.ToString());
            var boolRes = vivibilityResult == Visibility.Collapsed;

            Assert.IsTrue(boolRes);
        }

        [TestMethod]
        public void Test_StatusToIcoConverter()
        {
            var converter = new StatusToIcoConverter();

            var convertResult = converter.Convert("NotStarted", typeof(Visibility), null, null);

            Assert.IsTrue(convertResult.ToString().Contains("questionmark"));
        }

        [TestMethod]
        public void Test_ObjectNotNullError()
        {
            const object value = null;

            Assert.ThrowsException<ArgumentException>(() => { value.NotNull<string>(); });
        }

        [TestMethod]
        public void Test_StringToIntConverter()
        {
            const string number = "10";
            const int value = 10;

            Assert.IsTrue(value == number.ToPositiveInt());
        }

        [TestMethod]
        public void Test_StringToIntConverterException()
        {
            const string number = "-10";

            Assert.ThrowsException<ArgumentException>(() => { number.ToPositiveInt(); });
        }
    }
}
