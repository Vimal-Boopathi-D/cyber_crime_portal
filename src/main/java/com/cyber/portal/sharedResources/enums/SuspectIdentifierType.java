package com.cyber.portal.sharedResources.enums;

public enum SuspectIdentifierType {
    MOBILE("Mobile"),
    EMAIL("Email"),
    BANK_ACCOUNT("Bank Account"),
    WEBSITE_URL("Website Url"),
    WHATSAPP("Whatsapp"),
    TELEGRAM("Telegram"),
    SMS_HEADER("SMS Header"),
    SOCIAL_MEDIA("Social Media"),
    MOBILE_APP("Mobile App");

    public final String type;

    SuspectIdentifierType(String type) {
        this.type=type;
    }

    public String getValue(){
        return name();
    }
    public String gettype(){
        return type;
    }



}
