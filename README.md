# Сервис для сравнения Allure отчетов

Сравнивает 2 Allure отчета передачей ссылок на них. Отображает разницу пройденных тестов. Базируется на связке Spring Boot и Vaadin
Ссылка в формате https://allure.crpt.tech/api/buckets/gismt-bllk/report/DOCKER/TAPI/Report_26-12-2023_13-20_sim.lukyanov/index.html

## Запуск приложения

Это стандартный Maven проект. Для запуска из командной строки используйте
`mvnw` (Windows), or `./mvnw` (Mac & Linux), далее открыть
http://localhost:8080 в браузере.

## Установка в продакшн

Для создания билда `mvnw clean package -Pproduction` (Windows),
или `./mvnw clean package -Pproduction` (Mac & Linux).
Это создаст jar файл во всеми зависимостями и ресурсами фронта. JAR файл будет доступен в директории `target`

После сборки jar его можно запустить командой
`java -jar target/comparator-1.0-SNAPSHOT.jar`

Удаленный запуск через терминал на сервере
`nohup java -jar target/comparator-1.0-SNAPSHOT.jar &`

Для копирования jar файла на сервер с локальной машины можно использовать утилиту scp
`scp /Users/n.morozov/IdeaProjects/allure-comparator/target/comparator-1.0-SNAPSHOT.jar ubuntu@aqa-01.nmorozov.crpt.cloud:/opt/allure-comparator`

## Структура проекта

- `MainLayout.java` in `src/main/java` содержит разметку приложения
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` в `src/main/java` содержит серверную часть приложения
- `views` в `frontend/`  содержит фронтовую часть приложения
- `themes` в `frontend/`  содержит CSS

## Полезные ссылки

- Документация по Vaadin [vaadin.com/docs](https://vaadin.com/docs).
- Туториал по Vaadin [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).
- Создание нового Vaadin проекта [start.vaadin.com](https://start.vaadin.com/).
- Примеры UI компонент и их использование [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components).
- Построение UI без кастомных CSS c использованием [CSS utility classes](https://vaadin.com/docs/styling/lumo/utility-classes). 

