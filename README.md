# pliantresp_work
Repo for working on pliantresp emulator

Эмулятор состоит из standalone-сервера и client-библиотеки.
Для использования необходимо клонировать репозиторий и выполнить следующие команды в корне проекта:
* Для сборки и запуска сервера
```
cd standalone
../gradlew shadowJar
cd build/libs
java -jar pliantresp-0.1-standalone.jar
```
* Для сборки клиента
```
cd client
../gradlew shadowJar
cd build/libs
```
затем в build/libs взять pliantresp-0.1-client.jar и использовать как библиотеку в своих тестах.
