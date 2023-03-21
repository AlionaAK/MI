package com.example.dao.impl.company;

final class CompanySqlUtil {

    private CompanySqlUtil() {

    }


    static final String ADD_NEW_COMPANY =
            "INSERT INTO companies (company_id, segment_id, mark_status_id, name, country, succor, " +
                    " income_tax, financial_income, depriciation, ebitda) " +
                    " VALUES (uuid(), ? ,  ?,  ?,  ?,  ?, ?, ?, ?, ?);";

    static final String UPDATE_COMPANY =
            "update companies set segment_id=? ,mark_status_id=?, " +
                    " name =? , country =?, succor=?, " +
                    " income_tax=?, financial_income=?, depriciation=?, ebitda= ? " +
                    " where company_id=?";

    static final String REMOVE_COMPANY_BY_ID =
            "delete from companies where company_id=?";

    static final String GET_ALL_COMPANIES =
            " select c.company_id, " +
                    "s.segment_id, s.segment_name, " +
                    " c.mark_status_id, " +
                    " c.name, c.country, c.succor, c.income_tax, " +
                    " c.financial_income, c.depriciation, c.ebitda " +
                    " from companies c " +
                    " inner join segments s " +
                    " on s.segment_id = c.segment_id ";

    static final String GET_COMPANY_BY_ID =
            " select c.company_id, " +
                    "s.segment_id, s.segment_name, " +
                    " c.mark_status_id, " +
                    " c.name, c.country, c.succor, c.income_tax, " +
                    " c.financial_income, c.depriciation, c.ebitda " +
                    " from companies c " +
                    " inner join segments s " +
                    " on s.segment_id = c.segment_id " +
                    " where c.company_id = ? ";

    ////
    static final String GET_ALL_MARKED_COMPANIES =
            " select c.company_id, " +
                    "s.segment_id, s.segment_name, " +
                    " c.mark_status_id, " +
                    " c.name, c.country, c.succor, c.income_tax, " +
                    " c.financial_income, c.depriciation, c.ebitda " +
                    " from companies c " +
                    " inner join segments s " +
                    " on s.segment_id = c.segment_id " +
                    " WHERE c.mark_status_id != 4"; //4 не оценена

    static final String GET_ALL_NON_MARKED_COMPANIES =
            " select c.company_id, " +
                    "s.segment_id, s.segment_name, " +
                    " c.mark_status_id, " +
                    " c.name, c.country, c.succor, c.income_tax, " +
                    " c.financial_income, c.depriciation, c.ebitda " +
                    " from companies c " +
                    " inner join segments s " +
                    " on s.segment_id = c.segment_id " +
                    " WHERE c.mark_status_id = 4"; //4 не оценена
}
