/**
 * gov.identity - National Identification (NIRA).
 *
 *  - gov.identity.api      : exported to EVERY module (public NIN lookup/verification)
 *  - gov.identity.secure   : QUALIFIED export - ONLY gov.passport may use biometrics
 *  - gov.identity.internal : NOT exported - the raw NIN registry is completely hidden
 */
module gov.identity {
    requires transitive gov.core;

    exports gov.identity.api;
    exports gov.identity.secure to gov.passport;

    provides gov.core.api.GovService
            with gov.identity.internal.IdentityDepartment;
}
