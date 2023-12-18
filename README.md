# Сервис для сравнения Allure отчетов

Сравнивает 2 Allure отчета передачей ссылок на них. Отображает разницу пройденных тестов. Базируется на связке Spring Boot и Vaadin
Ссылка в формате http://allure01.test01.gismt.crpt.tech/frontend/tobacco/min/[17.12.23-02:06:15]crpt-tobacco-2.166.4-71968cea-autotests-1994580/data/suites.csv
http://allure01.test01.gismt.crpt.tech/frontend/tobacco/min/[17.12.23-02:06:15]crpt-tobacco-2.166.4-71968cea-autotests-1994580/

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

