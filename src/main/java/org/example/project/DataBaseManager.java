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
            String sql = "CREATE TABLE IF NOT EXISTS plans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "date DATE NOT NULL, " +
                    "time TIME NOT NULL, " +
                    "text VARCHAR(255) NOT NULL, " +
                    "category VARCHAR(50) NOT NULL, " +
                    "Color VARCHAR(7) NOT NULL " +
                    "UNIQUE (date, time));"; // Убедитесь, что здесь нет лишних символов
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
        }
    }

    public static void savePlans(Map<LocalDate, List<Plan>> plans) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String checkSql = "SELECT COUNT(*) FROM plans WHERE date = ? AND time = ? AND text = ? AND category = ?";
            String insertSql = "INSERT INTO plans (date, time, text, category, Color) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

                for (Map.Entry<LocalDate, List<Plan>> entry : plans.entrySet()) {
                    for (Plan plan : entry.getValue()) {
                        // Проверяем, существует ли план с такими же параметрами
                        checkStmt.setDate(1, Date.valueOf(entry.getKey()));
                        checkStmt.setTime(2, Time.valueOf(plan.getTime()));
                        checkStmt.setString(3, plan.getText());
                        checkStmt.setString(4, plan.getCategory().getName());

                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            System.out.println("План уже существует: " + plan);
                            continue;
                        }
                        // Если план не существует, добавляем его в базу данных
                        insertStmt.setDate(1, Date.valueOf(entry.getKey()));
                        insertStmt.setTime(2, Time.valueOf(plan.getTime()));
                        insertStmt.setString(3, plan.getText());
                        insertStmt.setString(4, plan.getCategory().getName());
                        insertStmt.setString(5, toHexString(plan.getCategory().getColor()));
                        insertStmt.addBatch();
                    }
                }
                insertStmt.executeBatch();
                System.out.println("Планы успешно сохранены в базе данных.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении планов: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String toHexString(Color color) {
        return String.format("#%02x%02x%02x",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    public static Map<LocalDate, List<Plan>>  loadPlans() {
        Map<LocalDate, List<Plan>> plans = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM plans")) {
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime time = rs.getTime("time").toLocalTime();
                String text = rs.getString("text");
                String categoryName = rs.getString("category");
                String colorHex=rs.getString("Color");
                Color color= Color.web(colorHex);

                // Отладочное сообщение
                System.out.println("Загружен план: " + text + " на " + date + " в " + time + " с категорией " + categoryName);

                Category category = new Category(categoryName, color); // Установите цвет по умолчанию
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
     public static List<Category> loadCategories(){
        List<Category> categories= new ArrayList<>();
        String sql ="SELECT category,color FROM plans";
        try(Connection conn= DriverManager.getConnection(URL,USER,PASSWORD);
            Statement stmt=conn.createStatement();
            ResultSet rs= stmt.executeQuery(sql)){
            while (rs.next()){
                String name= rs.getString("category");
                String colorHex=rs.getString("Color");
                Color color=Color.web(colorHex);
                categories.add(new Category(name,color));
            }}catch (SQLException e ){
            e.printStackTrace();
        }
        return categories;
     }



    public static void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Удалить все записи из таблицы
            stmt.executeUpdate("DELETE FROM plans");



            System.out.println("База данных очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
