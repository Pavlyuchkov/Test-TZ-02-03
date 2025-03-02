package org.example.pavlyuchkovtz.util;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Range {

    @NotNull(message = "'Радиус от' - обязательное поле")
    @Positive(message = "Значение 'Радиус от' должно быть положительным")
    private Integer rangeFrom;

    @NotNull(message = "'Радиус до' - обязательное поле")
    @Positive(message = "Значение 'Радиус до' должно быть положительным")
    private Integer rangeTo;

    public boolean isValid() {
        return rangeFrom != null && rangeTo != null && rangeFrom < rangeTo;
    }
}
