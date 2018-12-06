package operation;

import pojo.User;
import connect.JdbcConn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问类
 * <p>
 * @author Yohann.
 */
public class UserDao extends JdbcConn {

    /**
     * 添加用户
     *
     * @param username
     * @param password
     * @return
     */
    public int insertUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL添加用户出现异常", e);
        }
        return row;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public List<User> queryByUsername(String username) {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSex(resultSet.getString("sex"));
                user.setAge(resultSet.getString("age"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setIntroduction(resultSet.getString("introduction"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.warn("MySQL查询用户出现异常", e);
        }
        return users;
    }

    /**
     * 修改姓名
     *
     * @param username
     * @param name
     * @return
     */
    public int updateName(String username, String name) {
        String sql = "update users set name = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改姓名出现异常", e);
        }
        return row;
    }

    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    public int updatePassword(String username, String password) {
        String sql = "update users set password = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改密码出现异常", e);
        }
        return row;
    }

    /**
     * 修改性别
     *
     * @param username
     * @param sex
     * @return
     */
    public int updateSex(String username, String sex) {
        String sql = "update users set sex = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sex);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改性别出现异常", e);
        }
        return row;
    }

    /**
     * 修改年龄
     *
     * @param username
     * @param age
     * @return
     */
    public int updateAge(String username, String age) {
        String sql = "update users set age = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, age);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改年龄出现异常", e);
        }
        return row;
    }

    /**
     * 修改联系电话
     *
     * @param username
     * @param phone
     * @return
     */
    public int updatePhone(String username, String phone) {
        String sql = "update users set phone = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改电话出现异常", e);
        }
        return row;
    }

    /**
     * 修改地址
     *
     * @param username
     * @param address
     * @return
     */
    public int updateAddress(String username, String address) {
        String sql = "update users set address = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改地址出现异常", e);
        }
        return row;
    }

    /**
     * 修改自我介绍
     *
     * @param username
     * @param introduction
     * @return
     */
    public int updateIntroduction(String username, String introduction) {
        String sql = "update users set introduction = ? where username = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, introduction);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("MySQL修改自我介绍出现异常", e);
        }
        return row;
    }
}
