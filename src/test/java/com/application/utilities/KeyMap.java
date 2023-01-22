package com.application.utilities;

import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyMap {

    private static final String PREFIX = "${";
    private static final String SUFFIX = "}";

    private Map<String, String> keys = new HashMap<>();

    public String get(final String key) {
        return keys.get(key);
    }

    public void add(final String cucumberParam, final String responseValue) {
        if (cucumberParam.startsWith(PREFIX) && cucumberParam.endsWith(SUFFIX)) {
            keys.put(cucumberParam.substring(PREFIX.length(), cucumberParam.length() - SUFFIX.length()), responseValue);
        }
    }

    public String apply(final String text) {
        return StringSubstitutor.replace(text, keys, PREFIX, SUFFIX);
    }

    public Map<String, String> applyJDK7(final Map<String, String> map) {
        final Map<String, String> applied = new HashMap<>();
        map.forEach((key, value) -> applied.put(key, apply(value)));
        return applied;
    }

    public Map<String, String> apply(final Map<String, String> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> apply(e.getValue())));
    }

    public List<Map<String, String>> applyJDK7(final List<Map<String, String>> mapList) {
        final List<Map<String, String>> applied = new ArrayList<>();
        mapList.forEach(map -> applied.add(apply(map)));
        return applied;
    }

    public List<Map<String, String>> apply(final List<Map<String, String>> mapList) {
        return mapList.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

}