package kea.dpang.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SubCategory {
    WOMEN_CLOTHES,
    MEN_CLOTHES,
    UNDERWEAR,
    SHOES,
    BAGS_WALLETS_ACCESSORIES,
    JEWELRY_WATCHES_ACCESSORIES,
    NONE;

    @JsonCreator
    public static SubCategory forValue(String value) {
        if (value.isEmpty()) {
            return null;
        }
        return SubCategory.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
