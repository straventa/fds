package com.yokke.usermanagement.transactions.fds_transactions.report;

import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransactionsMerchantReportDto;
import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransactionsRuleNameReportsDto;
import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransationsAnalystReportDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "", produces = "application/json")
@Tags(
        {
                @Tag(name = "Fds Transactions"),
                @Tag(name = "Report")
        }
)
@RequiredArgsConstructor
public class FdsTransactionReportController {
    private final FdsTransactionsReportService fdsTransactionsReportService;

    @GetMapping("/fds/api/fds-transaction/report/analyst")
    public ResponseEntity<List<FdsTransationsAnalystReportDto>> next(
            @RequestParam(name = "start-date", required = false)


            OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) {
        return ResponseEntity.ok(fdsTransactionsReportService.statisticsByAnalyst(
                startDate, endDate
        ));
    }

    @GetMapping("/fds/api/fds-transaction/report/analyst/excel")
    public ResponseEntity<ByteArrayResource> downloadExcel(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] excelContent = fdsTransactionsReportService.generateExcelReport(
                startDate, endDate
        );

        ByteArrayResource resource = new ByteArrayResource(excelContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_analyst_report_" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelContent.length)
                .body(resource);
    }

    @GetMapping("/fds/api/fds-transaction/report/analyst/csv")
    public ResponseEntity<ByteArrayResource> downloadCsv(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] csvContent = fdsTransactionsReportService.generateCsvReport(
                startDate,
                endDate
        );

        ByteArrayResource resource = new ByteArrayResource(csvContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_analyst_report_" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(csvContent.length)
                .body(resource);
    }


    @GetMapping("/fds/api/fds-transaction/report/rule-name")
    public ResponseEntity<List<FdsTransactionsRuleNameReportsDto>> readReportByAnalyst(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) {
        return ResponseEntity.ok(fdsTransactionsReportService.statisticsByRuleName(
                startDate, endDate
        ));
    }

    @GetMapping("/fds/api/fds-transaction/report/rule-name/excel")
    public ResponseEntity<ByteArrayResource> downloadExcelReportByAnalyst(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] excelContent = fdsTransactionsReportService.generateExcelRuleNameReport(
                startDate, endDate
        );

        ByteArrayResource resource = new ByteArrayResource(excelContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_rule_name_report" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelContent.length)
                .body(resource);
    }

    @GetMapping("/fds/api/fds-transaction/report/rule-name/csv")
    public ResponseEntity<ByteArrayResource> downloadCsvReportByAnalyst(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] csvContent = fdsTransactionsReportService.generateCsvRuleNameReport(
                startDate,
                endDate
        );

        ByteArrayResource resource = new ByteArrayResource(csvContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_rule_name_report" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(csvContent.length)
                .body(resource);
    }


    @GetMapping("/fds/api/fds-transaction/report/merchant")
    public ResponseEntity<List<FdsTransactionsMerchantReportDto>> readReportByMerchant(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) {
        return ResponseEntity.ok(fdsTransactionsReportService.statisticsByMerchant(
                startDate, endDate
        ));
    }

    @GetMapping("/fds/api/fds-transaction/report/merchant/excel")
    public ResponseEntity<ByteArrayResource> downloadExcelReportByMerchant(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] excelContent = fdsTransactionsReportService.generateExcelMerchantReport(
                startDate, endDate
        );

        ByteArrayResource resource = new ByteArrayResource(excelContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_merchant_report_" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelContent.length)
                .body(resource);
    }

    @GetMapping("/fds/api/fds-transaction/report/merchant/csv")
    public ResponseEntity<ByteArrayResource> downloadCsvReportByMerchant(
            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate
    ) throws IOException {

        byte[] csvContent = fdsTransactionsReportService.generateCsvMerchantReport(
                startDate,
                endDate
        );

        ByteArrayResource resource = new ByteArrayResource(csvContent);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "fds_merchant_report_" + startDate.format(formatter) + "___" + endDate.format(formatter) + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(csvContent.length)
                .body(resource);
    }
}
