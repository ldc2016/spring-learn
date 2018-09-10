package com.ldc.spring.core.aspect;

import com.ldc.spring.core.model.BizSerialNo;

/**
 * created by liudacheng on 2018/9/10.
 */
public class SerialNoContainer {
    private BizSerialNo bizSerialNo;

    public BizSerialNo getBizSerialNumber() {
        return bizSerialNo;
    }

    public void setBizSerialNumber(BizSerialNo bizSerialNo) {
        this.bizSerialNo = bizSerialNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SerialNoContainer container = (SerialNoContainer) o;

        if (bizSerialNo != null ? !bizSerialNo.equals(container.bizSerialNo) : container.bizSerialNo != null) {
            return false;
        }

        return bizSerialNo != null ? bizSerialNo.equals(container.bizSerialNo) : container.bizSerialNo == null;
    }

    @Override
    public int hashCode() {
        int result = bizSerialNo != null ? bizSerialNo.hashCode() : 0;
        result = 31 * result + (bizSerialNo != null ? bizSerialNo.hashCode() : 0);
        return result;
    }
}
