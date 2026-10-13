package gov.passport.api;

import gov.core.api.AuditLog;
import gov.core.api.Citizen;
import gov.identity.api.IdentityService;
import gov.identity.secure.BiometricVault;   // allowed: qualified export to gov.passport

/**
 * Passport processing - requires a valid NIN AND a biometric match.
 */
public class PassportService {

    private final IdentityService identity = new IdentityService();
    private final BiometricVault biometrics = new BiometricVault();
    private int passportCounter = 100;

    public String applyForPassport(String nin, String scannedFingerprint) {
        Citizen citizen = identity.lookup(nin).orElse(null);
        if (citizen == null) {
            AuditLog.record("gov.passport", "Rejected - NIN not found");
            return "REJECTED: NIN not found";
        }
        if (!biometrics.matchFingerprint(nin, scannedFingerprint)) {
            AuditLog.record("gov.passport", "Rejected - fingerprint mismatch for " + citizen.fullName());
            return "REJECTED: fingerprint does not match";
        }
        String passportNo = "A" + String.format("%07d", ++passportCounter);
        AuditLog.record("gov.passport", "Passport " + passportNo + " issued to " + citizen.fullName());
        return "APPROVED: Passport " + passportNo + " for " + citizen.fullName();
    }
}
