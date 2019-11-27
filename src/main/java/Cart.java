import java.sql.*;

public class Cart {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/store";

    static final String USER = "root";
    static final String PASS = "";

    public static void findAll(String customerName) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            String sql = "select id,category,name,quantity,UniquePrice,Price from " + customerName;
            ResultSet rs = statement.executeQuery(sql);
            System.out.printf("your cart : \nID\t\tCategory\t\t\tName\t\t Qty\t Unique Price\t\tPrice\n" +
                    "---------------------------------------------------------------------------\n");
            double totalPrice = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double uniquePrice = rs.getDouble("UniquePrice");
                double price = rs.getDouble("Price");
                totalPrice += price;
                System.out.printf("%-8d%-20s%-13s%-8d%-19.2f%.2f\n", id, category, name, quantity, uniquePrice, price);
            }
            System.out.printf("total price is : %.2f\n\n", totalPrice);
            rs.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

        }
    }
}
