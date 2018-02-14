using System;
using System.Linq;
using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;
using TasksWindowsPhone.Model;
using UserTask = TasksWindowsPhone.Model.Task;

namespace TaskWindowsPhone.UnitTest
{
    [TestClass]
    public class TaskOperationTest
    {
        private const int TaskNumber = 5;

        private readonly UserTask _testTask = new UserTask
        {
            Name = "Test",
            JobTime = 10,
            StartDate = DateTime.Now,
            EndDate = DateTime.Now,
            Status = TaskStatus.InProcess
        };

        public TaskOperationTest()
        {
            for (var i = 0; i < TaskNumber; i++)
            {
                var task = new UserTask
                {
                    Name = "Test" + i,
                    JobTime = 10,
                    StartDate = DateTime.Now,
                    EndDate = DateTime.Now,
                    Status = TaskStatus.InProcess

                };
                App.Server.Add(task);
            }
        }

        [TestMethod]
        public void Test_AddNewTask()
        {
            App.Server.Add(_testTask);
        }

        [TestMethod]
        public void Test_RemoveTask()
        {
            App.Server.Add(_testTask);
            App.Server.Delete(5);
        }

        [TestMethod]
        public void Test_EditTask()
        {
            const string oldName = "Test";
            const string newName = "Test1000";

            var tasks = App.Server.GetList();
            var editTaskFirst = tasks.FirstOrDefault();

            editTaskFirst.Name = newName;
            App.Server.Update(editTaskFirst);

            var editTask = App.Server.GetList().FirstOrDefault(x => x.Name.Equals(newName));

            Assert.IsNotNull(editTask);
            Assert.AreNotEqual(oldName, editTask.Name);
        }

        [TestMethod]
        public void Test_GetTakeTasks()
        {
            var takeTasks = App.Server.GetList();

            Assert.AreEqual(TaskNumber, takeTasks.Count());
        }
    }
}
