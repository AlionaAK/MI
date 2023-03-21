package com.example.domain.service.factory;

import com.example.domain.service.SegmentService;
import com.example.domain.service.CompanyService;
import com.example.domain.service.UserService;
import com.example.domain.service.impl.SegmentServiceImpl;
import com.example.domain.service.impl.CompanyServiceImpl;
import com.example.domain.service.impl.UserServiceImpl;

public final class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {
    }


    private UserService userService = new UserServiceImpl();
    private SegmentService segmentService = new SegmentServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    public UserService getUserService() {
        return userService;
    }

    public SegmentService getSegmentService() {
        return segmentService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }
}
