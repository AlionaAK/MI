package com.example.dao.factory;

import com.example.dao.SegmentRepository;
import com.example.dao.CompanyRepository;
import com.example.dao.UserRepository;
import com.example.dao.impl.segment.SegmentSqlRepository;
import com.example.dao.impl.company.CompanySqlRepository;
import com.example.dao.impl.user.UserSqlRepository;

public final class RepositoryFactory {
    private static final RepositoryFactory instance = new RepositoryFactory();

    public static RepositoryFactory getInstance() {
        return instance;
    }

    private RepositoryFactory() {

    }


    private UserRepository userRepository = new UserSqlRepository();
    private SegmentRepository segmentRepository = new SegmentSqlRepository();

    private CompanyRepository companyRepository = new CompanySqlRepository();

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SegmentRepository getSegmentRepository() {
        return segmentRepository;
    }

    public CompanyRepository getCompanyRepository() {
        return companyRepository;
    }
}
