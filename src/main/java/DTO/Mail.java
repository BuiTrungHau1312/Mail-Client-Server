package DTO;

import java.util.List;

public class Mail {
    private static final int UNREAD = 0;
    private static final int READ = 1;
    private static final int SPAM = 2;
    private static final int TRAP = 3;

    private int id;
    private String message;
    private int status = UNREAD;
    private int accountId;
    private List<Account> ReceiveAccountList;

    public List<Account> getReceiveAccountList() {
        return ReceiveAccountList;
    }

    public void setReceiveAccountList(List<Account> receiveAccountList) {
        ReceiveAccountList = receiveAccountList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Mail(int id, String message, int status, int accountId) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.accountId = accountId;
    }

    public Mail() {
    }
}
