package com.resty.configuration;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Configuration for JDBI and related persistence.
 */
@Configuration
public class PersistenceConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean
    public DBI dbiBean() {
        DBI dbi = new DBI(dataSource);
        dbi.registerArgumentFactory(new DateTimeArgumentFactory());
        dbi.registerArgumentFactory(new LocalDateArgumentFactory());
        dbi.registerArgumentFactory(new DurationArgumentFactory());
        dbi.registerColumnMapper(new LocalDateTimeMapper());
        return dbi;
    }

    private static Calendar getUtcCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    /**
     * DBI argument factory for converting LocalDateTime to sql timestamp
     */
    public static class DateTimeArgumentFactory implements ArgumentFactory<LocalDateTime> {

        @Override
        public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
            return value != null && LocalDateTime.class.isAssignableFrom(value.getClass());
        }

        @Override
        public Argument build(Class<?> expectedType, final LocalDateTime value, StatementContext ctx) {
            return new Argument() {
                @Override
                public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                    long millis = value.toInstant(ZoneOffset.UTC).toEpochMilli();
                    statement.setTimestamp(position, new Timestamp(millis), getUtcCalendar());
                }
            };
        }
    }

    /**
     * DBI argument factory for converting LocalDate to sql timestamp
     */
    public static class LocalDateArgumentFactory implements ArgumentFactory<LocalDate> {
        @Override
        public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
            return value != null && LocalDate.class.isAssignableFrom(value.getClass());
        }

        @Override
        public Argument build(Class<?> expectedType, final LocalDate value, StatementContext ctx) {
            return new Argument() {
                @Override
                public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                    statement.setDate(position, Date.valueOf(value));
                }
            };
        }
    }

    /**
     * DBI argument factory for converting Duration to sql 'interval' strings.
     */
    public static class DurationArgumentFactory implements ArgumentFactory<Duration> {
        @Override
        public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
            return value != null && Duration.class.isAssignableFrom(value.getClass());
        }

        @Override
        public Argument build(Class<?> expectedType, final Duration value, StatementContext ctx) {
            return new Argument() {
                @Override
                public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                    statement.setString(position, value.getSeconds() + " second");
                }
            };
        }
    }

    /**
     * A {@link ResultColumnMapper} to map {@link LocalDateTime} objects.
     */
    public static class LocalDateTimeMapper implements ResultColumnMapper<LocalDateTime> {

        @Override
        public LocalDateTime mapColumn(ResultSet rs, int columnNumber, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnNumber, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return timestamp.toLocalDateTime();
        }

        @Override
        public LocalDateTime mapColumn(ResultSet rs, String columnLabel, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnLabel, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return timestamp.toLocalDateTime();
        }
    }

}


