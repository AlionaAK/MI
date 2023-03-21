package com.example.domain.service;

import com.example.domain.service.exception.ServiceException;
import entity.Company;

import java.util.List;

public interface CompanyService {

    void addNewCompany(Company company) throws ServiceException;

    void updateCompany(Company company) throws ServiceException;

    void deleteCompany(String companyId) throws ServiceException;

    List<Company> getAllCompanies() throws ServiceException;


    List<Company> getAllMarkedCompanies() throws ServiceException;

    List<Company> getAllNonMarkedCompanies() throws ServiceException;
}
