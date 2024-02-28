package org.example;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    TND("TND");
    private final String displayName;
    Currency(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
