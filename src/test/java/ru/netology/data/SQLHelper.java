package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static QueryRunner run = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnections() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void cleanBase() {  //    public void cleanDataBase() {
        var connection = getConnections();
        run.execute(connection, "DELETE FROM auth_codes");
        run.execute(connection, "DELETE FROM card_transactions");
        run.execute(connection, "DELETE FROM cards");
        run.execute(connection, "DELETE FROM users");
    }

    public static DataHelper.VerificationCode getVerificationCode() { //    public static String getCorrectVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC";
        try (var connections = getConnections()) {
            var result = run.query(connections, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(result);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
