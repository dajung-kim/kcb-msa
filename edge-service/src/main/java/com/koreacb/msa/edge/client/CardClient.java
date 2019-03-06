package com.koreacb.msa.edge.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "card")
public interface CardClient {

}
