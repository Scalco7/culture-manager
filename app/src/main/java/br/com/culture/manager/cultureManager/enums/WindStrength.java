package br.com.culture.manager.cultureManager.enums;

public enum WindStrength {
    STRONG("Forte"),
    MODERATE("Moderado"),
    WEAK("Fraco");

    private final String description;

    WindStrength(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
