package com.cyber.portal.sharedResources.config;

import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class KeywordWeightConfig {

    private final EnumMap<ComplaintCategory, Map<String, Double>> weights =
            new EnumMap<>(ComplaintCategory.class);

    public KeywordWeightConfig() {

        weights.put(
                ComplaintCategory.FINANCIAL_FRAUD,
                Map.ofEntries(
                        Map.entry("lottery", 2.8),
                        Map.entry("prize", 2.4),
                        Map.entry("crypto", 2.9),
                        Map.entry("investment", 2.6),
                        Map.entry("scheme", 2.5),
                        Map.entry("ponzi", 3.0),
                        Map.entry("airdrop", 2.7),
                        Map.entry("guaranteed", 2.6),
                        Map.entry("urgent", 2.3),
                        Map.entry("fee", 2.1)
                )
        );

        weights.put(
                ComplaintCategory.BANKING_FRAUD,
                Map.ofEntries(
                        Map.entry("otp", 3.0),
                        Map.entry("pin", 2.8),
                        Map.entry("password", 2.7),
                        Map.entry("kyc", 2.5),
                        Map.entry("blocked", 2.3),
                        Map.entry("suspended", 2.4),
                        Map.entry("transaction", 2.2),
                        Map.entry("refund", 2.1),
                        Map.entry("link", 2.4)
                )
        );

        weights.put(
                ComplaintCategory.ONLINE_FRAUD,
                Map.ofEntries(
                        Map.entry("phishing", 3.0),
                        Map.entry("scam", 2.8),
                        Map.entry("fake", 2.4),
                        Map.entry("qr", 2.6),
                        Map.entry("upi", 2.5),
                        Map.entry("gateway", 2.3),
                        Map.entry("offer", 2.2)
                )
        );

        weights.put(
                ComplaintCategory.SOCIAL_MEDIA_FRAUD,
                Map.ofEntries(
                        Map.entry("impersonation", 3.0),
                        Map.entry("clone", 2.7),
                        Map.entry("hack", 2.5),
                        Map.entry("giveaway", 2.4),
                        Map.entry("promotion", 2.2)
                )
        );

        weights.put(
                ComplaintCategory.HACKING,
                Map.ofEntries(
                        Map.entry("ransomware", 3.2),
                        Map.entry("malware", 2.8),
                        Map.entry("breach", 2.6),
                        Map.entry("unauthorized", 2.5),
                        Map.entry("data", 2.3),
                        Map.entry("leak", 2.7)
                )
        );
    }

    public double getWeight(ComplaintCategory category, String keyword) {
        return weights
                .getOrDefault(category, Map.of())
                .getOrDefault(keyword, 1.0);
    }
}
