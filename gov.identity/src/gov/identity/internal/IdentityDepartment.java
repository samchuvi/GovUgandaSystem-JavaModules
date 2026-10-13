package gov.identity.internal;

import gov.core.api.GovService;

/** Service provider registered in module-info with "provides ... with". */
public class IdentityDepartment implements GovService {

    @Override
    public String departmentName() {
        return "National Identification (NIRA)";
    }

    @Override
    public String description() {
        return "Manages National ID Numbers and biometrics";
    }
}
