# Скрипты для сборки контейнеров.

## Landscape 
```bash
cd landscape-service && docker build .
```

## Handyman
```bash
cd handyman-service && docker build .
```

## Rancher
```bash
cd rancher-service && docker build .
```

# Или собрать и запустить их сразу все вместе
```bash
docker-compose up --build
```

# Инструкция и запись с демонстрацией работоспособности

https://youtu.be/8gT32FIArhM

# Подключение мониторинга с Prometheus и Grafana
1. Перед запуском контейнеров нужно создать папки для хранилищ контейнеров
```bash
cd dev
mkdir grafana_data
mkdir prometheus_data
```
2. Меняем владельца, так как образ bitnami - non-root
```bash
sudo chown -R 1001:1001 grafana_data
sudo chown -R 1001:1001 prometheus_data
```
3. Запускаем контейнеры:
```bash
docker-compose up -d prometheus grafana
```
4. Заходим в grafana на http://localhost:3000