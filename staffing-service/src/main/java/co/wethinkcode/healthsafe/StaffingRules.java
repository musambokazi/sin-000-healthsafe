package co.wethinkcode.healthsafe;

public class StaffingRules {
    public static int doctorsOnCall(int level){
        if (level < 0 || level > 8){
            throw new IllegalArgumentException("Level must be 0-8 but was " + level);
        }
        return 2 + level;
    }
}
