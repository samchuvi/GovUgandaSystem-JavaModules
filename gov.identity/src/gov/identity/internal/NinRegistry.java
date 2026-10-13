package gov.identity.internal;

import gov.core.api.Citizen;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Raw national ID database. This package is NOT exported,
 * so no other module can read or modify this data directly.
 */
public class NinRegistry {

    private static final Map<String, Citizen> CITIZENS = new HashMap<>();
    private static final Map<String, String> FINGERPRINTS = new HashMap<>();

    static {
        add(new Citizen("CM9001234567AB", "Samuel Chuvi", "Kampala"), "FP-7781");
        add(new Citizen("CF8805551234CD", "Grace Namuli", "Wakiso"), "FP-4420");
        add(new Citizen("CM7702229876EF", "Peter Okello", "Gulu"), "FP-9013");
    }

    private static void add(Citizen c, String fingerprint) {
        CITIZENS.put(c.nin(), c);
        FINGERPRINTS.put(c.nin(), fingerprint);
    }

    public static Optional<Citizen> find(String nin) {
        return Optional.ofNullable(CITIZENS.get(nin));
    }

    public static String fingerprintOf(String nin) {
        return FINGERPRINTS.get(nin);
    }
}
