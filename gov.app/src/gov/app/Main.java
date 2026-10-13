package gov.app;

import gov.business.api.BusinessService;
import gov.core.api.GovService;
import gov.identity.api.IdentityService;
import gov.passport.api.PassportService;
import gov.tax.api.TaxService;

// import gov.identity.secure.BiometricVault;
//   ^ Uncomment this line and the project will NOT compile:
//     "package gov.identity.secure is declared in module gov.identity,
//      which does not export it to module gov.app"

// import gov.identity.internal.NinRegistry;
//   ^ Same: the internal package is not exported at all.

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        banner("UGANDA E-GOVERNMENT SERVICES PORTAL (Java Modules Demo)");

        // ---------------------------------------------------------------
        // 1. INDEPENDENT MODULES discovered at runtime (scalability)
        // ---------------------------------------------------------------
        section("1. Departments discovered with ServiceLoader");
        int count = 0;
        for (GovService dept : ServiceLoader.load(GovService.class)) {
            count++;
            System.out.printf("   %d. %-32s - %s  [module: %s]%n",
                    count, dept.departmentName(), dept.description(),
                    dept.getClass().getModule().getName());
        }
        System.out.println("   Total departments plugged in: " + count);

        // ---------------------------------------------------------------
        // 2. Using each department through its PUBLIC API
        // ---------------------------------------------------------------
        section("2. National ID verification (gov.identity)");
        IdentityService identity = new IdentityService();
        identity.isValidNin("CM9001234567AB");
        identity.isValidNin("XX0000000000ZZ");

        section("3. Tax collection (gov.tax)");
        TaxService tax = new TaxService();
        String tin = tax.registerTaxpayer("CF8805551234CD");
        System.out.println("   TIN issued: " + tin);
        double income = 1_500_000;
        System.out.printf("   Monthly PAYE on UGX %,.0f = UGX %,.0f%n", income, tax.computeMonthlyPaye(income));

        section("4. Passport processing (gov.passport uses restricted biometrics)");
        PassportService passport = new PassportService();
        System.out.println("   " + passport.applyForPassport("CM9001234567AB", "FP-7781"));
        System.out.println("   " + passport.applyForPassport("CM7702229876EF", "FP-0000"));

        section("5. Business registration (gov.business reuses identity + tax)");
        BusinessService business = new BusinessService();
        System.out.println("   " + business.registerBusiness("CM7702229876EF", "Okello Hardware Ltd"));
        System.out.println("   " + business.registerBusiness("BAD-NIN", "Ghost Traders"));

        // ---------------------------------------------------------------
        // 6. CONTROLLED ACCESSIBILITY - proven by the JVM at runtime
        // ---------------------------------------------------------------
        section("6. Access control matrix (who can see which package)");
        printAccessMatrix();

        section("7. Attempting to break in with reflection");
        tryToAccess("gov.identity.internal.NinRegistry");
        tryToAccess("gov.identity.secure.BiometricVault");
        tryToAccess("gov.tax.internal.TinGenerator");

        banner("END OF DEMO");
    }

    /** Asks the JVM which module may see which package. */
    private static void printAccessMatrix() {
        ModuleLayer layer = ModuleLayer.boot();
        Module identity = layer.findModule("gov.identity").orElseThrow();

        String[] packages = {"gov.identity.api", "gov.identity.secure", "gov.identity.internal"};
        String[] readers = {"gov.app", "gov.tax", "gov.passport", "gov.business"};

        System.out.printf("   %-24s", "package \\ module");
        for (String r : readers) System.out.printf("%-14s", r);
        System.out.println();

        for (String pkg : packages) {
            System.out.printf("   %-24s", pkg);
            for (String r : readers) {
                Module reader = layer.findModule(r).orElseThrow();
                System.out.printf("%-14s", identity.isExported(pkg, reader) ? "ALLOWED" : "denied");
            }
            System.out.println();
        }
    }

    /** Tries to create an object from another module's hidden package. */
    private static void tryToAccess(String className) {
        try {
            Class<?> cls = Class.forName(className);
            cls.getDeclaredConstructor().newInstance();
            System.out.println("   [!] Accessed " + className + " (should not happen)");
        } catch (IllegalAccessException e) {
            System.out.println("   BLOCKED: " + className + " -> IllegalAccessException (package not exported to gov.app)");
        } catch (Exception e) {
            System.out.println("   BLOCKED: " + className + " -> " + e.getClass().getSimpleName());
        }
    }

    private static void banner(String text) {
        String line = "=".repeat(80);
        System.out.println(line + "\n  " + text + "\n" + line);
    }

    private static void section(String title) {
        System.out.println("\n>> " + title);
    }
}
