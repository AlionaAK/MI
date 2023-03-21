package com.example.domain.util.builder.impl;

import com.example.domain.util.builder.CompanyBuilder;
import entity.Company;
import entity.ProductMarkStatus;
import entity.Segment;

public class CompanyBuilderImpl implements CompanyBuilder { // builder pattern

    private String id;
    private Segment segment;
    private String name;
    private String country;
    //
    private double succor; ///выручка
    private double incomeTax;//расходы по налогу на прибыль
    private double financialIncome; //итогоая финансовая прибыль
    private double depreciation;// амортизация
    private double ebitda; //мультипликатор ebitda
    //
    private ProductMarkStatus status;


    public CompanyBuilderImpl() {
        this.id = "";
    }

    public CompanyBuilderImpl(String id) {
        this.id = id;
    }

    @Override
    public CompanyBuilder withSegment(Segment segment) {
        this.segment = segment;
        return this;
    }

    @Override
    public CompanyBuilder withProductStatus(ProductMarkStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public CompanyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CompanyBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public CompanyBuilder withSuccor(double succor) {
        this.succor = succor;
        return this;
    }

    @Override
    public CompanyBuilder withIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
        return this;
    }

    @Override
    public CompanyBuilder withFinancialIncome(double financialIncome) {
        this.financialIncome = financialIncome;
        return this;
    }

    @Override
    public CompanyBuilder withDepreciation(double depreciation) {
        this.depreciation = depreciation;
        return this;
    }

    @Override
    public CompanyBuilder withEbitda(double ebitda) {
        this.ebitda = ebitda;
        return this;
    }

    @Override
    public Company build() {
        Company company = new Company();
        //
        company.setId(id);
        company.setSegment(segment);
        company.setName(name);
        company.setCountry(country);
        company.setSuccor(succor);
        company.setIncomeTax(incomeTax);
        company.setFinancialIncome(financialIncome);
        company.setDepreciation(depreciation);
        company.setEbitda(ebitda);
        company.setStatus(status);
        //
        return company;
    }
}
