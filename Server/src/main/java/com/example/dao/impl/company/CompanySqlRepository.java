package com.example.dao.impl.company;

import com.example.dao.CompanyRepository;
import com.example.dao.core.InitializerRepository;
import com.example.dao.core.exception.JdbcTemplateException;
import com.example.dao.exception.DAOException;
import com.example.dao.mapper.CompanyRowMapper;
import entity.Company;

import java.util.List;

public class CompanySqlRepository extends InitializerRepository implements CompanyRepository {


    @Override
    public Company getEntityById(String id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(CompanySqlUtil.GET_COMPANY_BY_ID, new CompanyRowMapper(), id);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean removeById(String id) throws DAOException {
        try {
            jdbcTemplate.update(CompanySqlUtil.REMOVE_COMPANY_BY_ID, id);
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean add(Company company) throws DAOException {
        try {
            jdbcTemplate.update(CompanySqlUtil.ADD_NEW_COMPANY,
                    company.getSegment().getId(),
                    company.getStatus().getId(),
                    company.getName(),
                    company.getCountry(),
                    company.getSuccor(),
                    company.getIncomeTax(),
                    company.getFinancialIncome(),
                    company.getDepreciation(),
                    company.getEbitda());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update(Company company) throws DAOException {
        try {
            jdbcTemplate.update(CompanySqlUtil.UPDATE_COMPANY,
                    company.getSegment().getId(),
                    company.getStatus().getId(),
                    company.getName(),
                    company.getCountry(),
                    company.getSuccor(),
                    company.getIncomeTax(),
                    company.getFinancialIncome(),
                    company.getDepreciation(),
                    company.getEbitda(),
                    company.getId());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(CompanySqlUtil.GET_ALL_COMPANIES, new CompanyRowMapper());
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> getAllMarkedProducts() throws DAOException {
        try {
            return jdbcTemplate.query(CompanySqlUtil.GET_ALL_MARKED_COMPANIES, new CompanyRowMapper());
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> getAllNonMarkedProducts() throws DAOException {
        try {
            return jdbcTemplate.query(CompanySqlUtil.GET_ALL_NON_MARKED_COMPANIES, new CompanyRowMapper());
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }
}
