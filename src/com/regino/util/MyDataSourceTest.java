package com.regino.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDataSourceTest {
    @Test
    public void test1() throws SQLException {
        MyDataSource dataSource = new MyDataSource();
        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            System.out.println(i + " ： " + connection);
        }
        //第11个
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void test2() throws SQLException {
        MyDataSource dataSource = new MyDataSource();
        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            System.out.println(i + " ： " + connection);
            if (i == 9) {
                //把第10个用完放回连接池中
                dataSource.close(connection);
            }
        }
        //第11个
        System.out.println(dataSource.getConnection());
    }
}
