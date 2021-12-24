package DAL;

import DTO.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class AuthDAL {
    Connection conn;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account getUser(String username, String password) {
        try {
            String query = "select * from Account where username= ? and password= ?";
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

            conn = new ContextDAL().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, encodedPassword);
            rs = ps.executeQuery();
//            System.out.println("rs " + rs.getString(2) + rs.getString(3));

            while (rs.next()) {
                Account account = new Account(rs.getString(2), rs.getString(3));
                System.out.println("receive " + rs.getString(2) + " " + rs.getString(3));
                return account;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account checkExist(String username) {
        try {
            String query = "select * from Account " +
                    "where username = ? ";
            conn = new ContextDAL().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString(2), rs.getString(3));
                return account;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void Register(String username, String password) {
        try {
            String query = "insert into Account(username, password) VALUES(?,?)";
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

            conn = new ContextDAL().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, encodedPassword);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
