package com.koreacb.msa.edge;

import com.koreacb.msa.edge.client.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardApiGateway {
    private final Logger logger = LoggerFactory.getLogger(CardApiGateway.class);

    private final UserClient userClient;

    @Autowired
    CardApiGateway(final UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/users/{id}/cards")
    public String getUserInfo(@PathVariable String id) {
        logger.debug("Id: {}", id);
        return this.userClient.getUserInfo(id);
    }
}
