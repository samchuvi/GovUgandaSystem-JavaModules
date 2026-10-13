# Uganda E-Government Services Portal (Java Modules)

**Course:** BIT2115 – Applied Object-Oriented Programming  
**Problem:** Java Modules (JPMS)  
**Student:** Chuvi Samuel

A modular Java application that combines tax collection, national identification,
passport processing and business registration. Each government department is an
independent Java module, and `module-info.java` controls exactly which modules may
access which packages.

## Modules

| Module | Department | Access rules |
|---|---|---|
| `gov.core` | Shared foundation (`Citizen`, `AuditLog`, `GovService`) | exports `gov.core.api` |
| `gov.identity` | NIRA – National ID | `api` exported to all; `secure` exported **only to gov.passport**; `internal` hidden |
| `gov.tax` | URA – Tax collection | exports `api`, hides TIN generator |
| `gov.passport` | Immigration – Passports | the only module allowed to use biometrics |
| `gov.business` | URSB – Business registration | reuses identity + tax public APIs |
| `gov.app` | Services portal (entry point) | discovers departments with `ServiceLoader` |

## Run

Open the folder in IntelliJ IDEA and run the **GovPortal** configuration, or:

```bash
javac -d out --module-source-path "./*/src" $(find . -name "*.java")
java --module-path out -m gov.app/gov.app.Main
```
