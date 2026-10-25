package co.wethinkcode.healthsafe;

public class AlertLevel {
    public static boolean isValidLevel(int value) {
        return value >= 0 && value <= 8;
    }
}
