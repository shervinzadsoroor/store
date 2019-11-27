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

    public static void createCart(String customerName) {
        Connection connection = null;
        Statement statement = null;
        // PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            String createCart = "create table " + customerName + " (id int,category varchar(30),name varchar(30),quantity int,UniquePrice double,Price double)";
            statement.executeUpdate(createCart);
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean isContainingTheId(int Id, String customerName) {
        Connection connection = null;
        Statement statement = null;
        boolean isContaining = false;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            String sql = "select id from " + customerName;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id == Id) {
                    isContaining = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isContaining;
    }

    public static void findById(int Id, String customerName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "select * from " + customerName + " where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getInt("price");
                System.out.printf("id : %d\tcategory : %s\tname : %s\tquantity : %d\tprice : %.2f\n"
                        , id, category, name, quantity, price);
            }

            rs.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int editCart(int id, int quantity, String customerName, int limitOfPurchase) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            if (isContainingTheId(id, customerName)) {

                String updateSql = "update " + customerName + " set quantity=quantity+? where id=?";
                preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();

            } else {
                //getting info from storeroom=====================================
                statement = connection.createStatement();
                String sql = "select category,name,UniquePrice from storeroom where id=" + id;
                ResultSet rs = statement.executeQuery(sql);
                String category = "";
                String name = "";
                double UniquePrice = 0.0;
                while (rs.next()) {
                    category = rs.getString("category");
                    name = rs.getString("name");
                    UniquePrice = rs.getDouble("UniquePrice");
                }
                double price = quantity * UniquePrice;
                //======================================================================================
                String updateSql = "insert into " + customerName + "(id,category,name,quantity,UniquePrice,Price)" +
                        " values (?,'" + category + "','" + name + "',?,?,?)";
                preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setDouble(3, UniquePrice);
                preparedStatement.setDouble(4, price);
                preparedStatement.executeUpdate();
                limitOfPurchase++;
                rs.close();
                statement.close();
                preparedStatement.close();
                connection.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return limitOfPurchase;
    }
}
