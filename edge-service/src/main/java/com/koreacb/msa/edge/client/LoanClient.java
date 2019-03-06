package com.koreacb.msa.edge.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "loan")
public interface LoanClient {
}
