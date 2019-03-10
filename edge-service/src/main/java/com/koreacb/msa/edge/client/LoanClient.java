package com.koreacb.msa.edge.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "loan")
public interface LoanClient {
//    @GetMapping(value = "/loans/{id}")
//    String getLoanInfo(@PathVariable("id") long id);
}
