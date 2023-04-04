package com.api.webvote.tests.stubs;

import com.api.webvote.v1.model.Associate;

public interface AssociateStub {

    static Associate associateDefault(){
        return new Associate(1L, "Ralf Drehmer Wink", "000.000.000-00");
    }

    static Associate associateWithInvalidCpf(){
        return new Associate(1L, "Ralf Drehmer Wink", "000.000.000-01");
    }

    static Associate associateWithMoreThan11Characters(){
        return new Associate(1L, "Ralf Drehmer Wink", "000.000.000-001");
    }
}
