package gov.tax.api;

import gov.core.api.AuditLog;
import gov.identity.api.IdentityService;
import gov.tax.internal.TinGenerator;

/**
 * Public tax API: register taxpayers and compute monthly PAYE.
 */
public class TaxService {

    private final IdentityService identity = new IdentityService();

    /** Issues a TIN only if the NIN is valid. Returns null otherwise. */
    public String registerTaxpayer(String nin) {
        if (!identity.isValidNin(nin)) {
            AuditLog.record("gov.tax", "TIN refused - unknown NIN " + nin);
            return null;
        }
        String tin = TinGenerator.next();
        AuditLog.record("gov.tax", "Issued TIN " + tin + " for NIN " + nin);
        return tin;
    }

    /** Simplified monthly PAYE bands (UGX) for demonstration. */
    public double computeMonthlyPaye(double grossIncome) {
        double tax;
        if (grossIncome <= 235_000) {
            tax = 0;
        } else if (grossIncome <= 335_000) {
            tax = (grossIncome - 235_000) * 0.10;
        } else if (grossIncome <= 410_000) {
            tax = 10_000 + (grossIncome - 335_000) * 0.20;
        } else {
            tax = 25_000 + (grossIncome - 410_000) * 0.30;
        }
        return tax;
    }
}
