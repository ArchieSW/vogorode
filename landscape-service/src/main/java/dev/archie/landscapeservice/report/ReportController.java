package dev.archie.landscapeservice.report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{fieldId}")
    public Report getReportByFieldId(@PathVariable Long fieldId) {
        return reportService.getReport(fieldId);
    }
}
