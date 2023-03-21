package com.example.domain.service.impl;

import com.example.dao.CompanyRepository;
import com.example.dao.exception.DAOException;
import com.example.dao.factory.RepositoryFactory;
import com.example.domain.service.CompanyService;
import com.example.domain.service.exception.ServiceException;
import entity.Company;

import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository = RepositoryFactory.getInstance().getCompanyRepository();

    @Override
    public void addNewCompany(Company company) throws ServiceException {
        try {
            companyRepository.add(company);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateCompany(Company company) throws ServiceException {
        try {
            companyRepository.update(company);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteCompany(String companyId) throws ServiceException {
        try {
            companyRepository.removeById(companyId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Company> getAllCompanies() throws ServiceException {
        try {
            return companyRepository.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public List<Company> getAllMarkedCompanies() throws ServiceException {
        try {
            return companyRepository.getAllMarkedProducts();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Company> getAllNonMarkedCompanies() throws ServiceException {
        try {
            return companyRepository.getAllNonMarkedProducts();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
