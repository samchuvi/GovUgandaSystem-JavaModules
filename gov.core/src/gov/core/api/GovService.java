package gov.core.api;

/**
 * Common contract every government department module implements.
 * Departments register their implementation with "provides ... with"
 * in their module-info, and the main app discovers them with ServiceLoader.
 */
public interface GovService {

    /** Department name, e.g. "National Identification (NIRA)". */
    String departmentName();

    /** Short description of what the department module offers. */
    String description();
}
