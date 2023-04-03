package io.swagger.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class TransactionRequestDTO {
    private String ibanFrom;
    private String ibanTo;
    @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
    private LocalDateTime dateFrom;
    @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
    private LocalDateTime dateTo;
    private Double amount;
    private Long operator;

    public String getIbanFrom() {
        return ibanFrom;
    }

    public void setIbanFrom(String ibanFrom) {
        this.ibanFrom = ibanFrom;
    }

    public String getIbanTo() {
        return ibanTo;
    }

    public void setIbanTo(String ibanTo) {
        this.ibanTo = ibanTo;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}
