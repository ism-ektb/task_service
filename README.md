## Микросервис task_service, часть проекта Микросервисы, создаваемой в рамках мастерской Яндекса.
Проект разрабатывается совместно бывшими студентами ЯндексПрактикума.
### Авторы
- Горбачевым Иваном https://github.com/MrGriv
- Шаталова Ольга https://github.com/ol5ga
- Марина https://github.com/marina52746
- Михайлов Илья https://github.com/ism-ektb
- Шведов Алексей https://github.com/Aleksey01091993
### В проект входитят следующие микросервисы:
- user_service https://github.com/ism-ektb/user_service
- event_service https://github.com/ism-ektb/event_service
- task_service https://github.com/ism-ektb/task_service
- review_service https://github.com/ism-ektb/review_service
- registration_service https://github.com/ism-ektb/registration_service

На первом этапе сервисы работали независимо друг от друга. Сейчас в них добавляются новые функции по взаимодействию между собой для отработки практических навыков по разработке микросервисов

### Данный микросервис позволяет: 
- Создавать, редактировать и удалять задачи. Модель Task включает следующие поля: title, description, createdDateTime, deadline, status, assigneeId, authorId, eventId
- Задачи принадлежащие к одному событию можно объединять в группы (epic). Руководитель группы может добавлять и удалять задачи.
- Сервис делает запросы к user_service и event_service и осуществляет проверку на существование мероприятия, а также на то, что assigneeId, authorId и ответственный за группу задач принадлежат команде этого мероприятия. 
