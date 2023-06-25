package dev.archie.landscapeservice.report;

import dev.archie.landscapeservice.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final OrderRepository orderRepository;

    public Report getReport(Long fieldId) {
        Report report = new Report();
        report.setWorks(orderRepository.findDistinctByField_Id(fieldId));
        report.setWorkers(orderRepository.findHandymenByField_Id(fieldId));
        report.setIsWorkDeficit(orderRepository.isWorkDeficit(fieldId));
        return report;
    }

}
