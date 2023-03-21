package com.example.dao.core.impl;


import com.example.dao.core.PreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class using for set arguments in statement.
 *
 * @see PreparedStatementSetter
 */
public class ArgumentPreparedStatementSetter implements PreparedStatementSetter {

    private final Object[] argc;


    public ArgumentPreparedStatementSetter(Object[] argc) {
        this.argc = argc;
    }

    /**
     * Method using cycle for and run by object array
     * and set arguments in statement.
     *
     * @param ps statement to work
     */
    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        if (this.argc != null) {
            for (int i = 0; i < this.argc.length; i++) {
                Object arg = this.argc[i];
                this.doSetValue(ps, i + 1, arg);
            }
        }
    }

    private void doSetValue(PreparedStatement ps, int parameterPosition, Object argValue) throws SQLException {
        set(ps, parameterPosition, argValue);
    }


    private void set(PreparedStatement ps, int parameterPosition, Object argValue) throws SQLException {
        if (argValue instanceof String) {
            ps.setString(parameterPosition, (String) argValue);
        } else if (argValue instanceof Integer) {
            ps.setInt(parameterPosition, (Integer) argValue);
        } else if (argValue instanceof Double) {
            ps.setDouble(parameterPosition, (Double) argValue);
        } else if (argValue instanceof Boolean) {
            ps.setBoolean(parameterPosition, (Boolean) argValue);
        } else if (argValue instanceof Timestamp) {
            ps.setTimestamp(parameterPosition, (Timestamp) argValue);
        } else if (argValue instanceof Long) {
            ps.setLong(parameterPosition, (Long) argValue);
        } else if (argValue instanceof Date) {
            ps.setDate(parameterPosition, (Date) argValue);
        } else if (argValue == null) {
            final int sqlTypeToUse = getColumnSqlDataType(ps, parameterPosition);
            ps.setNull(parameterPosition, sqlTypeToUse);
        }

    }

    /**
     * if value null ,we cant check it type, and cant set it in statement.
     * According with this using this function, that using statement metadata and
     * set null in parameter with correct type.
     */
    private int getColumnSqlDataType(PreparedStatement ps, int parameterPosition) throws SQLException {
        return ps.getParameterMetaData().getParameterType(parameterPosition);
    }

}
