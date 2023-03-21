package com.example.dao;

import com.example.dao.core.CrudRepository;
import com.example.dao.exception.DAOException;
import entity.Company;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company> {

    @Override
    Company getEntityById(String id) throws DAOException;

    @Override
    boolean removeById(String id) throws DAOException;

    @Override
    boolean add(Company company) throws DAOException;

    @Override
    boolean update(Company company) throws DAOException;

    @Override
    List<Company> getAll() throws DAOException;

    List<Company> getAllMarkedProducts() throws DAOException;

    List<Company> getAllNonMarkedProducts() throws DAOException;

}
