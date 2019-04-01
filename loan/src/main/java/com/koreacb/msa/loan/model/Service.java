package com.koreacb.msa.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private List<String> input = new ArrayList<>();
    private String output;
    private String name;
    private String description;

    public Service(Method method) {
        val parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if (requestParam != null) {
                this.input.add(requestParam.value() + "|" + parameter.getType().getName());
            } else {
                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                if (pathVariable != null) {
                    this.input.add(pathVariable.value() + "|" + parameter.getType().getName());
                }
            }
        }
        this.output = method.getReturnType().getTypeName();
        this.description = method.getAnnotation(Description.class) != null ? method.getAnnotation(Description.class).value() : "";
        this.name = method.getName();
    }
}
