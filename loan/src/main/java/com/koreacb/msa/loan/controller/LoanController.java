package com.koreacb.msa.loan.controller;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koreacb.msa.loan.Description;
import com.koreacb.msa.loan.model.Loan;
import com.koreacb.msa.loan.model.Service;
import com.koreacb.msa.loan.repository.LoanRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/loans")
@Slf4j
public class LoanController {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "fallback")
    @Description("이름을 입력받아서 화면에 출력해 주는 API")
    public String loanGreeting(@RequestParam(required = false) final String name) {
        log.debug("/hello");
        if (name == null) {
            throw new IllegalStateException("Error occurred!");
        }
        return name;
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource> getProfile(@RequestParam(required = false) final String name) {
        log.debug("Start HATEOAS profile");
        val all = loanRepository.findAll();
        val list = new ArrayList<Resource>();
        for (Loan loan : all) {
            val resource = new Resource<>(loan);
            resource.add(linkTo(methodOn(this.getClass()).getProfile(name)).withSelfRel());
            list.add(resource);
        }
        return new Resources<>(list);
    }

    @Description("테스트용 더미 메소드")
    public String dummyMethod(@RequestParam("name") final String name, @RequestParam("age") final int age, final Map<String, String> map) {
        return "";
    }

    @GetMapping("/apis")
    @Description("API 목록 조회")
    public String getApiInformation() {
        val reflections = new Reflections("com.koreacb.msa");
        val controllers = reflections.getTypesAnnotatedWith(RestController.class);

        val services = new ArrayList<>();
        for (Class clazz : controllers) {
            val declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                val service = new Service(method);
                services.add(service);
                log.debug("{}", service);
            }
        }
        val listType = new TypeToken<List<Service>>() {
        }.getType();

        return new GsonBuilder().setPrettyPrinting().create().toJson(services, listType);
    }

    @GetMapping("/apis/{name}")
    @Description("API 검색")
    @HystrixCommand(fallbackMethod = "fallback")
    public String getApiInformation(@PathVariable("name") final String name) {
        val reflections = new Reflections("com.koreacb.msa");
        val controllers = reflections.getTypesAnnotatedWith(RestController.class);

        for (Class clazz : controllers) {
            val declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.getName().equals(name)) {
                    val service = new Service(method);
                    return new GsonBuilder().setPrettyPrinting().create().toJson(service);
                }
            }
        }
        return "";
    }

    private String fallback(final String name) {
        log.debug("Entered fallback()");
        return "fallback " + name;
    }
}
