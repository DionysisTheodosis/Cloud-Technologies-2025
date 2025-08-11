package gr.aegean.icsd.mobilemanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NetworkTechnology {
    GSM("GSM"),
    HSPA("HSPA"),
    LTE("LTE"),
    THREE_G("3G"),
    FOUR_G("4G"),
    FIVE_G("5G");

    private final String value;

    NetworkTechnology(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static NetworkTechnology fromValue(String value) {
        for (NetworkTechnology tech : values()) {
            if (tech.value.equalsIgnoreCase(value)) {
                return tech;
            }
        }
        throw new IllegalArgumentException("Λάθος Δικτυακή Τεχνολογία: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
