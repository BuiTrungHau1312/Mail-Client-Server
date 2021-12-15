package Controller;

import DAL.AuthDAL;
import DTO.Account;

public class UserController {
    public Account DoLogin(String username, String password) {
        return new AuthDAL().getUser(username, password);
    }
}
