Система управления проектами
============================
Основаная цель: разработать систему управления проктами

Блоки задач
-----------
1.Разработка модуля dto
  - 
Создание классов dto для
- Сотрудника


      Запросы(requets)
        - создание и измение сотрудника (Фамилия, имя, отчество, должность, e-mail, пароль)
        - удаление сотрудника(Уникальный идентификатор)
        - поиск сотрудников(Фамилия, имя, отчество, учетная запись, e-mail)
      
      Ответы (response)
        - создание и измение сотрудника, поиск  (Фамилия, имя, отчество, должность, учетная запись, e-mail, пароль, статус) 
- Проекта
  

    Запросы (requests)
    - создание и изменеие проекта(Наименование, описание)
    - поиск проектов(Наименование, код)
    - перевод в другое состояние(статус проекта, код проекта)

    Ответы (response)
      - (Наименование, описание, код проекта)

- Команды

        - добавить участника проекта, получить всех участников(Роль, идентификатор сотрудника, код проекта)
        - удалить участника (код проекта, идентификатор сотрудника)
- Задачи

      - создание и измение задачи(наименование, описание, исполнитель, трудозатраты, крайний срок)
      - поиск задач( статус, исполнитель, автор, крайний срок, период создания)
      - изменение статуса(статус, идентификатор задачи) 
      Ответ
        -(наименование, описание, исполнитель, трудозатраты, крайний срок,статус, автор,дата создания, дата последнего изменения)


2.Разработка модуля models
  - 
- Сотрудник(Employee)
--------------------

| Наименование поля | Тип поля |                                                         Комментарий |
|-------------------|:--------:|--------------------------------------------------------------------:|
| id                |   UUID   |                       Уникальный идентификатор сотрудника в системе |
| position          |  Строка  |                              Должнось сотрудника. Обязательное поле |
| account           |   UUID   |                                Учетная запись. Не Обязательное поле |
| lastname          |  Строка  |                               Фамилия сотрудника. Обязательное поле |
| firstname         |  Строка  |                                   Имя сотрудника. Обязательное поле |
| middlename        |  Строка  |                           Отчество сотрудника. Не обязательное поле |
| Email             |  Строка  |                              Email сотрудника. Не обязательное поле |
| status            |  Status  | Фиксированый набор значений(Активный, Удаленный). обязательное поле |

Проект(Project)
-------------------

| Наименование поля | Тип поля |                                                                                        Комментарий |
|-------------------|:--------:|---------------------------------------------------------------------------------------------------:|
| project_id        |   UUID   |                                                                   Уникальный идентификатор проекта |
| projectName       |  Строка  |                                                            Наименование проекта. Обязательное поле |
| description       |  Строка  |                                                                               Не обязательное поле |
| projectStatus     |  Строка  | Фиксированный список статусов(Черновик, В раззработке,В тестировании, Завершен). Обязательное поле |

Команда(Team)
-------------------

| Наименование поля    |   Тип поля    | Комментарий |
|----------------------|:-------------:|------------:|
| идентификатор записи |     UUID      |             |
| Код проекта          |     UUID      |             |
| Роль                 | TeamRole Enum |             |
| сотрудник            |   Employee    |             |

Задача(Task)
-------------------
| Наименование поля             |   Тип поля   |                                                                           Комментарий |
|-------------------------------|:------------:|--------------------------------------------------------------------------------------:|
| id                            |     UUID     |                                                       Уникальный идентификатор задачи |
| taskName                      |    Строка    |                                                Наименование задачи. Обязательное поле |
| description                   |    Строка    |                                                 Описание задачи. не обязательное поле |
| executor                      |   Employee   | Исполнитель задачи сотрудник.Не обязательное поле.Только сотрудник в статусе активный |
| Трудозатраты                  |   Integer    |                                                                                       |
| Крайний срок                  |   Employee   |                                                                                       |
| Статус                        | Перечесление |                                                                                       |
| Автор задачи                  |   Employee   |                                                                                       |
| Дата создания                 |     Date     |                                                                                       |
| Дата последнего<br/>изменения |     Date     |
3.Разработка модуля application
  - 
  - Application

4.Разработка модуля repositories
  -
  - EmployeeRepository
  - ProjectRepository
  - TaskRepository
  - TeamRepository

5.Разработка модуля services
  - SecurityConfig
  - EmployeeService
  - ProjectService
  - TaskService
  - TeamService
6.Разработка модуля web
  -
EmployeeController
------------------
Реализуемые сервисы

**Создание сотрудника**

- Запрос: POST/employee

- Метод запроса: POST

- URL сервиса: /employee


**Изменение сотрудника**

- Запрос: PUT/employee/{:id}

- Метод запроса: PUT

- URL сервиса: /employee/{:id}


**Удаление сотрудника**

- Запрос: DELETE/employee/{:id}

- Метод запроса: DELETE

- URL сервиса: /employee/{:id}


**Получение карточки сотрудника**

- Запрос: GET/employee/{:id}

- Метод запроса: GET

- URL сервиса: /employee/{:id}


**Поск сотрудников**

- Запрос: GET

- Метод запроса: GET

- URL сервиса:

ProjectController
------------------
Реализуемые сервисы

**Создание проекта**

- Запрос: POST/project

- Метод запроса: POST

- URL сервиса: /project

**Изменение проекта**

- Запрос: PUT/project/{:id}

- Метод запроса: PUT

- URL сервиса: /project/{:id}


**Перевод в другое состояние проекта**

- Запрос: PUT/project/status/{:id}

- Метод запроса: PUT

- URL сервиса: /project/status/{:id}

**Поск проектов**

- Запрос: GET

- Метод запроса: GET

- URL сервиса: ?
  EmployeeController

TaskController
------------------
...

TeamController
------------------
...

AuthenticationController
-------------------------
...




Описание модулей
================

Модуль application
---------------
Cодержит класс конфигурации MainApplication.java, помеченый аннотацией @SpringBootApplication

Модуль web
-----
Содержит классы контроллеров. 

Методы контроллеров служат для обработки веб-запросов. Они взаимодействуют с сервисным уровнем, чтобы завершить работу, которую необходимо выполнить. 
После завершения обработки объектом сервисного уровня контроллер отвечает за обновление 
и создание объекта model и выбирает представление, которое пользователь увидит следующим, в качестве ответа.


Модуль buisness
----

Models
--------
Содержит классы сущностей.
Сущности в JPA — это не что иное, как POJO, представляющие данные, 
которые могут быть сохранены в базе данных. Сущность представляет собой таблицу, 
хранящуюся в базе данных. Каждый экземпляр сущности представляет собой строку в таблице.

                                                                             $1 
Services
------
В модуле services находится бизнес-логика приложения
Мы используем аннотацию @Service , чтобы указать, что класс принадлежит этому уровню.
Аннотация @Service используется с классами, которые предоставляют некоторые бизнес-функции.
Контекст Spring автоматически обнаружит эти классы, когда используется конфигурация на основе аннотаций и сканирование путей к классам.

    
Repositories
------------
Содержит интерфейсы которые используют JPA Entity для взаимодействия с ней. репозиторий
обеспечивает основные операции по поиску, сохранения, удалению данных.


dto
----
Содержит классы dto, которые являются объектами для передачи данных. Данными являются поля в классе.
Dto обеткты не содержат методы, только поля, геттеры/сеттеры, и конструкторы.
