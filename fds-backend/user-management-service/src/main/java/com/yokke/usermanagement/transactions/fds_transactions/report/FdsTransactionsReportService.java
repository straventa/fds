package com.yokke.usermanagement.transactions.fds_transactions.report;

import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactionsRepository;
import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransactionsMerchantReportDto;
import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransactionsRuleNameReportsDto;
import com.yokke.usermanagement.transactions.fds_transactions.report.dto.FdsTransationsAnalystReportDto;
import com.yokke.usermanagement.transactions.fds_transactions.report.projection.FdsTransactionsMerchantReportsProjection;
import com.yokke.usermanagement.transactions.fds_transactions.report.projection.FdsTransactionsRuleNameReportsProjection;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FdsTransactionsReportService {
    private final FdsTransactionsRepository fdsTransactionsRepository;

    public List<FdsTransationsAnalystReportDto> statisticsByAnalyst(

            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) {
        List<FdsTransationsAnalystReportDto> res = new ArrayList<>();
        String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDateFormatted = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<List<Object>> result = fdsTransactionsRepository.countByAnalyst(
                startDate.toLocalDateTime(),
                endDate.toLocalDateTime()
        );
        for (List<Object> objects : result) {
            FdsTransationsAnalystReportDto alertFdsParameterAnalystDto = new FdsTransationsAnalystReportDto();
            alertFdsParameterAnalystDto.setRiskLevel((objects.get(0) == null ? "0" : objects.get(0)).toString());
            alertFdsParameterAnalystDto.setAnalyst((objects.get(1) == null ? "0" : objects.get(1)).toString());
            alertFdsParameterAnalystDto.setGenuine((objects.get(2) == null ? "0" : objects.get(2)).toString());
            alertFdsParameterAnalystDto.setInvestigation((objects.get(3) == null ? "0" : objects.get(3)).toString());
            alertFdsParameterAnalystDto.setWatchlist((objects.get(4) == null ? "0" : objects.get(4)).toString());
            alertFdsParameterAnalystDto.setFraud((objects.get(5) == null ? "0" : objects.get(5)).toString());
            alertFdsParameterAnalystDto.setRemind((objects.get(6) == null ? "0" : objects.get(6)).toString());
            alertFdsParameterAnalystDto.setTotal((objects.get(7) == null ? "0" : objects.get(7)).toString());
            res.add(alertFdsParameterAnalystDto);
        }
        return res;
    }

    public byte[] generateExcelReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {
        List<FdsTransationsAnalystReportDto> reportData = statisticsByAnalyst(
                startDate,
                endDate
        );

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FDS Analyst Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Risk Level", "Analyst", "Genuine", "Investigation", "Watchlist", "Fraud", "Remind", "Total"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (FdsTransationsAnalystReportDto data : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getRiskLevel());
                row.createCell(1).setCellValue(data.getAnalyst());
                row.createCell(2).setCellValue(data.getGenuine());
                row.createCell(3).setCellValue(data.getInvestigation());
                row.createCell(4).setCellValue(data.getWatchlist());
                row.createCell(5).setCellValue(data.getFraud());
                row.createCell(6).setCellValue(data.getRemind());
                row.createCell(7).setCellValue(data.getTotal());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generateCsvReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {

        List<FdsTransationsAnalystReportDto> reportData = statisticsByAnalyst(
                startDate,
                endDate
        );
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out)) {

            // Write CSV header
            writer.println("Risk Level,Analyst,Genuine,Investigation,Watchlist,Fraud,Investigation,Total");

            // Write data rows
            for (FdsTransationsAnalystReportDto data : reportData) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s\n",
                        escapeSpecialCharacters(data.getAnalyst()),
                        data.getAnalyst(),
                        data.getGenuine(),
                        data.getInvestigation(),
                        data.getWatchlist(),
                        data.getFraud(),
                        data.getRemind(),
                        data.getTotal());
            }

            writer.flush();
            return out.toByteArray();
        }
    }

    public List<FdsTransactionsRuleNameReportsDto> statisticsByRuleName(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) {
        List<FdsTransactionsRuleNameReportsDto> res = new ArrayList<>();
        List<FdsTransactionsRuleNameReportsProjection> result = fdsTransactionsRepository.countByRuleName(
                startDate.toLocalDateTime(),
                endDate.toLocalDateTime()
        );
        return result.stream()
                .map(p -> FdsTransactionsRuleNameReportsDto.builder()
                        .ruleName(p.getRuleName())
                        .riskLevel(p.getRiskLevel())
                        .genuine(String.valueOf(p.getGenuine()))
                        .investigation(String.valueOf(p.getInvestigation()))
                        .watchlist(String.valueOf(p.getWatchlist()))
                        .fraud(String.valueOf(p.getFraud()))
                        .remind(String.valueOf(p.getRemind()))
                        .pending(String.valueOf(p.getPending()))
                        .total(String.valueOf(p.getTotal()))
                        .falsePositive(p.getFalsePositive() + "%")
                        .build())
                .collect(Collectors.toList());
    }

    public byte[] generateExcelRuleNameReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {
        List<FdsTransactionsRuleNameReportsDto> reportData = statisticsByRuleName(
                startDate,
                endDate
        );

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FDS Analyst Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Rule Name", "Risk Level", "Genuine", "Investigation", "Watchlist", "Fraud", "Remind", "Pending", "Total Alert", "False Positive"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (FdsTransactionsRuleNameReportsDto data : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getRuleName());
                row.createCell(1).setCellValue(data.getRiskLevel());
                row.createCell(2).setCellValue(data.getGenuine());
                row.createCell(3).setCellValue(data.getInvestigation());
                row.createCell(4).setCellValue(data.getWatchlist());
                row.createCell(5).setCellValue(data.getFraud());
                row.createCell(6).setCellValue(data.getRemind());
                row.createCell(7).setCellValue(data.getPending());
                row.createCell(8).setCellValue(data.getTotal());
                row.createCell(9).setCellValue(data.getFalsePositive());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generateCsvRuleNameReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {
        List<FdsTransactionsRuleNameReportsDto> reportData = statisticsByRuleName(
                startDate,
                endDate
        );
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out)) {

            // Write CSV header
            writer.println("Rule Name,Risk Level, Genuine, Investigation, Watchlist, Fraud, Remind, Pending, Total Alert, False Positive");

            // Write data rows
            for (FdsTransactionsRuleNameReportsDto data : reportData) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        escapeSpecialCharacters(data.getRuleName()),
                        escapeSpecialCharacters(data.getRiskLevel()),
                        data.getGenuine(),
                        data.getInvestigation(),
                        data.getWatchlist(),
                        data.getFraud(),
                        data.getRemind(),
                        data.getPending(),
                        data.getTotal(),
                        data.getFalsePositive());
            }

            writer.flush();
            return out.toByteArray();
        }
    }

//

    public List<FdsTransactionsMerchantReportDto> statisticsByMerchant(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) {
        List<FdsTransactionsMerchantReportDto> res = new ArrayList<>();
        List<FdsTransactionsMerchantReportsProjection> result = fdsTransactionsRepository.countByMerchant(
                startDate.toLocalDateTime(),
                endDate.toLocalDateTime()
        );

        return result.stream()
                .map(p -> FdsTransactionsMerchantReportDto.builder()
                        .mid(p.getMID())
                        .merchantName(p.getMerchantName())
                        .memberBank(p.getMemberBank())
                        .channel(p.getChannel())
                        .genuine(String.valueOf(p.getGenuine()))
                        .investigation(String.valueOf(p.getInvestigation()))
                        .watchlist(String.valueOf(p.getWatchlist()))
                        .fraud(String.valueOf(p.getFraud()))
                        .remind(String.valueOf(p.getRemind()))
                        .pending(String.valueOf(p.getPending()))
                        .total(String.valueOf(p.getTotalAlert()))
                        .falsePositive(p.getFalsePositive() + "%")
                        .build())
                .collect(Collectors.toList());
    }

    public byte[] generateExcelMerchantReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {
        List<FdsTransactionsMerchantReportDto> reportData = statisticsByMerchant(
                startDate,
                endDate
        );

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FDS Merchant Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"MID", "Merchant Name", "Member Bank", "Channel", "Genuine", "Investigation", "Watchlist", "Fraud", "Remind", "Pending", "Total Alert", "False Positive"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (FdsTransactionsMerchantReportDto data : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getMid());
                row.createCell(1).setCellValue(data.getMerchantName());
                row.createCell(2).setCellValue(data.getMemberBank());
                row.createCell(3).setCellValue(data.getChannel());
                row.createCell(4).setCellValue(data.getGenuine());
                row.createCell(5).setCellValue(data.getInvestigation());
                row.createCell(6).setCellValue(data.getWatchlist());
                row.createCell(7).setCellValue(data.getFraud());
                row.createCell(8).setCellValue(data.getPending());
                row.createCell(9).setCellValue(data.getTotal());
                row.createCell(10).setCellValue(data.getRemind());
                row.createCell(11).setCellValue(data.getFalsePositive());

            }
            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generateCsvMerchantReport(
            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) throws IOException {
        List<FdsTransactionsMerchantReportDto> reportData = statisticsByMerchant(
                startDate,
                endDate
        );
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out)) {

            // Write CSV header
            writer.println("MID, Merchant Name, Member Bank, Channel, Genuine, Investigation, Watchlist, Fraud, Remind, Pending, Total Alert, False Positive");

            // Write data rows
            for (FdsTransactionsMerchantReportDto data : reportData) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        escapeSpecialCharacters(data.getMid()),
                        escapeSpecialCharacters(data.getMerchantName()),
                        escapeSpecialCharacters(data.getMemberBank()),
                        escapeSpecialCharacters(data.getChannel()),
                        data.getGenuine(),
                        data.getInvestigation(),
                        data.getWatchlist(),
                        data.getFraud(),
                        data.getRemind(),
                        data.getPending(),
                        data.getTotal(),
                        data.getFalsePositive());
            }
            writer.flush();
            return out.toByteArray();
        }
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
//
//    public byte[] generateExcelFdsTransactionsReport(
//            OffsetDateTime startDate,
//            OffsetDateTime endDate,
//            String mid,
//            String tid,
//            String assignedTo,
//            String actionType,
//            Boolean actionTypeIsNull,
//            Boolean actionTypeIsNotNull,
//            Pageable pageable
//    ) throws IOException {
//        Page<FdsTransactionsDto> reportData = read(
//                startDate,
//                endDate,
//                mid,
//                tid,
//                assignedTo,
//                actionType,
//                actionTypeIsNull,
//                actionTypeIsNotNull,
//                pageable
//        );
//
//        try (Workbook workbook = new XSSFWorkbook();
//             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("FDS Transactions Alert");
//
//            // Create header row
//            Row headerRow = sheet.createRow(0);
//            String[] columns = {
//                    "Auth Seq No", "Card No", "Member Bank Acq", "Merchant Name", "MID", "TID", "RRN", "Issuer",
//                    "Auth Date", "Auth Time", "Auth Amount", "Trace No", "Message Type Id", "Auth Sale Type",
//                    "Auth Intn Rspn Cd", "Reason Contents", "Installment Count", "Switch Brand",
//                    "POS Entry Mode Detail", "Card Type Code", "Onus Code", "ECI Value", "Approval Code", "PG Name",
//                    "PG Type", "Issuer Member No", "Business Type", "Channel", "Issuer Country", "Parameter Values",
//                    "Action Type", "Fraud Type", "Fraud Pod Type", "Fraud Note", "Remind Note", "Remind Date",
//                    "Assigned Date Time", "Confirmed Date Time", "FDS Transaction ID", "Rule",
//                    "FDS Transactions Audit", "Confirmed User Account", "Assigned User Account"
//            };
//            for (int i = 0; i < columns.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(columns[i]);
//            }
//
//            // Populate data rows
//            int rowNum = 1;
//            for (FdsTransactionsDto data : reportData) {
//                Row row = sheet.createRow(rowNum++);
//
//                row.createCell(0).setCellValue(data.getAuthSeqNo());
//                row.createCell(1).setCellValue(data.getCardNo());
//                row.createCell(2).setCellValue(data.getMemberBankAcq());
//                row.createCell(3).setCellValue(data.getMerchantName());
//                row.createCell(4).setCellValue(data.getMid());
//                row.createCell(5).setCellValue(data.getTid());
//                row.createCell(6).setCellValue(data.getRrn());
//                row.createCell(7).setCellValue(data.getIssuer());
//                row.createCell(8).setCellValue(data.getAuthDate());
//                row.createCell(9).setCellValue(data.getAuthTime());
//                row.createCell(10).setCellValue(data.getAuthAmount().toString());
//                row.createCell(11).setCellValue(data.getTraceNo());
//                row.createCell(12).setCellValue(data.getMessageTypeId());
//                row.createCell(13).setCellValue(data.getAuthSaleType());
//                row.createCell(14).setCellValue(data.getAuthIntnRspnCd());
//                row.createCell(15).setCellValue(data.getReasonContents());
//                row.createCell(16).setCellValue(data.getInstallmentCount() != null ? data.getInstallmentCount() : 0);
//                row.createCell(17).setCellValue(data.getSwitchBrand());
//                row.createCell(18).setCellValue(data.getPosEntryModeDetail());
//                row.createCell(19).setCellValue(data.getCardTypeCode());
//                row.createCell(20).setCellValue(data.getOnusCode());
//                row.createCell(21).setCellValue(data.getEciValue());
//                row.createCell(22).setCellValue(data.getApprovalCode());
//                row.createCell(23).setCellValue(data.getPgName());
//                row.createCell(24).setCellValue(data.getPgType());
//                row.createCell(25).setCellValue(data.getIssuerMemberNo());
//                row.createCell(26).setCellValue(data.getBusinessType());
//                row.createCell(27).setCellValue(data.getChannel());
//                row.createCell(28).setCellValue(data.getIssuerCountry());
//                row.createCell(29).setCellValue(data.getParameterValues());
//                row.createCell(30).setCellValue(data.getActionType());
//                row.createCell(31).setCellValue(data.getFraudType());
//                row.createCell(32).setCellValue(data.getFraudPodType());
//                row.createCell(33).setCellValue(data.getFraudNote());
//                row.createCell(34).setCellValue(data.getRemindNote());
//                row.createCell(35).setCellValue(data.getRemindDate() != null ? data.getRemindDate().toString() : "");
//                row.createCell(36).setCellValue(data.getAssignedDateTime() != null ? data.getAssignedDateTime().toString() : "");
//                row.createCell(37).setCellValue(data.getConfirmedDateTime() != null ? data.getConfirmedDateTime().toString() : "");
//                row.createCell(38).setCellValue(data.getFdsTransactionId());
//                row.createCell(39).setCellValue(data.getRule() != null ? data.getRule().toString() : "");
//                row.createCell(40).setCellValue(data.getFdsTransactionsAudit() != null ? data.getFdsTransactionsAudit().toString() : "");
//                row.createCell(41).setCellValue(data.getConfirmedUserAccount() != null ? data.getConfirmedUserAccount().toString() : "");
//                row.createCell(42).setCellValue(data.getAssignedUserAccount() != null ? data.getAssignedUserAccount().toString() : "");
//            }
//
//            for (int i = 0; i < columns.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            workbook.write(out);
//            return out.toByteArray();
//        }
//    }
//
//    public byte[] generateCsvTransactionReport(
//            OffsetDateTime startDate,
//            OffsetDateTime endDate,
//            String mid,
//            String tid,
//            String assignedTo,
//            String actionType,
//            Boolean actionTypeIsNull,
//            Boolean actionTypeIsNotNull,
//            Pageable pageable
//    ) throws IOException {
//        Page<FdsTransactionsDto> reportData = read(
//                startDate,
//                endDate,
//                mid,
//                tid,
//                assignedTo,
//                actionType,
//                actionTypeIsNull,
//                actionTypeIsNotNull,
//                pageable
//        );
//
//        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
//             PrintWriter writer = new PrintWriter(out)) {
//
//            // Write CSV header
//            writer.println("Auth Seq No, Card No, Member Bank Acq, Merchant Name, MID, TID, RRN, Issuer, Auth Date, Auth Time, " +
//                    "Auth Amount, Trace No, Message Type Id, Auth Sale Type, Auth Intn Rspn Cd, Reason Contents, Installment Count, " +
//                    "Switch Brand, POS Entry Mode Detail, Card Type Code, Onus Code, ECI Value, Approval Code, PG Name, PG Type, " +
//                    "Issuer Member No, Business Type, Channel, Issuer Country, Parameter Values, Action Type, Fraud Type, Fraud Pod Type, " +
//                    "Fraud Note, Remind Note, Remind Date, Assigned Date Time, Confirmed Date Time, FDS Transaction ID, Rule, " +
//                    "FDS Transactions Audit, Confirmed User Account, Assigned User Account");
//
//            // Write data rows
//            for (FdsTransactionsDto data : reportData) {
//                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
//                        escapeSpecialCharacters(data.getAuthSeqNo()),
//                        escapeSpecialCharacters(data.getCardNo()),
//                        escapeSpecialCharacters(data.getMemberBankAcq()),
//                        escapeSpecialCharacters(data.getMerchantName()),
//                        escapeSpecialCharacters(data.getMid()),
//                        escapeSpecialCharacters(data.getTid()),
//                        escapeSpecialCharacters(data.getRrn()),
//                        escapeSpecialCharacters(data.getIssuer()),
//                        escapeSpecialCharacters(data.getAuthDate()),
//                        escapeSpecialCharacters(data.getAuthTime()),
//                        data.getAuthAmount() != null ? data.getAuthAmount().toString() : "",
//                        escapeSpecialCharacters(data.getTraceNo()),
//                        escapeSpecialCharacters(data.getMessageTypeId()),
//                        escapeSpecialCharacters(data.getAuthSaleType()),
//                        escapeSpecialCharacters(data.getAuthIntnRspnCd()),
//                        escapeSpecialCharacters(data.getReasonContents()),
//                        data.getInstallmentCount() != null ? data.getInstallmentCount() : 0,
//                        escapeSpecialCharacters(data.getSwitchBrand()),
//                        escapeSpecialCharacters(data.getPosEntryModeDetail()),
//                        escapeSpecialCharacters(data.getCardTypeCode()),
//                        escapeSpecialCharacters(data.getOnusCode()),
//                        escapeSpecialCharacters(data.getEciValue()),
//                        escapeSpecialCharacters(data.getApprovalCode()),
//                        escapeSpecialCharacters(data.getPgName()),
//                        escapeSpecialCharacters(data.getPgType()),
//                        escapeSpecialCharacters(data.getIssuerMemberNo()),
//                        escapeSpecialCharacters(data.getBusinessType()),
//                        escapeSpecialCharacters(data.getChannel()),
//                        escapeSpecialCharacters(data.getIssuerCountry()),
//                        escapeSpecialCharacters(data.getParameterValues()),
//                        escapeSpecialCharacters(data.getActionType()),
//                        escapeSpecialCharacters(data.getFraudType()),
//                        escapeSpecialCharacters(data.getFraudPodType()),
//                        escapeSpecialCharacters(data.getFraudNote()),
//                        escapeSpecialCharacters(data.getRemindNote()),
//                        data.getRemindDate() != null ? data.getRemindDate().toString() : "",
//                        data.getAssignedDateTime() != null ? data.getAssignedDateTime().toString() : "",
//                        data.getConfirmedDateTime() != null ? data.getConfirmedDateTime().toString() : "",
//                        escapeSpecialCharacters(data.getFdsTransactionId()),
//                        escapeSpecialCharacters(data.getRule() != null ? data.getRule().toString() : ""),
//                        escapeSpecialCharacters(data.getFdsTransactionsAudit() != null ? data.getFdsTransactionsAudit().toString() : ""),
//                        escapeSpecialCharacters(data.getConfirmedUserAccount() != null ? data.getConfirmedUserAccount().toString() : ""),
//                        escapeSpecialCharacters(data.getAssignedUserAccount() != null ? data.getAssignedUserAccount().toString() : "")
//                );
//            }
//
//            writer.flush();
//            return out.toByteArray();
//        }
//    }
}
