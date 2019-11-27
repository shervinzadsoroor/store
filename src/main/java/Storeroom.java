import java.sql.*;

public class Storeroom {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/store";

    static final String USER = "root";
    static final String PASS = "";

    public static void showStoreroom() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            String sql = "select id,category,name,quantity,UniquePrice from storeroom ";
            ResultSet rs = statement.executeQuery(sql);
            System.out.printf("ID\t\tCategory\t\t\tName\t\t Qty\t Unique Price\n" +
                    "-----------------------------------------------------------\n");
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double UniquePrice = rs.getDouble("UniquePrice");
                System.out.printf("%-8d%-20s%-13s%-8d%.2f\n", id, category, name, quantity, UniquePrice);

            }
            rs.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

        }
    }

    public static void add(int id, String category, String name, int quantity, double price) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "insert into storeroom values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, price);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
