package com.company.example.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LogStatService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void logInfo(String url) {
        log.info(url);
    }

}
