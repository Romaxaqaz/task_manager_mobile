��������� � ��: 
��: Windows 8+
    - ������� � ���������� .NET SDK v4.5
    - � �������� C:\WP8DeployTools ��������� ���� sdksetup.exe
    - ���������� Windows 8.1 and Windows phone 8.0/8.1 Tools (Tools and Windows SDKs)

���������� � ����������: 
��: Windows Phone 8.0+ (��� ������������� ���������� ���������� ������ ���� ���������������� ��� ���������� ������������):
    - ���������� ���������� � ����������
    - ��������� ������� PhoneReg (Windows Phone Developer Registration) �� �������� C:\WP8DeployTools
    - ��������� ��������� ����������.

������ �������:
    - ������� ������ �� �����������.
    - ����������� ZIP-����� � ����� �����.
    - � ���������� ������� � C:\Windows\Microsoft.NET\Framework\v4.0.30319 � � �������� ������ ������ "cmd".
    - ��������� ������� msbuild <���� � �������>\Qulix.PanteleevRV.TrainingTask.WpClient /p:Configuration=Release 
        (�������: msbuild D:\MyTraningTask\trainingtask\Sources\Qulix.PanteleevRV.TrainingTask.WpClient\Qulix.PanteleevRV.TrainingTask.WpClient.sln /p:Configuration=Release)

������������� ���������� �� ����������:

    - ��������� ���������� "Application Deployment" C:\Program Files (x86)\Microsoft SDKs\Windows Phone\v8.0\Tools\XAP Deployment\XapDeploy
    - � �������� Target ������� Device
    - ������� Xap ����, ������� ��������� � <���� � �������>\Qulix.PanteleevRV.TrainingTask.WpClient\TasksWindowsPhone\Bin\Release
    - ������ ������ Deploy
