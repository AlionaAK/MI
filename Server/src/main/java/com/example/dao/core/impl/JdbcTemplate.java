package com.example.dao.core.impl;


import com.example.dao.core.*;
import com.example.dao.core.exception.JdbcTemplateException;
import com.example.dao.core.pool.connection.ConnectionWrapper;
import com.example.dao.core.pool.connection.ProxyConnection;
import com.example.dao.core.pool.impl.DatabaseConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class contain realization of {@code JdbcOperations}
 * <p>
 * This class allows you to encapsulate the entire logic of working with the database, completely
 * remove duplication of code and make it easily extensible and mutable.
 * </p>
 * This is achieved through delegation and support interfaces, which take on a certain part of the work.
 * Due to the fact that the methods of the most database queries do not know anything about the type of
 * request about the parameters in it and the action itself, we can achieve polymorphism,
 * because now the same method will serve absolutely any request.
 * It also happened for the update method, because it also knows nothing about the parameters and actions.
 * <p>
 * It is especially worth paying attention to the class {@code PreparedStatementSetter}.
 * There, parameters are set in the request,which allows you to send further a completely ready
 * request with the set parameters
 * </p>
 *
 * @see PreparedStatementCallBack
 * @see PreparedStatementCreator
 * @see PreparedStatementSetter
 * @see ResultSetExtractor
 * @see JdbcUtils
 * @see ArgumentPreparedStatementSetter
 * @see RowMapperResultSetExtractor
 */
public class JdbcTemplate implements JdbcOperations {

    /**
     * @param sql       {@code String} sql query to bd
     * @param rowMapper used for creat correct object with parameters from bd
     * @param argc      parameters for sql
     * @param <T>       Any entity from business logic
     * @return {@code <T>} object
     * @throws JdbcTemplateException throws if something going wrong
     */
    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... argc) throws JdbcTemplateException {
        List<T> results = this.query(sql, rowMapper, argc);
        return getSingleResult(results);
    }

    private <T> T getSingleResult(List<T> results) {
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * Used to check if there is an object in the database
     *
     * @param sql  sql query for bd
     * @param argc parameters for query
     * @return {@code boolean}
     * @throws JdbcTemplateException
     */
    @Override
    public boolean queryForObject(String sql, Object... argc) throws JdbcTemplateException {
        return this.query(
                new SimplePreparedStatementCreator(sql),
                this.newArgPreparedStatementSetter(argc));
    }


    /**
     * Used for all update operations
     *
     * @param sql  sql query for bd
     * @param argc parameters for query
     * @return {@code int } count of updated columns
     * @throws JdbcTemplateException
     */
    @Override
    public int update(String sql, Object... argc) throws JdbcTemplateException {
        return this.update(new SimplePreparedStatementCreator(sql),
                this.newArgPreparedStatementSetter(argc));
    }


    /**
     * Used for get List of objects from bd.
     * Before delegate method wrap sql in PreparedStatementCreator;
     * Creating RowMapperExtractor to row mapper;
     * Creating preparedStatementSetter for argc;
     *
     * @param sql       sql query for bd
     * @param rowMapper @{code RowMapper} for correct creating object
     * @param argc      parameters for query
     * @param <T>       any entity from business logic
     * @return {@code List<T>}
     * @throws JdbcTemplateException
     */
    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... argc) throws JdbcTemplateException {
        return (List<T>) (this.query(
                new SimplePreparedStatementCreator(sql),
                new RowMapperResultSetExtractor<>(rowMapper),
                this.newArgPreparedStatementSetter(argc)));
    }

    /**
     * Creating PreparedStatementSetter for query and
     * delegate work to next stage.
     */
    private <T> List<T> query(String sql, Object[] argc, ResultSetExtractor<T> rse) throws JdbcTemplateException {
        return (List<T>) this.query(sql, this.newArgPreparedStatementSetter(argc), rse);
    }

    /**
     * Creating PreparedStatementCreator for sql and
     * delegate work to next stage
     */
    private <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws JdbcTemplateException {
        return this.query(new SimplePreparedStatementCreator(sql), pss, rse);
    }


    /**
     * Here is anonymous class with implementing interface PreparedStatementCallBack
     * with realization of method then do some action in statement.
     * After this method delegate to method {@code execute}
     * with ref for this class, and execute will call this overriding method to do work.
     */
    private <T> T query(PreparedStatementCreator psc, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws JdbcTemplateException {
        return this.execute(psc, new PreparedStatementCallBack<T>() {
            @Override
            public T doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ResultSet set = null;
                T result;
                try {
                    if (pss != null) {
                        pss.setValues(ps);
                    }

                    set = ps.   executeQuery();
                    result = rse.extractData(set);
                } finally {
                    JdbcUtils.closeResultSet(set);
                }

                return result;
            }

        });
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws JdbcTemplateException {
        return (List<T>) (this.query(
                new SimplePreparedStatementCreator(sql),
                new RowMapperResultSetExtractor<>(rowMapper)));
    }

    private <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws JdbcTemplateException {
        return this.query(psc, (PreparedStatementSetter) null, rse);
    }


    /**
     * This method contain inner class with implementation of PreparedStatementCallBack.
     * This class realize method of action in statement in wich creating
     * resultSet and calling resultSetExtractor.
     * After method delegate to {@code execute} with
     * PreparedStatementCreator and object of this inner class.
     */
    private <T> List<T> query(PreparedStatementCreator psc,
                              ResultSetExtractor<T> resultSetExtractor,
                              PreparedStatementSetter pss)
            throws JdbcTemplateException {

        class QueryStatementCallBack implements PreparedStatementCallBack<T> {
            QueryStatementCallBack() {
            }

            @Override
            public T doInPreparedStatement(PreparedStatement ps) throws SQLException {

                if (pss != null) {
                    pss.setValues(ps);
                }

                ResultSet set = null;

                T var;
                try {
                    set = ps.executeQuery();

                    var = resultSetExtractor.extractData(set);
                } finally {
                    JdbcUtils.closeResultSet(set);
                }
                return var;
            }
        }

        return (List<T>) this.execute(psc, new QueryStatementCallBack());
    }

    /**
     * Action method that do action in statement and return action result.
     * He dont know anything about sql, about params in query about type and type of action.
     * He know only about prepared statement that he can create with interface PreparedStatementCreator
     * and about action than he can call from PreparedStatementCallBack
     * give to him created preparedStatement in wich ready sql query with setted params.
     * <p>
     * After work method in finally block cancel all outside resources
     * with {@code JdbcUtils}.
     * </p>
     */
    private <T> T execute(PreparedStatementCreator psc, PreparedStatementCallBack<T> action) throws JdbcTemplateException {

        ProxyConnection proxyConnection = ConnectionCreator.getProxyConnection();
        ConnectionWrapper connectionWrapper = proxyConnection.getConnectionWrapper();
        PreparedStatement ps = null;

        try {
            ps = psc.createPreparedStatement(connectionWrapper);

            T result = action.doInPreparedStatement(ps);

            return result;
        } catch (SQLException e) {
            throw new JdbcTemplateException(e);
        } finally {
            JdbcUtils.closePreparedStatement(ps);
            JdbcUtils.closeConnection(proxyConnection);
        }
    }


    private boolean query(PreparedStatementCreator psc, PreparedStatementSetter pss)
            throws JdbcTemplateException {

        return this.execute(psc, new PreparedStatementCallBack<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                if (pss != null) {
                    pss.setValues(ps);
                }

                ResultSet set = null;
                boolean result;

                try {
                    set = ps.executeQuery();
                    result = set.first();
                } finally {
                    JdbcUtils.closeResultSet(set);
                }
                return result;
            }
        });
    }

    /**
     * Method for make update operations.
     * Received statementCreator, statementSettor.
     *
     * @param psc {@code PreparedStatementCreator}
     * @param pss {@code PreparedStatementSetter}
     * @return {@code int} count of updated columns
     * @throws JdbcTemplateException
     */
    private int update(PreparedStatementCreator psc,
                       PreparedStatementSetter pss) throws JdbcTemplateException {
        return this.execute(psc, (preparedStatement) -> {
            if (pss != null) {
                pss.setValues(preparedStatement);
            }

            int rows = preparedStatement.executeUpdate();

            return rows;
        });
    }

    /**
     * Nested class that contain logic of creator statement.
     * Implementing {@code PreparedStatementCreator}
     */
    private static class SimplePreparedStatementCreator implements PreparedStatementCreator {

        private final String sql;

        SimplePreparedStatementCreator(String sql) {
            this.sql = sql;
        }

        @Override
        public PreparedStatement createPreparedStatement(ConnectionWrapper connectionWrapper) throws SQLException {
            return connectionWrapper.prepareStatement(this.sql);
        }

        public String getSql() {
            return sql;
        }
    }

    /**
     * Nested class that contain logic of return {@code ProxyConnection}
     */
    private static class ConnectionCreator {

        private static final DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

        private static ProxyConnection getProxyConnection() {
            return pool.getConnection();
        }

        private ConnectionCreator() {
        }
    }


    /**
     * Method used to creat and returns object of statement setter
     *
     * @param argc
     * @return {@code ArgumentPreparedStatementSetter}
     */
    private PreparedStatementSetter newArgPreparedStatementSetter(Object[] argc) {
        return new ArgumentPreparedStatementSetter(argc);
    }


}
