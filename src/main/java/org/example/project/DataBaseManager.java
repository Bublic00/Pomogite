package org.example.project;

import javafx.scene.paint.Color;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DataBaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/plans"; // Проверьте имя вашей базы данных
    private static final String USER = "root"; // Ваше имя пользователя MySQL
    private static final String PASSWORD = "1633"; // Ваш пароль MySQL

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Создаем таблицу, если она не существует
            String sql = "CREATE TABLE IF NOT EXISTS plans " +
                    "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "date DATE NOT NULL, " +
                    "time TIME NOT NULL, " +
                    "text VARCHAR(255) NOT NULL, " +
                    "category VARCHAR(50) NOT NULL)";
            stmt.execute(sql);
            System.out.println("Таблица 'plans' успешно инициализирована.");
        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void savePlans(Map<LocalDate, List<Plan>> plans) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO plans (date, time, text, category) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Map.Entry<LocalDate, List<Plan>> entry : plans.entrySet()) {
                    for (Plan plan : entry.getValue()) {
                        pstmt.setDate(1, Date.valueOf(entry.getKey()));
                        pstmt.setTime(2, Time.valueOf(plan.getTime()));
                        pstmt.setString(3, plan.getText());
                        pstmt.setString(4, plan.getCategory().getName());
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
                System.out.println("Планы успешно сохранены в базе данных.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении планов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Map<LocalDate, List<Plan>> loadPlans() {
        Map<LocalDate, List<Plan>> plans = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM plans")) {
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime time = rs.getTime("time").toLocalTime();
                String text = rs.getString("text");
                String categoryName = rs.getString("category");

                // Отладочное сообщение
                System.out.println("Загружен план: " + text + " на " + date + " в " + time + " с категорией " + categoryName);

                Category category = new Category(categoryName, Color.GRAY); // Установите цвет по умолчанию
                Plan plan = new Plan(time, text, category);
                plans.computeIfAbsent(date, k -> new ArrayList<>()).add(plan);
            }
            System.out.println("Планы успешно загружены из базы данных.");
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке планов: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }
    public static void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Удалить все записи из таблицы
            stmt.executeUpdate("DELETE FROM plans");

            // Или использовать TRUNCATE
            // stmt.executeUpdate("TRUNCATE TABLE plans");

            System.out.println("База данных очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
