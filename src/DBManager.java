import object.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver"; //드라이버
    private static String DB_URL = "jdbc:mysql://127.0.0.1/coronaproject?&useSSL=false"; //접속할 DB 서버

    private static String USER_NAME = "root"; //DB에 접속할 사용자 이름
    private static String PASSWORD = "1z2x3c4A5S6D!0"; //사용자의 비밀번호

    public DBManager(){}

    public static UserInfo getUserInfo(int pid){
        UserInfo userInfo = null;

        Connection conn = null;
        Statement state = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();

            String sql = "select * from user_info where p_id = " + pid;
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()) {
                int p_id = rs.getInt("p_id");
                String login_id = rs.getString("login_id");
                String login_password = rs.getString("login_password");
                String name = rs.getString("name");
                String working_building = rs.getString("working_building");
                int working_floor = rs.getInt("working_floor");
                Boolean self_monitor = rs.getBoolean("self_monitor");
                userInfo = new UserInfo(p_id, login_id, login_password, name, working_building, working_floor, self_monitor);
            }
            rs.close();
            state.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(state != null) {
                    state.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return userInfo;
    }

    public static void InsertUserInfo(UserInfo userInfo){
        Connection conn = null;
        Statement state = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();

            String sql = "insert into user_info (login_id, login_password, name, working_building, working_floor)" +
                    "values(\"" + userInfo.getLogin_id() + "\", \"" + userInfo.getLogin_password() + "\", \"" +
                    userInfo.getName() + "\", \"" + userInfo.getBuilding() + "\", \"" +
                    userInfo.getFloor() + "\");";
            int result = state.executeUpdate(sql);

            if(result > 0) System.out.println("Success Insert...");
            else System.out.println("Fail Insert...");

            state.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(state != null) {
                    state.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
