package org.example.pavlyuchkovtz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.pavlyuchkovtz.enums.Type;
import org.example.pavlyuchkovtz.enums.Unit;
import org.example.pavlyuchkovtz.util.Range;

@Entity
@Table(name = "sensors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название - обязательное поле")
    @Size(min = 3, max = 30, message = "Название датчика должно быть от 3 до 30 символов")
    private String name;

    @NotBlank(message = "Модель - обязательное поле")
    @Size(max = 15, message = "Название модели не должно превышать 15 символов")
    private String model;

    @Embedded
    private Range range;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Тип датчика - обязательное поле")
    private Type type;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Size(max = 40, message = "Поле 'Локация' не должно превышать 40 символов")
    private String location;

    @Size(max = 200, message = "Поле 'Описание' не должно превышать 200 символов")
    private String description;

    @AssertTrue(message = "Поле 'Радиус от' должно быть меньше, чем поле 'Радиус до'")
    private boolean isValidRange() {
        return range != null && range.isValid();
    }

}
