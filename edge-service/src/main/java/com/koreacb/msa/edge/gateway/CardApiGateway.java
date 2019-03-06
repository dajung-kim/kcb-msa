package com.koreacb.msa.edge.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardApiGateway {
    private final Logger logger = LoggerFactory.getLogger(CardApiGateway.class);
}
