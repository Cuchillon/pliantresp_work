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
затем из build/libs скопировать pliantresp-0.1-client.jar и использовать как библиотеку в своих тестах.


Примеры использования:

* Задать контракт 'request-response' (в данном примере указан весь список возможных методов):
```
Contract contract = new ContractBuilder("/some/path", "POST")
                .withRequestBody("{\"name\":\"Question\",\"description\":\"Something in request\"}")
                .withRequestHeaders("Content-type", "application/json; charset=UTF-8")
                .withPositiveResponseStatus(200)
                .withPositiveResponseBody("{\"name\":\"Answer\",\"description\":\"Something in response\"}")
                .withPositiveResponseHeaders("Content-type", "application/json")
                .withNegativeResponseStatus(404)
                .withNegativeResponseBody("Not found")
                .withNegativeResponseHeaders("Content-type", "text/xml")
                .setResponseTimeout(10000)
                .build();

```
