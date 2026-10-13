/**
 * gov.passport - Passport processing (Immigration).
 * The ONLY module allowed to use gov.identity.secure (biometrics).
 */
module gov.passport {
    requires gov.core;
    requires gov.identity;

    exports gov.passport.api;

    provides gov.core.api.GovService
            with gov.passport.internal.PassportDepartment;
}
