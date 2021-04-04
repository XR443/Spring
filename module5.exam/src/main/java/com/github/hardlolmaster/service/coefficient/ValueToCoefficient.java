package com.github.hardlolmaster.service.coefficient;

import lombok.Data;

@Data
public class ValueToCoefficient<Value, Coefficient> {
    private Value value;
    private Coefficient coefficient;
}
