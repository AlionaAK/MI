package com.example.dao.mapper.builder.impl;

import com.example.dao.mapper.builder.RowMapperBuilder;
import com.example.domain.util.builder.impl.CompanyBuilderImpl;
import entity.Company;
import entity.ProductMarkStatus;
import entity.Segment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapperBuilder implements RowMapperBuilder<Company> {
    //
    private final int COMPANY_ID;
    //
    private final int SEGMENT_ID;
    private final int SEGMENT_NAME;
    //
    private final int COMPANY_MARK_STATUS;
    //
    private final int COMPANY_NAME;
    private final int COMPANY_COUNTRY;
    private final int COMPANY_SUCCOR;
    private final int COMPANY_INCOME_TAX;
    private final int COMPANY_FINANCIAL_INCOME;
    private final int COMPANY_DEPRICIATION;
    private final int COMPANY_EBITDA;


    public CompanyRowMapperBuilder(int COMPANY_ID, int SEGMENT_ID, int SEGMENT_NAME, int COMPANY_MARK_STATUS,
                                   int COMPANY_NAME, int COMPANY_COUNTRY, int COMPANY_SUCCOR,
                                   int COMPANY_INCOME_TAX,
                                   int COMPANY_FINANCIAL_INCOME, int COMPANY_DEPRICIATION, int COMPANY_EBITDA) {
        this.COMPANY_ID = COMPANY_ID;
        this.SEGMENT_ID = SEGMENT_ID;
        this.SEGMENT_NAME = SEGMENT_NAME;
        this.COMPANY_MARK_STATUS = COMPANY_MARK_STATUS;
        this.COMPANY_NAME = COMPANY_NAME;
        this.COMPANY_COUNTRY = COMPANY_COUNTRY;
        this.COMPANY_SUCCOR = COMPANY_SUCCOR;
        this.COMPANY_INCOME_TAX = COMPANY_INCOME_TAX;
        this.COMPANY_FINANCIAL_INCOME = COMPANY_FINANCIAL_INCOME;
        this.COMPANY_DEPRICIATION = COMPANY_DEPRICIATION;
        this.COMPANY_EBITDA = COMPANY_EBITDA;
    }

    @Override
    public Company getBuiltEntity(ResultSet set) throws SQLException {
        return doBuildProduct(set);
    }


    private Company doBuildProduct(ResultSet set) throws SQLException {
        //
        Segment segment = new SegmentRowMapperBuilder(SEGMENT_ID, SEGMENT_NAME)
                .getBuiltEntity(set);
        //
        return getProduct(segment, set);
    }

    private Company getProduct(Segment segment, ResultSet set) throws SQLException {
        final int statusId = set.getInt(COMPANY_MARK_STATUS) - 1; // because arrays start from 0
        final ProductMarkStatus status = ProductMarkStatus.values()[statusId];
        //
        return
                new CompanyBuilderImpl(set.getString(COMPANY_ID))
                        .withSegment(segment)
                        .withProductStatus(status)
                        .withName(set.getString(COMPANY_NAME))
                        .withCountry(set.getString(COMPANY_COUNTRY))
                        .withSuccor(set.getDouble(COMPANY_SUCCOR))
                        .withFinancialIncome(set.getDouble(COMPANY_FINANCIAL_INCOME))
                        .withIncomeTax(set.getDouble(COMPANY_INCOME_TAX))
                        .withDepreciation(set.getDouble(COMPANY_DEPRICIATION))
                        .withEbitda(set.getDouble(COMPANY_EBITDA))
                        .build();
    }
}
