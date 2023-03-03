 Сделано:
 - [X] *LandscapeService*: endpoint, возвращающий хост, текущее состояние, версию и количество сервисов Handyman и Rancher
 - [X] Взаимодействие сервиса *Landscape* с сервисами *Rancher* и *Handyman* производится через gRPC
 - [X] *LandscapeService* - gRPC клиент
 - [X] *RancherService* и *LandscapeService* - gRPC сервера
 - [X] Использование `StateOfConnectivity` для проверки состояния через `/readiness` или `getReadiness` для gRPC
 - [X] Интеграционные тесты для ручек
