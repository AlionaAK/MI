package com.example.domain.command.impl.company;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.CompanyService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.Company;

public class AddNewCompanyCommand implements Command {

    private CompanyService companyService = ServiceFactory.getInstance().getCompanyService();

    @Override
    public Message execute(Message request) {
        Company company = (Company) request.getByKey("company");
        Message response = new Message();
        try {
            companyService.addNewCompany(company);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }
        return response;
    }
}
