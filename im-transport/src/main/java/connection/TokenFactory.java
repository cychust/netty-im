package connection;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:37
 **/
public class TokenFactory {
    private final static Logger logger=Logger.getLogger(TokenFactory.class);

    public TokenFactory(){

    }
    /**
     * 调用此方法生成一个token
     *
     * @return
     */
    public Long generate() {
        Long token;

        Random random = new Random();

        while (isExist(token = random.nextLong())) {
            token = random.nextLong();
        }
        logger.info("创建Token(" + token + ")");

        return token;
    }

    private boolean isExist(Long token) {
        return TokenPool.query(token);
    }
}
