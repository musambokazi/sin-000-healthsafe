package co.wethinkcode.healthsafe;

public class CsvCleaner {

    public String stripWhiteSpaces(String value) {
        return value.trim();
    }

    public String toLowerCase(String value) {
        return value.toLowerCase();
    }

    public Integer cleanBedValue(String value) {
        try {
            int number = Integer.parseInt(value);
            if (number < 0) {
                return null;
            }
            return number;
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


    public Ward cleanRow(String[] value) {
        String id = toLowerCase(stripWhiteSpaces(value[0]));
        String wing = toTitleCase(removeExtraSpaces(value[1]));
        String department = toTitleCase(removeExtraSpaces(value[2]));
        Integer bedsAvailable = cleanBedValue(value[3]);
        return new Ward(id, wing, department, bedsAvailable);
    }
}
