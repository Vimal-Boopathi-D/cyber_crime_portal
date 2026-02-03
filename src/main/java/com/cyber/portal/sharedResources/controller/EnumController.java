package com.cyber.portal.sharedResources.controller;

import com.cyber.portal.sharedResources.enums.ComplaintCategory;
import com.cyber.portal.sharedResources.enums.State;
import com.cyber.portal.sharedResources.enums.SuspectIdentifierType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class EnumController {
    @GetMapping("/categories")
    public List<Map<String, String>> getComplaintCategories() {

        List<Map<String, String>> list = new ArrayList<>();
        for (ComplaintCategory category : ComplaintCategory.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", category.getValue()); // ONLINE_FRAUD
            map.put("label", category.getcategory()); // Online Fraud
            list.add(map);
        }
        return list;
    }

    @GetMapping("/state")
    public List<Map<String, String>> getComplaintstates() {

        List<Map<String, String>> list = new ArrayList<>();
        for (State states : State.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", states.getValue()); // ONLINE_FRAUD
            map.put("label", states.getState()); // Online Fraud
            list.add(map);
        }
        return list;
    }


    @GetMapping("/Suspect/Type")
    public List<Map<String, String>> getSuspectType() {

        List<Map<String, String>> list = new ArrayList<>();
        for (SuspectIdentifierType suspectIdentifierType : SuspectIdentifierType.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", suspectIdentifierType.getValue()); // ONLINE_FRAUD
            map.put("label", suspectIdentifierType.gettype()); // Online Fraud
            list.add(map);
        }
        return list;
    }
}
