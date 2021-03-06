## Эмулятор Pliantresp

#### Сборка
Эмулятор состоит из standalone-сервера и client-библиотеки.
Для использования необходимо клонировать репозиторий и выполнить следующие команды в корне проекта:
* Для сборки и запуска сервера
```
cd standalone
../gradlew shadowJar
cd build/libs
java -jar pliantresp-0.1-standalone.jar // по-умолчанию порт 4567
java -jar pliantresp-0.1-standalone.jar 50053 // на порту 50053
java -jar pliantresp-0.1-standalone.jar 0 // на рандомном порту
```
* Для сборки клиента
```
cd client
../gradlew shadowJar
cd build/libs
```
затем из build/libs скопировать pliantresp-0.1-client.jar и использовать как библиотеку в своих тестах.


#### Управление сервером происходит путем контрактов 'request-response', в которых указываются:
1. Request path (обязательный параметр), который может быть указан как полностью (например '/some/path/first'), так и в виде шаблона (например '/some/\*/first', либо '/some/:name/first'). Все три приведенных примера являются эквивалентными. 
2. Request method (обязательный параметр). Эмулятор работает с методами GET, POST, PUT, PATCH, DELETE.
###### Хранение и поиск контрактов эмулятор осуществляет по соответствию параметрам request path и request method, сочетание которых является уникальным. Только один контракт с уникальным сочетанием 'request path-request method' может быть сохранен в contract storage эмулятора, при добавлении нового контракта с таким же сочетанием он перезаписывается.
3. Request body (опциональный параметр). Если указан, то эмулятор будет проверять тело запроса на соответствие данному параметру.
4. Request headers (опциональный параметр). Эмулятор будет проверять заголовки запроса на соответствие указанным в контракте request headers.
###### В контракте можно задать один позитивный и один негативный response. Эмулятор проверяет запрос на соответствие указанным в контракте параметрам, и при положительном результате проверки добавляет в ответ указанные в positive response данные, либо, если в контракте не задан positive response, только статус ответа 200. При отрицательном результате проверки в ответ добавляются указанные в negative response данные, либо, если в контракте не задан negative response, только статус ответа 400.
5. Positive response status (опциональный параметр). По-умолчанию 200.
6. Positive response body (опциональный параметр).
7. Positive response headers (опциональный параметр).
8. Negative response status (опциональный параметр). По-умолчанию 400.
9. Negative response body (опциональный параметр).
10. Negative response headers (опциональный параметр).
11. Response timeout (опциональный параметр). Задержка ответа, указывается в миллисекундах.


#### Пример формирования контракта (в данном примере указан весь список возможных методов):
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
Примечание: headers передаются парами - "ключ", "значение", "ключ", "значение".


#### Именованные параметры запроса
Можно указать именованные параметры запроса и в контракте в теле ответа задать шаблон, пример:
```
Contract contract = new ContractBuilder("/some/:name1/path/:name2", "GET")                
                .withPositiveResponseStatus(200)
                .withPositiveResponseBody("{\"name\":\"{{name1}}\",\"description\":\"{{name2}}\"}")
                .withPositiveResponseHeaders("Content-type", "application/json")                
                .build();
```
В данном случае при запросе с request path '/some/first_value/path/second_value' ответ эмулятора будет содержать следующее:
```
{
  "name": "first_value",
  "description": "second_value"
}
```

#### Управление контрактами
Для управления контрактами в клиентской библиотеке имеются следующие методы:
1. Добавить контракт
```
PliantrespClient pliantresp = new PliantrespClient();
String message = pliantresp.addContract(contract); // возвращает сообщение, добавлен контракт или нет
```
2. Получить все добавленные в эмулятор контракты
```
List<Contract> contracts = pliantresp.getContracts();
```
3. Удалить контракт по уникальному сочетанию 'request path-request method'
```
RequestDTO request = new RequestDTO().setPath("/some/path").setMethod("POST");
String message = pliantresp.deleteContract(request); // возвращает сообщение, удален контракт или нет
```
4. Удалить все добавленные в эмулятор контракты
```
String message = pliantresp.deleteContracts(); // возвращает сообщение, удалены контракты или нет
```

#### Управление сохраненными запросами
В эмуляторе имеется storage, в котором сохраняются поступившие запросы. Для управления хранилищем запросов в клиентской библиотеке доступны следующие методы:
1. Получить все сохраненные запросы
```
List<RequestDTO> requests = pliantresp.getRequests();
```
2. Получить сохраненный запрос по уникальному сочетанию 'request path-request method'
```
RequestDTO request = new RequestDTO().setPath("/some/path").setMethod("POST");
RequestDTO storedRequest = pliantresp.getRequest(request);
```
3. Удалить все сохраненные запросы
```
String message = pliantresp.deleteRequests(); // возвращает сообщение, удалены запросы или нет
```

#### Примечание
Пути запросов, начинающиеся со '/storage' являются служебными (предназначены для управления хранилищами контрактов и запросов).
