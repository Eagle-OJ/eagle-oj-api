package org.inlighting.oj.web.data;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.inlighting.oj.web.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Smith
 **/
public class DataHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataHelper.class);

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 不开启事务
     */
    public static SqlSession getSession() {
        return getSession(false);
    }

    /**
     * 开启事务
     */
    public static SqlSession getSession(boolean transaction) {
        return sqlSessionFactory.openSession(!transaction);
    }
}
