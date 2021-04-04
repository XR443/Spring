package com.github.hardlolmaster.service.coefficient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionYearCoefficient {
    private ValueToCoefficient<Integer, Double> less;
    private ValueToCoefficient<Integer, Double> from;
    private ValueToCoefficient<Integer, Double> to;
    private ValueToCoefficient<Integer, Double> more;
}
