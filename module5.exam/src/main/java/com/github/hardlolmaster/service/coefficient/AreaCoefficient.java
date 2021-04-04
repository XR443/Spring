package com.github.hardlolmaster.service.coefficient;

import lombok.Data;

@Data
public class AreaCoefficient {
    private ValueToCoefficient<Double, Double> less;
    private ValueToCoefficient<Double, Double> from;
    private ValueToCoefficient<Double, Double> to;
    private ValueToCoefficient<Double, Double> more;
}
