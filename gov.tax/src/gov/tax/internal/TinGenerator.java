package gov.tax.internal;

/** Hidden helper - not exported, so no other module can forge TINs. */
public class TinGenerator {

    private static long counter = 1000234560L;

    public static synchronized String next() {
        return String.valueOf(++counter);
    }
}
