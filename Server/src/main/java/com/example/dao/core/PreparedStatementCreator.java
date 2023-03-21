package com.example.dao.core;




import com.example.dao.core.pool.connection.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCreator {

    PreparedStatement createPreparedStatement(ConnectionWrapper connectionWrapper) throws SQLException;

}
