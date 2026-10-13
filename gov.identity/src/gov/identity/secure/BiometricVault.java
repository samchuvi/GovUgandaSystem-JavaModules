package gov.identity.secure;

import gov.core.api.AuditLog;
import gov.identity.internal.NinRegistry;

/**
 * Highly sensitive biometric matching.
 * Exported ONLY to gov.passport (see module-info: "exports ... to gov.passport").
 * Tax, Business or the main app will get a COMPILE ERROR if they try to use it.
 */
public class BiometricVault {

    public boolean matchFingerprint(String nin, String scannedPrint) {
        String stored = NinRegistry.fingerprintOf(nin);
        boolean match = stored != null && stored.equals(scannedPrint);
        AuditLog.record("gov.identity", "Biometric match for " + nin + " -> " + (match ? "MATCH" : "NO MATCH"));
        return match;
    }
}
