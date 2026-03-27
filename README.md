# Проект "Валидатор данных"
[![Actions Status](https://github.com/sheykoda-rettani/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/sheykoda-rettani/java-project-78/actions) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sheykoda-rettani_java-project-78&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sheykoda-rettani_java-project-78)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sheykoda-rettani_java-project-78&metric=bugs)](https://sonarcloud.io/summary/new_code?id=sheykoda-rettani_java-project-78) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sheykoda-rettani_java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sheykoda-rettani_java-project-78)
<br/>Проект представляет собой библиотеку для валидации различных данных
## Поддерживаемые варианты валидации

- Валидация строк.
- Валидация целых чисел
- Валидация карт (Map) с возможностью вложенной валидации

## Описание
Библиотека предоставляет гибкий и расширяемый механизм для проверки данных на соответствие заданным правилам (схемам). Основной принцип работы — построение цепочки вызовов методов (fluent interface), где каждый метод добавляет новое правило валидации.

Центральным объектом является класс `Validator`, который служит точкой входа для создания схем валидации.

## Использование
### 1. Валидация чисел (NumberSchema)
Для проверки числовых данных используется метод `Validator.number()`.

#### Список правил:

- `required()` — значение не должно быть null.
- `positive()` — значение должно быть больше нуля. Правило пропускает null.
- `range(from, to)` — значение должно находиться в указанном диапазоне (включительно).

### 2. Валидация строк (StringSchema)
Для проверки строковых данных используется метод `Validator.string()`. 

#### Список правил:

- `required()` — значение не должно быть null.
- `minLength(length)` — строка должна быть не меньше указанной длины.
- `contains(substring)` — строка должна содержать подстроку. Регистр букв игнорируется

### 2. Валидация карт (MapSchema)
Для проверки карт используется метод `Validator.map()`.

#### Список правил:

- `required()` — значение не должно быть null.
- `sizeof(size)` — карта должна содержать определенное количество записей.
- `shape(keySchemas)` — Позволяет настраивать валидацию под каждый из ключей карты индивидуалтно

### Примечание
Все правила работают по принципу "И", каждый новый вызов определенного ограничения перезаписывает старый

Пример для `NumberValidator`:

```java
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;

var v = new Validator();

var schema = v.string();

// Пока не вызван метод required(), null и пустая строка считаются валидным
schema.isValid(""); // true
schema.isValid(null); // true

schema.required();

schema.isValid(null); // false
schema.isValid(""); // false
schema.isValid("what does the fox say"); // true
schema.isValid("hexlet"); // true

schema.contains("wh").isValid("what does the fox say"); // true
schema.contains("what").isValid("what does the fox say"); // true
schema.contains("whatthe").isValid("what does the fox say"); // false

schema.isValid("what does the fox say"); // false
// Здесь уже false, так как добавлена еще одна проверка contains("whatthe")

// Если один валидатор вызывался несколько раз
// то последний имеет приоритет (перетирает предыдущий)
var schema1 = v.string();
schema1.minLength(10).minLength(4).isValid("Hexlet"); // true
```

