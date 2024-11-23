package com.revcom;

import java.sql.*;
import java.time.Instant;
import java.util.Date;

public class CricketScorerDAO {

    Connection conn = null;


    public CricketScorerDAO() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();


        } catch (Exception ex) {
            // handle the error
        }
    }

    /**
     *
     * @param audit
     * @throws SQLException
     */
    public void createAuditRow(Audit audit) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet rs = null;
        try {
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:mysql://cricketscorer-mysql.cpoays6q42ir.eu-west-2.rds.amazonaws.com/scorer?" +
                        "user=admin&password=FL2ZCfyNnkMhu3P9std3");
            }

            myStmt = conn.prepareStatement("INSERT INTO audit (timestamp,value) VALUES (?,?)");

            myStmt.setTimestamp(1,new Timestamp(audit.date.toEpochMilli()));
            myStmt.setString(2,audit.value);

            myStmt.execute();

        } catch (SQLException e) {
            throw e;
        }



    }

    /**
     *
     * @param date
     * @param value
     */
    public record Audit(Instant date, String value){}

}
