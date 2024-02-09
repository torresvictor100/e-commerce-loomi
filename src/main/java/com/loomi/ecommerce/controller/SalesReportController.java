package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.SalesReport;
import com.loomi.ecommerce.service.SalesReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesreport")
public class SalesReportController {
    @Autowired
    private SalesReportService salesReportService;

    @Operation(summary = "Find all Sales Report", tags = "SalesReport")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SalesReport>> findAll() {
        return ResponseEntity.ok(salesReportService.findAll());
    }

    @Operation(summary = "Find Sales Report by ID", tags = "SalesReport")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{sales_report_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SalesReport> findById(@PathVariable(name = "sales_report_id") Long id) {
        SalesReport salesReport = salesReportService.findById(id);
        if (salesReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(salesReport, HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Sales Report", tags = "SalesReport")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SalesReport> save(@RequestBody SalesReport salesReport) {
        try {
            salesReport = salesReportService.save(salesReport);
            return new ResponseEntity<>(salesReport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Sales Report", tags = "SalesReport")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/{sales_report_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SalesReport> update(@PathVariable(name = "sales_report_id") Long id,
                                              @RequestBody SalesReport salesReport) {
        salesReport.setId(id);
        try {
            salesReport = salesReportService.update(salesReport);
            if (salesReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(salesReport, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a Sales Report", tags = "SalesReport")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{sales_report_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "sales_report_id") Long id) {
        salesReportService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
