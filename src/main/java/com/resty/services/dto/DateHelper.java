package com.resty.services.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by damianhagge on 5/16/17.
 */
public class DateHelper {

    public static LocalDateTime getLocalDateTime(ResultSet rs, String col) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(col);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    public static LocalDate getLocalDate(ResultSet rs, String col) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(col);
        return timestamp != null ? timestamp.toLocalDateTime().toLocalDate() : null;
    }
}
