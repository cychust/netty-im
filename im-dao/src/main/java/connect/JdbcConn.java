package connect;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 15:40
 **/
public class JdbcConn implements Connect {

    public static final Logger logger = Logger.getLogger(JdbcConn.class);

    protected Connection conn;
    protected PreparedStatement pstmt;
    protected ResultSet resultSet;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("JDBC Driver", e);
        }
        Properties info = new Properties();
        String url = JdbcConfig.DB_URL;
        info.put("user", JdbcConfig.DB_USERNAME);
        info.put("password", JdbcConfig.DB_PASSWORD);

        //获取连接对象
        try {
            conn = DriverManager.getConnection(url, info);
        } catch (SQLException e) {
            logger.warn("DriverManager.getConnection", e);
        }
    }


    public void close() {
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("MySQL关闭ResultSet出现异常", e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.warn("MySQL关闭PreparedStatement出现异常", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warn("MySQL关闭Connection出现异常", e);
            }
        }
    }
}
