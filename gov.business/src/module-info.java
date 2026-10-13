/**
 * gov.business - Business registration (URSB).
 * Reuses the identity and tax modules through their public APIs only.
 */
module gov.business {
    requires gov.core;
    requires gov.identity;
    requires gov.tax;

    exports gov.business.api;

    provides gov.core.api.GovService
            with gov.business.internal.BusinessDepartment;
}
