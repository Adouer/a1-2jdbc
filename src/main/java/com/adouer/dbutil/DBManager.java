package com.adouer.dbutil;

import com.adouer.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBManager {

    //数据库连接信息
    private static String dbDriver;
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    //读取配置文件
    static {

        Properties prop = new Properties();
        try {
            //加载属性文件
            prop.load(DBManager.class.getResourceAsStream("/dbconfig.properties"));

            //读取数据库连接信息
            dbDriver = prop.getProperty("dbDriver");
            dbUrl = prop.getProperty("dbUrl");
            dbUsername = prop.getProperty("dbUsername");
            dbPassword = prop.getProperty("dbPassword");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //单例
    private static DBManager instance = new DBManager();

    public static DBManager getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement pstmt;

    //构造方法---加载数据库驱动
    private DBManager() {

        //加载驱动
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //打开数据库连接
    public Connection openConnection() {

        //获取连接
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //更新方法
    public boolean execUpdate(String sql, Object... params) {

        try {
            //获取连接
            this.openConnection();

            //创建语句对象
            this.pstmt = this.conn.prepareStatement(sql);

            //参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection();
        }

    }

    /**
     * 传入Connection对象，用于事务【方便外界传入开启事务的connection对象】
     * @param conn
     * @param sql
     * @param params
     * @return
     */
    public boolean execUpdate(Connection conn,String sql, Object... params) {

        try {
            //获取连接
            this.conn = conn;
            //创建语句对象
            this.pstmt = this.conn.prepareStatement(sql);

            //参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //查询方法
    public ResultSet execQuery(String sql, Object... params) {

        try {
            //获取连接
            this.openConnection();

            //创建语句对象
            this.pstmt = this.conn.prepareStatement(sql);

            //参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    //关闭数据库连接
    public void closeConnection() {

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws SQLException {

        DBManager dbManger = DBManager.getInstance();
        String sql = "select * from user";
        ResultSet rs = dbManger.execQuery(sql);
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            users.add(new User(id, name, age, userName, password));
        }
        for (User user : users) {
            System.out.println("user = " + user);
        }
        dbManger.closeConnection();
    }

}
