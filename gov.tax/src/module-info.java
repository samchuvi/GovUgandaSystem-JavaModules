/**
 * gov.tax - Tax collection (URA).
 * Depends only on the PUBLIC identity API; it cannot touch biometrics.
 */
module gov.tax {
    requires gov.core;
    requires gov.identity;

    exports gov.tax.api;

    provides gov.core.api.GovService
            with gov.tax.internal.TaxDepartment;
}
