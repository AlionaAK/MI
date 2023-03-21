package com.example.dao.mapper;

import com.example.dao.core.RowMapper;
import com.example.dao.mapper.builder.impl.CompanyRowMapperBuilder;
import entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {
    //
    private final int COMPANY_ID = 1;
    //
    private final int SEGMENT_ID = 2;
    private final int SEGMENT_NAME = 3;
    //
    private final int COMPANY_MARK_STATUS = 4;
    //
    private final int COMPANY_NAME = 5;
    private final int COMPANY_COUNTRY = 6;
    private final int COMPANY_SUCCOR = 7;
    private final int COMPANY_INCOME_TAX = 8;
    private final int COMPANY_FINANCIAL_INCOME = 9;
    private final int COMPANY_DEPRICIATION = 10;
    private final int COMPANY_EBITDA = 11;
    //

    @Override
    public Company mapRow(ResultSet set) throws SQLException {
        return
                new CompanyRowMapperBuilder(COMPANY_ID, SEGMENT_ID, SEGMENT_NAME,
                        COMPANY_MARK_STATUS, COMPANY_NAME, COMPANY_COUNTRY, COMPANY_SUCCOR,
                        COMPANY_INCOME_TAX, COMPANY_FINANCIAL_INCOME, COMPANY_DEPRICIATION, COMPANY_EBITDA)
                        .getBuiltEntity(set);
    }
}
