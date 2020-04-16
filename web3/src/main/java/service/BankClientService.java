package service;

import dao.BankClientDAO;
import exception.DBException;
import model.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankClientService {
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
        return null;
    }

    public List<BankClient> getAllClient() {
        BankClientDAO dao = getBankClientDAO();
        List<BankClient> list = new ArrayList<>();
        try {
           list = dao.getAllBankClient();
        } catch (SQLException e) {
            System.out.println("Невозможно получить пользователей из базы");
        }

        return null;
    }

    public boolean deleteClient(String name) {
        return false;
    }

    public boolean addClient(BankClient client) throws DBException {
        BankClientDAO dao = getBankClientDAO();
        try {
            dao.addClient(client);
        } catch (SQLException e) {
            throw new DBException(e);
        }catch (NumberFormatException ex){
            throw new NumberFormatException();
        }
        return true;
    }

    public boolean sendMoneyToClient(BankClient sender, String name, Long value) {

        return false;
    }

    public void cleanUp() throws DBException {
        BankClientDAO dao = getBankClientDAO();
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void createTable() throws DBException {
        BankClientDAO dao = getBankClientDAO();
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
