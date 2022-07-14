package Database;
import Interface.Program.Group;
import Interface.Program.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDatabase {
    private static Connection con;
    private static final String name = "Shop";

    public void initialization() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + name);
            PreparedStatement st = con.prepareStatement(
                    "CREATE TABLE if NOT EXISTS 'ProductGroup' (" +
                            "'GroupID' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "'GroupName' VARCHAR(20) NOT NULL UNIQUE, " +
                            "'Description' VARCHAR(300) NULL DEFAULT ' ')");

            st.executeUpdate();

            st = con.prepareStatement(
                    "CREATE TABLE if NOT EXISTS 'Product' (" +
                            "'ProductID' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "'ProductName' VARCHAR(20) NOT NULL UNIQUE, " +
                            "'ProductAmount' INT DEFAULT 0 CHECK(ProductAmount > 0), " +
                            "'ProductPrice' DECIMAL DEFAULT 0, " +
                            "'GroupID' INTEGER DEFAULT 0, " +
                            "'Brand' VARCHAR(20) DEFAULT '', " +
                            "'Description' VARCHAR(300) NULL DEFAULT '', " +
                            "CONSTRAINT 'GroupID' " +
                            "FOREIGN KEY ('GroupID') " +
                            "REFERENCES 'ProductGroup'('GroupID'))");

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит");
            //todo вивести помилку на екран
            e.printStackTrace();
        }
    }

    public void insertGroup(String name, String description) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO 'ProductGroup'(GroupName, Description) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, description);

        statement.executeUpdate();
        statement.close();
    }

    public void insertProduct(String name, int amount, double price, String groupName, String brand,String description) throws SQLException {
        PreparedStatement statement = con.prepareStatement("SELECT * FROM 'ProductGroup' WHERE GroupName = ?");
        statement.setString(1, groupName);
        ResultSet res = statement.executeQuery();

        statement = con.prepareStatement(
                "INSERT INTO Product(ProductName, ProductAmount, ProductPrice, GroupID, Brand, Description) " +
                        "VALUES (?, ?, ?, ?, ?, ?)");

        statement.setString(1, name);
        statement.setInt(2, amount);
        statement.setDouble(3, price);
        statement.setInt(4, res.getInt("GroupID"));
        statement.setString(5, brand);
        statement.setString(6, description);

        statement.executeUpdate();
        statement.close();
    }

    public Product readProduct(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM Product WHERE ProductName = ?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        Product product = new Product(
                res.getString("ProductName"),
                res.getInt("ProductAmount"),
                res.getDouble("ProductPrice"),
                res.getString("Brand"),
                res.getString("Description")
                );

        res.close();
        statement.close();

        return product;
    }
    public Group readGroup(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM ProductGroup WHERE GroupName = ?");
        statement.setString(1, name);
        ResultSet resGroup = statement.executeQuery();

        Group group = new Group(
                resGroup.getString("GroupName"),
                resGroup.getString("Description"));


        resGroup.close();
        statement.close();

        return group;
    }
    public Group readGroup(int groupID) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM ProductGroup WHERE GroupID = ?");
        statement.setInt(1, groupID);
        ResultSet resGroup = statement.executeQuery();

        Group group = new Group(
                resGroup.getString("GroupName"),
                resGroup.getString("Description"));


        resGroup.close();
        statement.close();

        return group;
    }

    public void updateProduct(String oldName, String newName, int amount, double price, String brand, String description) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "UPDATE Product SET ProductName = ?, ProductAmount = ?, ProductPrice = ?,  "+
                        "Brand = ?, Description = ? WHERE ProductName = ?");
        statement.setString(1, newName);
        statement.setInt(2, amount);
        statement.setDouble(3, price);
        statement.setString(4, brand);
        statement.setString(5, description);
        statement.setString(6, oldName);

        statement.executeUpdate();
        statement.close();
    }
    public void addProductAmount(String name, int amount) throws SQLException {
        PreparedStatement statement = con.prepareStatement("SELECT ProductAmount FROM Product WHERE ProductName = ?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        statement = con.prepareStatement(
                "UPDATE Product SET ProductAmount = ? WHERE ProductName = ?");
        statement.setInt(1, amount + res.getInt("ProductAmount"));
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    public void reduceProductAmount(String name, int amount) throws SQLException {
        PreparedStatement statement = con.prepareStatement("SELECT ProductAmount FROM Product WHERE ProductName = ?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        if (res.getInt("ProductAmount") - amount < 0) throw new SQLException("Incorrect value. Amount < 0");

        statement = con.prepareStatement(
                "UPDATE Product SET ProductAmount = ? WHERE ProductName = ?");
        statement.setInt(1, res.getInt("ProductAmount") - amount);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    public void updateGroup(String oldName, String newName, String description) throws SQLException {

        PreparedStatement statement = con.prepareStatement(
                "UPDATE ProductGroup SET GroupName = ?, Description = ? WHERE GroupName = ?");

        statement.setString(3, oldName);
        statement.setString(1, newName);
        statement.setString(2, description);

        statement.executeUpdate();
        statement.close();
    }

    public void deleteGroup(String name) throws SQLException {
        ArrayList<Product> products = getAllProductsInGroup(name);
        for (Product p: products) {
            deleteProduct(p.getName());
        }
        PreparedStatement statement = con.prepareStatement(
                "DELETE FROM ProductGroup WHERE GroupName = ?;");

        statement.setString(1, name);
        statement.executeUpdate();

        statement.close();
    }
    public void deleteProduct(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "DELETE FROM Product WHERE ProductName = ?");

        statement.setString(1, name);
        statement.executeUpdate();
        statement.close();
    }
    public void deleteAllGroups() throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM ProductGroup");
        statement.executeUpdate();

        statement.close();

    }
    public void deleteAllProducts() throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM Product");
        statement.executeUpdate();

        statement.close();
    }

    public ArrayList<Group> groupListByCriteria(Criteria criteria) {
        String where = null;
        if(criteria.getName()!=null) { where = " name like '%" + criteria.getName() + "%' ";}

        ArrayList<Group> list =new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = where == null
                    ? "SELECT * FROM ProductGroup ;"
                    : "SELECT * FROM ProductGroup WHERE " + where+ " ;";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                list.add(new Group(
                        res.getString("GroupName"),
                        res.getString("Description"))
                );
            }
            res.close();
            st.close();

        }catch(SQLException e){
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Group> getGroupList() throws SQLException {
        ArrayList<Group> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM ProductGroup");

        while (res.next()) {
            list.add(new Group(
                    res.getString("GroupName"),
                    res.getString("Description"))
            );
        }
        res.close();
        st.close();
        return list;
    }


    public ArrayList<Product> productListByCriteria(Criteria criteria) {
        List<String> criterias = new ArrayList();
        if(criteria.getName()!=null) { criterias .add(" ProductName like '%" + criteria.getName() + "%' ");}
        if(criteria.getPriceFrom()!=null){ criterias .add(" ProductPrice >=" + criteria.getPriceFrom());}
        if(criteria.getPriceTill()!=null){ criterias .add(" ProductPrice <=" + criteria.getPriceTill());}
        if(criteria.getAmountFrom()!=null){ criterias .add(" ProductAmount >=" + criteria.getAmountFrom());}
        if(criteria.getAmountTill()!=null){ criterias .add(" ProductAmount <=" + criteria.getAmountTill());}
        String where = String.join(" and", criterias );

        ArrayList<Product> list =new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = criterias .isEmpty()
                    ? "SELECT * FROM Product ;"
                    : "SELECT * FROM Product WHERE " + where+" ;";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                list.add(new Product(
                        res.getString("ProductName"),
                        res.getInt("ProductAmount"),
                        res.getDouble("ProductPrice"),
                        res.getString("Brand"),
                        res.getString("Description")
                ));
            }

            res.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Product> getAllProductsInGroup(String groupName) throws SQLException {

        ArrayList<Product> list = new ArrayList<>();
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM Product WHERE GroupID IN (SELECT GroupID FROM ProductGroup WHERE GroupName = ?)");
        statement.setString(1, groupName);
        ResultSet res = statement.executeQuery();

        while (res.next()) {
            list.add(new Product(
                    res.getString("ProductName"),
                    res.getInt("ProductAmount"),
                    res.getDouble("ProductPrice"),
                    res.getString("Brand"),
                    res.getString("Description")
            ));
        }
        res.close();
        statement.close();

        return list;
    }

}
