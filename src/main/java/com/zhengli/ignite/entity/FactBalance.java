package com.zhengli.ignite.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by sunzhengli on 2020/7/12
 */
public class FactBalance implements Serializable {
    private long identifier;
    @QuerySqlField(index = true)
    private int calendarId;
    @QuerySqlField
    private int pfId;
    private int localCurrencyId;
    private int productProcessorId;
    @QuerySqlField
    private BigDecimal tdLocalBalance;

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public int getPfId() {
        return pfId;
    }

    public void setPfId(int pfId) {
        this.pfId = pfId;
    }

    public int getLocalCurrencyId() {
        return localCurrencyId;
    }

    public void setLocalCurrencyId(int localCurrencyId) {
        this.localCurrencyId = localCurrencyId;
    }

    public int getProductProcessorId() {
        return productProcessorId;
    }

    public void setProductProcessorId(int productProcessorId) {
        this.productProcessorId = productProcessorId;
    }

    public BigDecimal getTdLocalBalance() {
        return tdLocalBalance;
    }

    public void setTdLocalBalance(BigDecimal tdLocalBalance) {
        this.tdLocalBalance = tdLocalBalance;
    }

    public BigDecimal getSdLocalBalance() {
        return sdLocalBalance;
    }

    public void setSdLocalBalance(BigDecimal sdLocalBalance) {
        this.sdLocalBalance = sdLocalBalance;
    }

    public BigDecimal getEdLocalBalance() {
        return edLocalBalance;
    }

    public void setEdLocalBalance(BigDecimal edLocalBalance) {
        this.edLocalBalance = edLocalBalance;
    }

    private BigDecimal sdLocalBalance;
    private BigDecimal edLocalBalance;


}
