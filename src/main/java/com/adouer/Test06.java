package com.adouer;

import com.adouer.dbutil.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC处理事务
 * 模拟一个用户向另一用户转账【这里偷懒用age代替】
 */
public class Test06 {

    public static void main(String[] args) {
        //获取工具类实例
        DBManager instance = DBManager.getInstance();
        //步骤1---获取数据库连接
        Connection conn = instance.openConnection();
        try {
            //步骤2---开启事务
            conn.setAutoCommit(false);
            /*步骤3---转账*/
            String sql1 = "update user set age = age - 1 where id = ?";
            instance.execUpdate(conn, sql1, 1);

            //模拟异常【网络超时等】
            int i = 1 / 0;

            String sql2 = "update user set age = age + 1 where id = ?";
            instance.execUpdate(conn, sql2, 2);
            //步骤4---没有异常，手动提交事务
            conn.commit();
        } catch (Exception e) {
            System.out.println("日志记录：发生异常，执行回滚");
            e.printStackTrace();
            try {
                //步骤5---有异常，回滚事务
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("日志记录：回滚异常");
                e.printStackTrace();
            }
        } finally {
            //步骤6---关闭数据库连接
            instance.closeConnection();
        }

    }
}
