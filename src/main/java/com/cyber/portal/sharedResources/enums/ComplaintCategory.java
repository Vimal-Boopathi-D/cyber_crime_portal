package com.cyber.portal.sharedResources.enums;

public enum ComplaintCategory {
    ONLINE_FRAUD("Online Fraud"),
    BANKING_FRAUD("Banking Fraud"),
    FINANCIAL_FRAUD("Financial Fraud"),
    SOCIAL_MEDIA_FRAUD("Social Media Fraud"),
    CYBER_BULLYING("Cyber Bullying"),
    WOMEN_CHILDREN_CRIME("Women / Children Related Crime"),
    HACKING("Hacking"),
    OTHER_CYBERCRIME("Other Cyber Crime");

    private final String category;

    ComplaintCategory(String category) {
        this.category=category;
    }
    public String getValue() {
        return name();
    }

    public String getcategory() {
        return category;
    }
}
