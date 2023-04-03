package io.swagger.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IbanTransactionRequestDTO {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String IbanTo;
    private String IbanFrom;
    private BigDecimal amountEquals;
    private BigDecimal amountBigger;
    private BigDecimal amountSmaller;


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

    public String getIbanTo() {
        return IbanTo;
    }

    public void setIbanTo(String ibanTo) {
        IbanTo = ibanTo;
    }

    public String getIbanFrom() {
        return IbanFrom;
    }

    public void setIbanFrom(String ibanFrom) {
        IbanFrom = ibanFrom;
    }

    public BigDecimal getAmountEquals() {
        return amountEquals;
    }

    public void setAmountEquals(BigDecimal amountEquals) {
        this.amountEquals = amountEquals;
    }

    public BigDecimal getAmountBigger() {
        return amountBigger;
    }

    public void setAmountBigger(BigDecimal amountBigger) {
        this.amountBigger = amountBigger;
    }

    public BigDecimal getAmountSmaller() {
        return amountSmaller;
    }

    public void setAmountSmaller(BigDecimal amountSmaller) {
        this.amountSmaller = amountSmaller;
    }


}
