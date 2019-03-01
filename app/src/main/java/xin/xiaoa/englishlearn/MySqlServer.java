package xin.xiaoa.englishlearn;

import java.sql.*;

public class MySqlServer {
    //数据库链接e
    private static Statement sql = null;
    //  ResultSet rs;
    private static Connection con = null;

    @SuppressWarnings("unused")
    public static Statement getSql() {
        return sql;
    }

    @SuppressWarnings("unused")
    public static void setSql(Statement tmp) {
        sql = tmp;
    }

    @SuppressWarnings("unused")
    public static Connection getCon() {
        return con;
    }

    @SuppressWarnings("unused")
    public static void setCon(Connection tmp) {
        con = tmp;
    }

    @SuppressWarnings("unused")
    ResultSet sel(String cmd) {
        System.out.println(cmd);
        try { //链接数据库 if(name.equals("admin")){
            sql = con.createStatement();
            if (sql.isClosed())
                System.out.println("连接已经关闭了，不能查询)");
            return sql.executeQuery(cmd); //查询
        } catch (SQLException e) {
            System.out.println("查询数据库问题" + e);
            return null;
        }
    }

    int up(String cmd) {
        System.out.println(cmd);
        //if(sqlStationCheck()) System.out.println("数据库连接正常，定义");
        try {
            sql = con.createStatement();
            if (sql.isClosed())
                System.out.println("连接已经关闭了，不能插入)");
            return sql.executeUpdate(cmd);
        } catch (SQLException e) {
            System.out.println("sql 更新错误" + e);
        }
        return -1;
    }

    private boolean sqlStationCheck() {//查看数据库连接状态
        try {
            if (con != null)
                if (!con.isClosed()) return true;
        } catch (SQLException e) {
            System.out.println("查看数据库连接状态问题" + e);
        }
        return false;
    }

    boolean sqlStation() {
        return sqlStationCheck();
    }

    @SuppressWarnings("unused")
    void initSql() {
        //开始初始化数据库
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载JDBC_MySQL驱动
        } catch (Exception e) {
            System.out.println("错误ser1.5");
        }
        String uri = "jdbc:mysql://47.93.96.41:3306/data?useSSL=true&autoReconnect=true";
        String user = "data";
        String password = "7286014";
        try {
            con = DriverManager.getConnection(uri, user, password); //连接代码
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            System.out.println("连不上" + e + "\n");
        }
    }

}


