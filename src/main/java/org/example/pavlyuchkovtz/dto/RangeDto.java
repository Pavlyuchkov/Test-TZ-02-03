package org.example.pavlyuchkovtz.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeDto {
    @NotNull
    @Positive
    private Integer from;

    @NotNull
    @Positive
    private Integer to;

}
