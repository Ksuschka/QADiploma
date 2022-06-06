package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DataBaseHelper {

    static String url = System.getProperty("db.url");
    static String user = System.getProperty("db.user");
    static String password = System.getProperty("db.password");


    private DataBaseHelper() {
    }

    public static String getStatusPaymentByCard() {
        val statusSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (
                val conn = DriverManager.getConnection(url, user, password);
                val statusStmt = conn.createStatement();
        ) {
            try (val rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    val status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getStatusBuyInCredit() {
        val statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";

        try (
                val conn = DriverManager.getConnection(url, user, password);
                val statusStmt = conn.createStatement();
        ) {
            try (val rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    val status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void clearTables() {
        QueryRunner runner = new QueryRunner();
        val deleteOrderEntity = "DELETE FROM order_entity;";
        val deletePaymentEntity = "DELETE FROM payment_entity;";
        val deleteCreditRequestEntity = "DELETE FROM credit_request_entity;";
        try (
                val conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, deleteOrderEntity);
            runner.update(conn, deletePaymentEntity);
            runner.update(conn, deleteCreditRequestEntity);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
