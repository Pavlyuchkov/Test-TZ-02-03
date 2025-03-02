package org.example.pavlyuchkovtz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto {

    private Long id;

    @NotBlank(message = "Название - обязательное поле")
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank(message = "Модель - обязательное поле")
    @Size(max = 15)
    private String model;

    @NotNull
    private RangeDto range;

    @NotNull
    private String type;

    private String unit;

    @Size(max = 40)
    private String location;

    @Size(max = 200)
    private String description;
}
