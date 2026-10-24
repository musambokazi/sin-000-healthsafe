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

    public String toTitleCase(String value) {
        String[] words = toLowerCase(value).split(" ");
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            }
        }
        return String.join(" ", words);
    }
}
