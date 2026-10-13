package gov.business.api;

import gov.core.api.AuditLog;
import gov.core.api.Citizen;
import gov.identity.api.IdentityService;
import gov.tax.api.TaxService;

/**
 * Registers a business: verifies the owner's NIN, then gets a TIN from URA.
 */
public class BusinessService {

    private final IdentityService identity = new IdentityService();
    private final TaxService tax = new TaxService();
    private int regCounter = 80000;

    public String registerBusiness(String ownerNin, String businessName) {
        Citizen owner = identity.lookup(ownerNin).orElse(null);
        if (owner == null) {
            AuditLog.record("gov.business", "Rejected '" + businessName + "' - owner NIN invalid");
            return "REJECTED: owner NIN invalid";
        }
        String tin = tax.registerTaxpayer(ownerNin);
        String regNo = "URSB-" + (++regCounter);
        AuditLog.record("gov.business", "Registered '" + businessName + "' as " + regNo);
        return "APPROVED: '" + businessName + "' " + regNo + ", owner " + owner.fullName() + ", TIN " + tin;
    }
}
