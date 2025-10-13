package utils;

import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

// 工具类
public class C3p0Tool {
    // 使用单例模式创建数据源
    private static final DataSource dataSource = new ComboPooledDataSource();

    // 获取数据源
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 获取数据库连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // 打印异常信息并抛出运行时异常
            e.printStackTrace();
            throw new RuntimeException("Failed to get database connection", e);
        }
    }
}