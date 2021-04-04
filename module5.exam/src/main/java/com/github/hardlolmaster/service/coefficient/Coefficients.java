package com.github.hardlolmaster.service.coefficient;

import lombok.Data;

@Data
public class Coefficients {
    private PropertyTypeCoefficients propertyTypeCoefficient;
    private ConstructionYearCoefficient constructionYearCoefficient;
    private AreaCoefficient areaCoefficient;
}
