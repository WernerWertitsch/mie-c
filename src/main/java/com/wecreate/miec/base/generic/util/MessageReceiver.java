package com.wecreate.miec.base.generic.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {
    public void message(String message) {
        log.info(message);
    }
}
