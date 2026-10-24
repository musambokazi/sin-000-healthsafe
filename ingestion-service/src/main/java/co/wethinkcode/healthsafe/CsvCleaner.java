package co.wethinkcode.healthsafe;

public class CsvCleaner {

    public String stripWhiteSpaces(String value) {
        return value.trim();
    }

    public String toLowerCase(String value) {
        return value.toLowerCase();
    }

    public String cleanBedValue(String value) {
        try {
            int number = Integer.parseInt(value);
            if (number < 0){
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String removeExtraSpaces(String value) {
        return value.strip().replaceAll("\\s+", " ");
    }
}
