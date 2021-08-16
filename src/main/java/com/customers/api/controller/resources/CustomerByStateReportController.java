package com.customers.api.controller.resources;

import com.customers.api.controller.DTO.reports.CustomerByStateReportDTO;
import com.customers.api.model.services.reports.CustomerByStateReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class CustomerByStateReportController {

    private CustomerByStateReportService customerByStateReportService;

    @GetMapping("/customer-by-state")
    public Flux<CustomerByStateReportDTO> report(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt){
        return customerByStateReportService.report(startAt,endAt);
    }

}
