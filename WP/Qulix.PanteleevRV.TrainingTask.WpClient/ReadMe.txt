ребования к ПО: 
ОС: Windows 8+
    - Скачать и установить .NET SDK v4.5
    - В каталоге C:\WP8DeployTools запустить файл sdksetup.exe
    - Установить Windows 8.1 and Windows phone 8.0/8.1 Tools (Tools and Windows SDKs)

Требования к устройству: 
ОС: Windows Phone 8.0+ (Для развертывания приложения устройство должно быть зарегестрировано как устройство разработчика):
    - Подключить устройство к компьютеру
    - Запустить утилиту PhoneReg (Windows Phone Developer Registration) из каталога C:\WP8DeployTools
    - Следовать указаниям приложения.

Сборка проекта:
    - Скачать проект из репозитория.
    - Распаковать ZIP-архив в любую папку.
    - В проводнике перейти в C:\Windows\Microsoft.NET\Framework\v4.0.30319 и в адресной строке ввести "cmd".
    - Выполнить команду msbuild <путь к проекту>\Qulix.PanteleevRV.TrainingTask.WpClient /p:Configuration=Release 
        (напимер: msbuild D:\MyTraningTask\trainingtask\Sources\Qulix.PanteleevRV.TrainingTask.WpClient\Qulix.PanteleevRV.TrainingTask.WpClient.sln /p:Configuration=Release)

Развертывание приложения на устройстве:

    - Запустить приложение "Application Deployment" C:\Program Files (x86)\Microsoft SDKs\Windows Phone\v8.0\Tools\XAP Deployment\XapDeploy
    - В качестве Target выбрать Device
    - Выбрать Xap файл, который находится в <путь к проекту>\Qulix.PanteleevRV.TrainingTask.WpClient\TasksWindowsPhone\Bin\Release
    - Нажать кнопку Deploy
