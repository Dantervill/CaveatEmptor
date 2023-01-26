/*
Генератор идентификаторов Hibernate в виде метаданных уровня пакета.
Стратегия генерации первичных ключей распространяется на все модели предметной области,
если в каждой из них указать @GeneratedValue(generator = "ID_GENERATOR")
 */
@org.hibernate.annotations.GenericGenerator(
        name = "ID_GENERATOR",
        /* Стратегия применения расширенной последовательности
            стратегия enhanced_sequence производит последовательные числовые значения.
            Если диалект SQL поддерживает последовательности, Hibernate будет
            использовать настоящую последовательность в базе данных. Если СУБД
            не поддерживает последовательности, Hibernate создаст и будет использовать
            доп таблицу "таблицу последовательностей", имитирующую поведение последовательности.
         */
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        /* Имя последовательности.
                            При помощи этого параметра можно выбрать имя последовательности.
                            Hibernate будет использовать либо существующуюю последовательность,
                            либо создаст ее при автоматическом формировании SQL-схемы. Если СУБД
                            не поддерживает последовательности, это имя станет именем
                            "таблицы последовательностей"
                         */
                        name = "sequence_name",
                        value = "JPWH_SEQUENCE"
                ),
                @org.hibernate.annotations.Parameter(
                        // начальное значение
                        name = "initial_value",
                        value = "1000"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "increment_size",
                        value = "1"
                )
        }
)

package com.senla.internship.caveatemptor.model;