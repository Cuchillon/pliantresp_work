## Эмулятор Pliantresp

#### Сборка
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


#### Управление сервером происходит путем контрактов 'request-response', в которых указываются:
1. Request path (обязательный параметр), который может быть указан как полностью (например '/some/path/first'), так и в виде шаблона (например '/some/\*/first', либо '/some/:name/first').
2. Request method (обязательный параметр). Эмулятор работает с методами GET, POST, PUT, PATCH, DELETE.

###### Хранение и поиск контрактов эмулятор осуществляет по соответствию параметрам request path и request method, сочетание которых является уникальным. Только один контракт с уникальным сочетанием request path-request method может быть сохранен в contract storage эмулятора, при добавлении нового контракта с таким же сочетанием он перезаписывается.



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
* Обязательные параметры
1. Значения, передаваемые в конструкторе ContractBuilder:
