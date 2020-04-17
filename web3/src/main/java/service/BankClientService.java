package service;

import dao.BankClientDAO;
import exception.DBException;
import model.BankClient;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankClientService {
    private BankClientDAO dao = getBankClientDAO();

    public BankClientService() {
    }

    public BankClient getClientById(long id) throws DBException {
        try {
            return getBankClientDAO().getClientById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public BankClient getClientByName(String name) {
        BankClient bankClient = null;
        try {
            bankClient = dao.getClientByName(name);
        } catch (SQLException ex) {
            System.out.println("Не возможно найти такого клиента");
        }
        return bankClient;
    }

    public List<BankClient> getAllClient() {
        List<BankClient> list = new ArrayList<>();
        try {
            list = dao.getAllBankClient();
        } catch (SQLException e) {
            System.out.println("Невозможно получить пользователей из базы");
        }
        return list;
    }

    public boolean deleteClient(String name) {
        try {
            dao.deleteClient(name);
            return true;
        } catch (SQLException ex) {
            System.out.println("User is not deleted");
        }
        return false;
    }

    public boolean addClient(BankClient client) throws DBException {
        boolean res = false;
        try {
            dao.addClient(client);
            res = true;
        } catch (SQLException e) {
//            throw new DBException(e);
        } catch (NumberFormatException ex) {
//            throw new NumberFormatException();
        }
        return res;
    }

    public boolean sendMoneyToClient(BankClient sender, String name, Long value) {
        try {
            dao.updateClientsMoney(sender.getName(), sender.getPassword(), value, name);
            return true;
        } catch (Exception e) {
            System.out.println("Перевод не осуществлен");
        }
        return false;
    }

    public void cleanUp() throws DBException {
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void createTable() throws DBException {
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=root&").          //login
                    append("password=root&").       //password
                    append("serverTimezone=UTC");   //setup server time


            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static BankClientDAO getBankClientDAO() {
        return new BankClientDAO(getMysqlConnection());
    }
}
