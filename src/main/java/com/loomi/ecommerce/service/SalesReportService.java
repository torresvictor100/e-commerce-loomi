package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.SalesReport;
import com.loomi.ecommerce.repository.SalesReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesReportService {

    @Autowired
    private SalesReportRepository salesReportRepository;

    public SalesReportService(SalesReportRepository salesReportRepository) {
        this.salesReportRepository = salesReportRepository;
    }

    public List<SalesReport> findAll(){
        return salesReportRepository.findAll();
    }

    public SalesReport save(SalesReport salesReport){
        salesReport.setId(null);
        return salesReportRepository.save(salesReport);
    }

    public SalesReport findById(Long id) {
        Optional<SalesReport> optionalSalesReport=  salesReportRepository.findById(id);
        return optionalSalesReport.orElse(null);
    }

    public SalesReport update(SalesReport salesReport) {
        SalesReport salesReportFound = findById(salesReport.getId());
        if (salesReportFound != null) {
            return salesReportRepository.save(salesReport);
        }else{
            return salesReport;
        }
    }

    public void deleteById(Long id) {
        SalesReport salesReport = new SalesReport();
        salesReport.setId(id);
        salesReportRepository.delete(salesReport);

    }
}
