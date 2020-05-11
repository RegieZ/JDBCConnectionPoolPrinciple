package com.regino.dao;

import com.regino.domain.User;
import com.regino.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public User login(String username, String password) {

        Connection connection =null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet =null;
        try {
            // 1.获取连接【从连接池】
            connection = JdbcUtils.getConnection();
            // 2.编写sql
            String sql = "select * from user where username = ? and password = ?";
            // 3.获取sql预编译执行对象
            preparedStatement = connection.prepareStatement(sql);
            // 4.设置实际参数
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // 5.执行sql并返回结果
            resultSet = preparedStatement.executeQuery();
            // 6.处理结果
            User user = null;
            if (resultSet.next()) {
                // 获取 id 用户名、密码
                int id = resultSet.getInt("id");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 7.释放资源
            JdbcUtils.close(resultSet, preparedStatement, connection);
        }
        return null;
    }
}