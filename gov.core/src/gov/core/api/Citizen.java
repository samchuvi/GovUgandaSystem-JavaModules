package gov.core.api;

/**
 * Basic citizen record shared across all modules.
 */
public record Citizen(String nin, String fullName, String district) {

    @Override
    public String toString() {
        return fullName + " (NIN: " + nin + ", " + district + ")";
    }
}
