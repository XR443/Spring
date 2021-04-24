package com.github.hardlolmaster.service;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.domain.PropertyInsuranceObject;
import com.github.hardlolmaster.service.coefficient.AreaCoefficient;
import com.github.hardlolmaster.service.coefficient.Coefficients;
import com.github.hardlolmaster.service.coefficient.ConstructionYearCoefficient;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@PropertySource("classpath:application.properties")
public class CalculateService
{
    private final Yaml yaml;
    @Value("${coefficients.file}")
    private String pathToCoefficients;
    private Coefficients coefficients;

    public CalculateService(Yaml yaml)
    {
        this.yaml = yaml;
    }

    @PostConstruct
    public void init()
    {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(pathToCoefficients);
        coefficients = yaml.load(inputStream);
    }

    public PropertyInsuranceContract calculate(@NotNull PropertyInsuranceContract contract)
    {
        PropertyInsuranceObject insuranceObject = contract.getInsuranceObject();
        assert insuranceObject != null;
        assert insuranceObject.getInsuranceSum() != null;

        Date periodFrom = contract.getInsurancePeriodFrom();
        assert periodFrom != null;
        LocalDate insurancePeriodFrom = periodFrom.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Date periodTo = contract.getInsurancePeriodTo();
        assert periodTo != null;
        LocalDate insurancePeriodTo = periodTo.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long days = DAYS.between(insurancePeriodFrom, insurancePeriodTo);
        double premium = insuranceObject.getInsuranceSum() / days;

        switch (insuranceObject.getPropertyType().toLowerCase())
        {
            case "квартира" -> premium *= coefficients.getPropertyTypeCoefficient().getApartment();
            case "дом" -> premium *= coefficients.getPropertyTypeCoefficient().getHouse();
            case "комната" -> premium *= coefficients.getPropertyTypeCoefficient().getRoom();
        }

        Long constructionYear = insuranceObject.getConstructionYear();
        ConstructionYearCoefficient constructionYearCoefficient = coefficients.getConstructionYearCoefficient();
        if (constructionYear < constructionYearCoefficient.getLess().getValue())
        {
            premium *= constructionYearCoefficient.getLess().getCoefficient();
        }
        else if (constructionYear >= constructionYearCoefficient.getFrom().getValue()
                && constructionYear < constructionYearCoefficient.getTo().getValue())
        {
            premium *= constructionYearCoefficient.getFrom().getCoefficient();
        }
        else if (constructionYear >= constructionYearCoefficient.getMore().getValue())
        {
            premium *= constructionYearCoefficient.getMore().getCoefficient();
        }

        Double area = insuranceObject.getArea();
        AreaCoefficient areaCoefficient = coefficients.getAreaCoefficient();
        if (area < areaCoefficient.getLess().getValue())
        {
            premium *= areaCoefficient.getLess().getCoefficient();
        }
        else if (area >= areaCoefficient.getFrom().getValue()
                && area < areaCoefficient.getTo().getValue())
        {
            premium *= areaCoefficient.getFrom().getCoefficient();
        }
        else if (area >= areaCoefficient.getMore().getValue())
        {
            premium *= areaCoefficient.getMore().getCoefficient();
        }

        double insurancePremium = BigDecimal.valueOf(premium).setScale(2, RoundingMode.HALF_UP).doubleValue();
        insuranceObject.setInsurancePremium(insurancePremium);

        long millisLocalDate = LocalDate.now()
                .atStartOfDay().toInstant(OffsetDateTime
                        .now()
                        .getOffset())
                .toEpochMilli();

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millisLocalDate + TimeUnit.DAYS.toMillis(1) - 1);

        contract.setCalculateDate(calendar.getTime());
        return contract;
    }
}
