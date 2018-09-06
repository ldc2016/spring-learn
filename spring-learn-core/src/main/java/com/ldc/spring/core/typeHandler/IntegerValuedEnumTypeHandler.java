package com.ldc.spring.core.typeHandler;

import com.ldc.spring.core.enums.IntegerValuedEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * created by liudacheng on 2018/9/6.
 */
public class IntegerValuedEnumTypeHandler <E extends Enum<?> & IntegerValuedEnum> extends BaseTypeHandler<IntegerValuedEnum> {

    private Class<E> type;

    private Map<Integer, E> map = new HashMap();

    public IntegerValuedEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (enums != null) {
            for (E e : enums) {
                IntegerValuedEnum valuedEnum = e;
                map.put(valuedEnum.getValue(), e);
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntegerValuedEnum valuedEnum, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, valuedEnum.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    private E getValuedEnum(int value) {
        try {
            return map.get(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Cannot convert " + value + " to " + type.getSimpleName() + " by value.", ex);
        }
    }


}
