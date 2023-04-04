package com.api.webvote.v1.service.associate.check;

import com.api.webvote.v1.model.Associate;
import org.springframework.stereotype.Component;

@Component
public interface AssociateSystemChecker {

    void check(Associate associate);
}
