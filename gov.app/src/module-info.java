/**
 * gov.app - the Government Services Portal (entry point).
 * It uses every department through its PUBLIC api only and
 * discovers all departments at runtime with ServiceLoader ("uses").
 */
module gov.app {
    requires gov.core;
    requires gov.identity;
    requires gov.tax;
    requires gov.passport;
    requires gov.business;

    uses gov.core.api.GovService;
}
