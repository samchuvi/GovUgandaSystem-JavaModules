package gov.identity.api;

import gov.core.api.AuditLog;
import gov.core.api.Citizen;
import gov.identity.internal.NinRegistry;

import java.util.Optional;

/**
 * Public identity API - any module may verify a NIN,
 * but it never gets direct access to the registry itself.
 */
public class IdentityService {

    public boolean isValidNin(String nin) {
        boolean valid = NinRegistry.find(nin).isPresent();
        AuditLog.record("gov.identity", "NIN check " + nin + " -> " + (valid ? "VALID" : "INVALID"));
        return valid;
    }

    public Optional<Citizen> lookup(String nin) {
        return NinRegistry.find(nin);
    }
}
