# Ionov-JavaBank

REST-сервис для управления пользователями, банковскими картами и операциями по картам.

## Требования

- Java 25
- Maven 3+
- Docker + Docker Compose

## Документация API

- Swagger UI (GitHub Pages): https://ionover.github.io/bankEffectiveMobile
- Локально после запуска: `http://localhost:8084/swagger-ui/index.html`
- Bruno коллекция в assets/bruno

## Быстрый старт

1. Склонируйте репозиторий и перейдите в папку проекта.
2. Проверьте файл `.env` (для обычного запуска) или `.dev.env` (для прогона Acceptance-тестов).
3. Запустите сервис:

```bash
./scripts/start.sh
```

Сервис поднимется на `http://localhost:8084`.

Остановить сервис:

```bash
./scripts/down.sh
```

## Аутентификация и роли

- Получение JWT: `POST /oauth/login`
- Все остальные эндпоинты требуют токен `Bearer`.
- `ADMIN` управляет пользователями и картами.
- Обычный пользователь работает со своими картами и операциями.

## Тесты

Acceptance Testing (Cucumber + Rest Assured):

```bash
./scripts/run-asseptense.sh
```

Windows:

```bat
scripts\run-asseptense.bat
```

Пример feature-файла:
https://github.com/ionover/bankEffectiveMobile/blob/main/asseptense-test/src/test/resources/ogr/exapmle/asseptensetest/Login.feature
