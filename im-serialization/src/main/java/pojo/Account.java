package pojo;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 15:15
 **/
public class Account {
    private String username;
    private String password;
    private Long token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }
}
