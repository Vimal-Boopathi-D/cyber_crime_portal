package com.cyber.portal.sharedResources.enums;

public enum ComplaintCategory {
    ONLINE_FRAUD("Online fraud"),
    SOCIAL_MEDIA_ABUSE("Social Media Issue"),
    BANKING_FRAUD("Banking fraud"),
    CYBER_BULLYING("Cyber Bullying"),
    HACKING("Hacking");

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
