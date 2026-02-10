package com.cyber.portal.sharedResources.config;

import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import org.springframework.stereotype.Component;


import java.util.EnumMap;
import java.util.Set;

@Component
public class FakeKeywordConfig {

    private final EnumMap<ComplaintCategory, Set<String>> categoryKeywords =
            new EnumMap<>(ComplaintCategory.class);

    public FakeKeywordConfig() {

        categoryKeywords.put(
                ComplaintCategory.FINANCIAL_FRAUD,
                Set.of(
                        "lottery", "prize", "investment", "crypto", "fee", "urgent",
                        "reward", "bonus", "returns", "double", "profit",
                        "scheme", "ponzi", "airdrop", "trading",
                        "deposit", "withdrawal", "commission", "guaranteed"
                )
        );

        categoryKeywords.put(
                ComplaintCategory.BANKING_FRAUD,
                Set.of(
                        "otp", "bank", "account", "link", "verification",
                        "kyc", "debit", "credit", "atm", "transaction",
                        "blocked", "suspended", "refund", "chargeback",
                        "pin", "password", "alert"
                )
        );

        categoryKeywords.put(
                ComplaintCategory.ONLINE_FRAUD,
                Set.of(
                        "scam", "fake", "link", "phishing", "offer",
                        "website", "app", "download", "payment",
                        "qr", "upi", "wallet", "gateway",
                        "support", "customer", "helpline"
                )
        );

        categoryKeywords.put(
                ComplaintCategory.SOCIAL_MEDIA_FRAUD,
                Set.of(
                        "instagram", "facebook", "whatsapp", "telegram",
                        "impersonation", "profile", "hack", "clone",
                        "message", "dm", "followers", "verified",
                        "giveaway", "promotion"
                )
        );

        categoryKeywords.put(
                ComplaintCategory.HACKING,
                Set.of(
                        "hack", "breach", "unauthorized", "access",
                        "malware", "virus", "trojan", "ransomware",
                        "keylogger", "spyware", "data", "leak",
                        "server", "system", "ip", "firewall"
                )
        );
    }

    public Set<String> getKeywords(ComplaintCategory category) {
        return categoryKeywords.getOrDefault(category, Set.of());
    }
}
