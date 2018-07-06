package com.axon.complaintsstats.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

public class LoanInfo extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String bankLoanId;

    public String getBankLoanId() {
        return bankLoanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public BigDecimal getSax() {
        return sax;
    }

    public void setBankLoanId(String bankLoanId) {
        this.bankLoanId = bankLoanId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setSax(BigDecimal sax) {
        this.sax = sax;
    }

    @ExcelProperty(index = 1)
    private Long customerId;
//
//    @ExcelProperty(index = 2,format = "yyyy/MM/dd")
//    private Date loanDate;
//
//    @ExcelProperty(index = 3)
//    private BigDecimal quota;
//
//    @ExcelProperty(index = 4)
//    private String bankInterestRate;
//
//    @ExcelProperty(index = 5)
//    private Integer loanTerm;
//
//    @ExcelProperty(index = 6,format = "yyyy/MM/dd")
//    private Date loanEndDate;

//    @ExcelProperty(index = 7)
//    private BigDecimal interestPerMonth;

    @ExcelProperty(value = {"sax"})
    private BigDecimal sax;
}
