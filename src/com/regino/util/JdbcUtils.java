package com.regino.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {

    private static DataSource dataSource = null;

    // 1.初始化连接池对象（Druid），一个项目只有一个 static{}
    static {
        try {
            // 通过类加载器读取src目录下配置文件，获取io流
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            // 创建Properties对象
            Properties properties = new Properties();
            properties.load(is);
            // Druid连接池工厂对象，初始化连接池
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2.提供获取连接池对象静态方法
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 3.提供连接对象的静态方法
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 4.提供释放资源的静态方法（Connection是归还）
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 重载关闭方法
    public static void close(Statement statement, Connection connection) {
        close(null, statement, connection);
    }
}