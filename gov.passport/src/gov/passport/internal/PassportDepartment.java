package gov.passport.internal;

import gov.core.api.GovService;

public class PassportDepartment implements GovService {

    @Override
    public String departmentName() {
        return "Immigration (Passports)";
    }

    @Override
    public String description() {
        return "Processes passports using biometric verification";
    }
}
