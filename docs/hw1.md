# Сделано:
 - [X] Монорепозиторий с тремя отдельными сервисами
 - [X] Эндпоинт `/actuator/info` через *Spring Boot Actuator*
 - [X] Возможность развернуть все три сервиса через *docker-compose* с разными портами
 - [X] Эндпоинт `/metrics` для предоставления метрик в формате *Prometheus* через *Spring Boot Actuator*
 - [X] Эндпоинт `/system/liveness` предоставляющий *HttpStatus.OK* (захардкожен)
 - [X] Эндпоинт `/system/readiness` предоставляющий *HttpStatus.OK*  и Json с именем текущего сервиса и статусом (захардкожен)
 - [X] Интеграционные тесты на эндпоинты
