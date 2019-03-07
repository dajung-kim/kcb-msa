package com.koreacb.msa.edge.gateway;

import com.koreacb.msa.edge.client.LoanClient;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoanApiGateway {
    private final Logger logger = LoggerFactory.getLogger(LoanApiGateway.class);

    private final LoanClient loanClient;

    @Autowired
    public LoanApiGateway(final LoanClient loanClient) {
        this.loanClient = loanClient;
    }

    @GetMapping("/loans/{id}")
    public String getLoanInfo(@PathVariable final long id) {
        logger.debug("Start getting loan information for id {}", id);
        val loanInfo = loanClient.getLoanInfo(id);
        logger.debug("Retrieved information: {}", loanInfo);
        return loanInfo;
    }
}
