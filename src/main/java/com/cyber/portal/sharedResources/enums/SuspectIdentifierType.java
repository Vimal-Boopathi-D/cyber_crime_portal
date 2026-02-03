package com.cyber.portal.sharedResources.enums;

public enum SuspectIdentifierType {
    MOBILE("Mobile"),
    EMAIL("Email"),
    BANK_ACCOUNT("Bank Account"),
    UPI_ID("UPI Id"),
    SOCIAL_MEDIA("Social Media"),
    WEBSITE_APP("Website App");

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
