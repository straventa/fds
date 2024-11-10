package com.yokke.fdsservice.alert_fds_parameter;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistoryDto;
import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class AlertFdsParameterService {
    private final AlertFdsParameterRepository alertFdsParameterRepository;
    private final AlertFdsParameterMapper alertFdsParameterMapper;
    private final AlertFdsParameterHistoryService alertFdsParameterHistoryService;

    public Page<AlertFdsParameterDto> read(Pageable pageable) {
        return alertFdsParameterRepository.findAll(pageable)
                .map(
                        (alertFdsParameter -> {
                            List<AlertFdsParameterHistoryDto> alertFdsParameterHistoryDtos = alertFdsParameterHistoryService.readByAlertFdsParameterId(alertFdsParameter.getId());
                            return alertFdsParameterMapper.mapToDto(alertFdsParameter, alertFdsParameterHistoryDtos);
                        })
                );
    }

    public Page<AlertFdsParameterDto> read(
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            String mid,
            String tid,
            String assignedTo,
            String actionType,
            Boolean isMarked,
            Pageable pageable
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);
        if (Objects.equals(assignedTo, "all")) {
            assignedTo = null;
        }
        if (Objects.equals(actionType, "all")) {
            actionType = null;
        }
        return alertFdsParameterRepository.findAll(
                        formattedStartDate,
                        formattedEndDate,
                        mid,
                        tid,
                        assignedTo,
                        actionType,
                        isMarked,
                        pageable
                )
                .map(alertFdsParameterMapper::mapToDto);
    }

    public AlertFdsParameterDto read(String uniqueId) {
        AlertFdsParameterUniqueIdParamDto alertFdsParameterUniqueIdParamDto = alertFdsParameterMapper.getUniqueId(uniqueId);
        return alertFdsParameterRepository.findByAuthSeqNoAndAuthDateAndCardNo(
                        alertFdsParameterUniqueIdParamDto.getAuthSeqNo(),
                        alertFdsParameterUniqueIdParamDto.getAuthDate(),
                        alertFdsParameterUniqueIdParamDto.getCardNo()
                ).map(alertFdsParameterMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("Alert FDS Parameter not found"));
    }

    public AlertFdsParameterDto next(String uniqueId) {
        AlertFdsParameterUniqueIdParamDto alertFdsParameterUniqueIdParamDto = alertFdsParameterMapper.getUniqueId(uniqueId);

        return alertFdsParameterRepository.findOne(
                        "admin",
                        alertFdsParameterUniqueIdParamDto.getAuthSeqNo()
//                        alertFdsParameterUniqueIdParamDto.getAuthDate(),
//                        alertFdsParameterUniqueIdParamDto.getCardNo()
                ).map(alertFdsParameterMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("Alert FDS Parameter not found"));
    }

    public AlertFdsParameterDto update(String uniqueId, AlertFdsParameterDto alertFdsParameterDto) {
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        AlertFdsParameterUniqueIdParamDto alertFdsParameterUniqueIdParamDto = alertFdsParameterMapper.getUniqueId(uniqueId);
        AlertFdsParameter alertFdsParameter = alertFdsParameterRepository.findByAuthSeqNoAndAuthDateAndCardNo(
                alertFdsParameterUniqueIdParamDto.getAuthSeqNo(),
                alertFdsParameterUniqueIdParamDto.getAuthDate(),
                alertFdsParameterUniqueIdParamDto.getCardNo()
        ).orElseThrow(() -> new NotFoundException("Alert FDS Parameter not found"));
        AlertFdsParameter updated = alertFdsParameterRepository.save(
                alertFdsParameterMapper.update(alertFdsParameterDto, alertFdsParameter)
        );
        String notes;
        if (alertFdsParameterDto.getActionType() == "REMIND") {
            notes = alertFdsParameterDto.getRemindNote();
        } else {
            notes = alertFdsParameterDto.getFraudNote();
        }
        if (alertFdsParameterDto.getNotes() == null) {
            alertFdsParameterDto.setNotes(notes);
        }
        AlertFdsParameterHistoryDto alertFdsParameterHistoryDto = alertFdsParameterHistoryService.create(
                alertFdsParameter.getId(),
                alertFdsParameterDto.activity,
                alertFdsParameterDto.getNotes()
        );
        return alertFdsParameterMapper.mapToDto(
                alertFdsParameterRepository.save(
                        alertFdsParameterMapper.update(alertFdsParameterDto, alertFdsParameter)
                )
        );
    }

    public List<AlertFdsParameterAnalystDto> statisticsByAnalyst(

            OffsetDateTime startDate,
            OffsetDateTime endDate
    ) {
        List<AlertFdsParameterAnalystDto> res = new ArrayList<>();
        String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDateFormatted = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<List<Object>> result = alertFdsParameterRepository.countByAnalyst(
                startDateFormatted,
                endDateFormatted
        );
        for (List<Object> objects : result) {
            AlertFdsParameterAnalystDto alertFdsParameterAnalystDto = new AlertFdsParameterAnalystDto();
            alertFdsParameterAnalystDto.setAnalyst((objects.get(0) == null ? "0" : objects.get(0)).toString());
            alertFdsParameterAnalystDto.setIncoming((objects.get(1) == null ? "0" : objects.get(1)).toString());
            alertFdsParameterAnalystDto.setGenuine((objects.get(2) == null ? "0" : objects.get(2)).toString());
            alertFdsParameterAnalystDto.setWatchlist((objects.get(3) == null ? "0" : objects.get(3)).toString());
            alertFdsParameterAnalystDto.setFraud((objects.get(4) == null ? "0" : objects.get(4)).toString());
            alertFdsParameterAnalystDto.setInvestigation((objects.get(5) == null ? "0" : objects.get(5)).toString());
            alertFdsParameterAnalystDto.setPending((objects.get(6) == null ? "0" : objects.get(6)).toString());
            res.add(alertFdsParameterAnalystDto);
        }
        return res;
    }


    public byte[] generateExcelReport(List<AlertFdsParameterAnalystDto> reportData) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FDS Transactions Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Analyst", "Incoming", "Genuine", "Watchlist", "Fraud", "Investigation", "Pending"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (AlertFdsParameterAnalystDto data : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getAnalyst());
                row.createCell(1).setCellValue(data.getIncoming());
                row.createCell(2).setCellValue(data.getGenuine());
                row.createCell(3).setCellValue(data.getWatchlist());
                row.createCell(4).setCellValue(data.getFraud());
                row.createCell(5).setCellValue(data.getInvestigation());
                row.createCell(6).setCellValue(data.getPending());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

//    public void  delete(String uniqueId) {
//        AlertFdsParameterUniqueIdParamDto alertFdsParameterUniqueIdParamDto = alertFdsParameterMapper.getUniqueId(uniqueId);
//        AlertFdsParameter alertFdsParameter = alertFdsParameterRepository.findByAuthSeqNoAndAuthDateAndCardNo(
//                alertFdsParameterUniqueIdParamDto.getAuthSeqNo(),
//                alertFdsParameterUniqueIdParamDto.getAuthDate(),
//                alertFdsParameterUniqueIdParamDto.getAuthDate()
//        ).orElseThrow(() -> new RuntimeException("Alert FDS Parameter not found"));
//        alertFdsParameterRepository.delete(alertFdsParameter);
//    }
}
