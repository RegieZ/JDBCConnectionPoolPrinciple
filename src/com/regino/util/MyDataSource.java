package com.regino.util;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

//自定义的一个连接池
/*
 连接池Sun公司要求一定要实现DataSource接口
 */
public class MyDataSource implements DataSource {

    //定义一个集合保存链接
    private LinkedList<Connection> list = new LinkedList<>();
    //LinkedList有出栈的方法，方便增删

    //初始化的链接个数
    private int initSize = 5;  //一旦创建连接池就要有五个链接在连接池中

    private int maxSize = 10; //连接池最大的链接个数

    private int curConnectionCount = 0; //定义一个变量记录当前已经创建了几个链接


    //一旦创建连接池，那么连接池就必须有5个链接。
    public MyDataSource() {
        for (int i = 0; i < initSize; i++) {

            try {
                Connection connection = JdbcUtils.getConnection();
                //把链接放入到集合中
                list.add(connection);
                curConnectionCount++; //修改当前创建的链接个数
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        //情况1： 连接池中有链接
        if (list.size() > 0) {
            //取出链接（本质要从集合中删除）
            Connection connection = list.pop(); //出栈,删除并返回
            return connection;
        }

        //情况2： 连接池中没有链接，但是没有超过最大链接个数，还可以创建新的链接
        if (curConnectionCount < maxSize) {
            Connection connection = JdbcUtils.getConnection();
            curConnectionCount++;
            return connection;
        }


        //情况3： 连接池中没有链接，创建的链接个数也超过了最大的链接个数，那么就只能抛出异常。
        throw new RuntimeException("服务器繁忙，已经超过最大负载量，请稍后");
    }


    //关闭方法 , 关闭链接并不是真正意义上的关闭，而是把链接放回连接池中
    public void close(Connection connection) throws SQLException {
        list.add(connection);
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
