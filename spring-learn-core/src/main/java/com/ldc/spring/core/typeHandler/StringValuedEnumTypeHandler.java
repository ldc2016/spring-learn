package com.ldc.spring.core.typeHandler;

import com.ldc.spring.core.enums.StringValuedEnum;
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
public class StringValuedEnumTypeHandler <E extends Enum<?> & StringValuedEnum> extends BaseTypeHandler<StringValuedEnum> {

    private Class<E> type;

    private Map<String, E> map = new HashMap<String, E>();

    public StringValuedEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (enums != null) {
            for (E e : enums) {
                map.put(e.getValue(), e);
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, StringValuedEnum valuedEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, valuedEnum.getValue());
    }

    @Override
    public StringValuedEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String i = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public StringValuedEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String i = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public StringValuedEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String i = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    private StringValuedEnum getValuedEnum(String value) {
        try {
            return map.get(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Cannot convert " + value + " to " + type.getSimpleName() + " by value.", ex);
        }
    }
}
