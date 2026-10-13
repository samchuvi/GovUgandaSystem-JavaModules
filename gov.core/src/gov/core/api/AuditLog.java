package gov.core.api;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Central audit log - every module records sensitive actions here.
 */
public final class AuditLog {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private AuditLog() { }

    public static void record(String module, String action) {
        System.out.println("   [AUDIT " + LocalTime.now().format(FMT) + "] "
                + String.format("%-13s", module) + " -> " + action);
    }
}
