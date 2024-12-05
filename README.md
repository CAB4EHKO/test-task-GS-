# Flight Filtering Application

## Описание проекта

Flight Filtering Application - это небольшое Java-приложение, предназначенное для обработки и фильтрации перелётов в соответствии с различными правилами. Основная цель проекта - демонстрация работы с объектно-ориентированным программированием и применение фильтров для исключения нежелательных данных из списка перелётов.

Приложение позволяет фильтровать перелёты на основе различных правил, таких как:
1. Исключение перелётов с вылетом в прошлом.
2. Исключение сегментов, где время прилёта раньше времени вылета.
3. Исключение перелётов, в которых общее время ожидания на земле превышает два часа.

Проект создан с использованием Java 17 и спроектирован по принципам SOLID, что делает его легко поддерживаемым и расширяемым.

## Структура проекта

Проект состоит из следующих основных пакетов и классов:

- **com.gridnine.testing.model**:
  - `Flight` - класс, представляющий перелёт, который состоит из одного или нескольких сегментов.
  - `Segment` - класс, представляющий один сегмент перелёта с временем вылета и прилёта.

- **com.gridnine.testing.service**:
  - `FlightBuilder` - класс для создания тестовых данных по перелётам. Используется для генерации различных сценариев перелётов, включая корректные и некорректные варианты.
  - `FlightFilter` - класс для фильтрации перелётов на основе заданных правил. Фильтрация выполняется с использованием предикатов.
  - `RulesManager` - класс, создающий различные правила для фильтрации перелётов. Правила представлены в виде предикатов, которые могут быть динамически применены к списку перелётов.

- **com.gridnine.testing.main**:
  - `Main` - основной класс для запуска приложения. Демонстрирует работу фильтрации, выводя отфильтрованные списки перелётов.

## Основные функции

- **Гибкость фильтрации**: Использование предикатов позволяет легко добавлять новые правила и менять существующие без изменения основной логики приложения.
- **Тестовые данные**: Генерация тестовых данных с использованием фабрики позволяет легко тестировать и расширять функциональность.
- **Применение SOLID**: Проект спроектирован с учётом принципов SOLID, что упрощает понимание кода, его поддержку и расширение.

## Установка и запуск

1. **Установка JDK**: Убедитесь, что у вас установлен JDK 17 или выше.
2. **Клонирование репозитория**: Склонируйте проект на локальную машину.
   ```bash
   git clone <URL репозитория>
   ```
3. **Открытие в IntelliJ IDEA**: Откройте проект в IntelliJ IDEA.
4. **Сборка и запуск**: Соберите и запустите проект, используя IDE или команду Maven.

## Примеры использования

Запустите класс `Main`, чтобы увидеть результаты фильтрации тестового набора перелётов. Программа выведет списки перелётов после применения каждого правила фильтрации:
- Исключение перелётов с вылетом в прошлом.
- Исключение сегментов с некорректными данными.
- Исключение перелётов с долгим временем ожидания на земле.

## Требования
- **Java**: 17 или выше
- **Maven**: Для управления зависимостями и сборки проекта

## Пример вывода
При запуске программы будет напечатан список исходных перелётов, а затем несколько списков, отфильтрованных по каждому из правил:
```
Исходные перелёты: [[2024-12-09T01:30|2024-12-09T03:30], [2024-12-09T04:30|2024-12-09T06:30]]
Перелёты после применения правила 1: ...
Перелёты после применения правила 2: ...
Перелёты после применения правила 3: ...
```

## Контрибьюция
Если вы хотите улучшить проект, пожалуйста, создайте pull request или откройте issue с описанием предложенного улучшения.

## Контакты
Если у вас есть вопросы или предложения, вы можете связаться с автором проекта через GitHub.

