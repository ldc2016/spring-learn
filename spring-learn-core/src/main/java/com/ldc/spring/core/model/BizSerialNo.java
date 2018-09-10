package com.ldc.spring.core.model;

import com.ldc.spring.core.aspect.SerialNoContainerHolder;

/**
 * created by liudacheng on 2018/9/10.
 */
public class BizSerialNo implements SerialNo{

    private String bizSerialNo;

    public BizSerialNo(String number) {
        this.bizSerialNo = number;
    }

    public static BizSerialNo getCurrentNumber() {
        return SerialNoContainerHolder.get() != null ? SerialNoContainerHolder.get().getBizSerialNumber() : null;
    }

    @Override
    public String getNumber() {
        return bizSerialNo;
    }

    @Override
    public String toString() {
        return this.bizSerialNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BizSerialNo that = (BizSerialNo) o;

        return bizSerialNo != null ? bizSerialNo.equals(that.bizSerialNo) : that.bizSerialNo == null;
    }

    @Override
    public int hashCode() {
        return bizSerialNo != null ? bizSerialNo.hashCode() : 0;
    }

}
