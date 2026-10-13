package gov.tax.internal;

import gov.core.api.GovService;

public class TaxDepartment implements GovService {

    @Override
    public String departmentName() {
        return "Uganda Revenue Authority (URA)";
    }

    @Override
    public String description() {
        return "Issues TINs and computes PAYE tax";
    }
}
