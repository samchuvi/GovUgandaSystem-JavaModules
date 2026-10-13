package gov.business.internal;

import gov.core.api.GovService;

public class BusinessDepartment implements GovService {

    @Override
    public String departmentName() {
        return "Business Registration (URSB)";
    }

    @Override
    public String description() {
        return "Registers businesses and links them to a TIN";
    }
}
