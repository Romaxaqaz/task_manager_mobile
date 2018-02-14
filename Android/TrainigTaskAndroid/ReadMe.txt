Тренировочный проект приложения менеджера задач для Android.
---
Требования к ПО:

jdk - Java 7 и выше
Android sdk Build-tools - rev.25
Android sdk platform - rev.25
Устройство - Android 4.0 (API 14) и выше

Установка Android SDK и JDK:

    1. Скачать и распаковать Android SDK: 
        http://cs-repo.qulix.com/service/local/repositories/system-repository/content/com/google/androidsdk/androidsdk/25.2.5/androidsdk-25.2.5-windows.zip
        
    2. Скачать и распаковать JDK: 
        http://cs-repo.qulix.com/service/local/repositories/system-repository/content/com/oracle/java/jdk/1.8.0_66/jdk-1.8.0_66-windows64.zip

Подготовка к сборке приложения:

    1. Создать файл local.properties.
    2. Открыть файл local.properties и добавть в него строку sdk.dir=path/to/sdk (Пример: sdk.dir=D:/Android/androidsdk-25.2.5-windows)
    3. Создать файл gradle.properties
    4. Открыть файл gradle.properties и добавить строку org.gradle.java.home=path/to/javac  (Пример: org.gradle.java.home=D:/Android/jdk-1.8.0_66-windows64)
    5. Собранный *.apk файл находится в папке build в корне проекта


Сборка приложения:
    1. Выполнить команду gradlew clean
    2. Выполнить команду gradlew build

Разворачивание приложения на устройстов:
    1. Выполнить команду gradlew uninstallRelease
    2. Выполнить команду gradlew installRelease
