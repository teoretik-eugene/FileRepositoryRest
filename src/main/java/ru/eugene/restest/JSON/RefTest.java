package ru.eugene.restest.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefTest {
    @JsonProperty("ref")
    private String ref;

    public RefTest() {
    }

    public RefTest(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
