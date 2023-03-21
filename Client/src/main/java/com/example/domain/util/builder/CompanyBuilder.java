package com.example.domain.util.builder;

import entity.Company;
import entity.ProductMarkStatus;
import entity.Segment;

public interface CompanyBuilder {

    CompanyBuilder withSegment(Segment segment);

    CompanyBuilder withName(String name);

    CompanyBuilder withCountry(String country);

    CompanyBuilder withSuccor(double succor);

    CompanyBuilder withIncomeTax(double incomeTax);

    CompanyBuilder withFinancialIncome(double financialIncome);

    CompanyBuilder withDepreciation(double depreciation);

    CompanyBuilder withEbitda(double ebitda);

    CompanyBuilder withProductStatus(ProductMarkStatus status);

    Company build();
}
