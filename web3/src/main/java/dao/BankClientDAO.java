package dao;

import model.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BankClientDAO {
    private Logger logger = Logger.getLogger(BankClientDAO.class.getName());

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() throws SQLException {
        List<BankClient> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank_client");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            long money = resultSet.getLong("money");
            list.add(new BankClient(name, password, money));
        }

        return list;
    }

    public boolean validateUser(String name, String password) {
        boolean res = false;
        try {
            if (getClientByName(name).getPassword().equals(password)) {
                res = true;
            }
        } catch (SQLException ex) {
            System.out.println("Не правельный логин или пароль");
        }
        return res;
    }

    public boolean isClientHasSum(String name, Long expectedSum) throws SQLException {
        boolean res = false;
        if (getMoney(name) >= expectedSum) {
            res = true;
        }
        return res;
    }

    public void updateClientsMoney(String name, String password, Long transactValue, String nameTo) throws SQLException {
        if (validateUser(name, password)) {
            connection.setAutoCommit(false);
            try {
                if (isClientHasSum(name, transactValue)) {
                    setMoney(name, getMoney(name) - transactValue);
                    setMoney(nameTo, getMoney(nameTo) + transactValue);
                    connection.commit();
                } else {
                    throw new NumberFormatException();
                }
            } catch (SQLException ex) {
                connection.rollback();
            }
        }
    }

    public long getMoney(String name) throws SQLException {
        long res = 0;
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT money FROM bank_client where name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        res = resultSet.getLong("money");
        return res;
    }

    public void setMoney(String name, long count) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE bank_client SET money = ? where name = ?");
        preparedStatement.setLong(1, count);
        preparedStatement.setString(2, name);
        preparedStatement.execute();
    }

    public BankClient getClientById(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.
                prepareStatement("select * from bank_client where id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        long money = resultSet.getLong("money");
        return new BankClient(name, password, money);
    }

    public long getClientIdByName(String name) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT id FROM bank_client where name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public boolean deleteClient(String name) throws SQLException {
        boolean res = false;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("delete from bank_client where name='" + name + "'");
            res = true;
        } catch (SQLException e) {
            System.out.println("Не возможно удалить пользователя");
        }
        return res;
    }

    public BankClient getClientByName(String name) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank_client WHERE name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String name1 = resultSet.getString("name");
        String password = resultSet.getString("password");
        long money = resultSet.getLong("money");
        return new BankClient(name1, password, money);
    }

    public void addClient(BankClient client) throws SQLException {
        if (!client.getName().isEmpty() && !client.getPassword().isEmpty()) {
            try {
                if (getClientIdByName(client.getName().toString()) > 0) {
                    logger.info("A user with this name already exists");
                    throw new SQLDataException();
                }
            } catch (SQLDataException e) {
                throw new SQLDataException();
            } catch (SQLException ex) {
                if (client.getMoney() < 0) {
                    logger.info("The amount cannot be negative");
                    throw new NumberFormatException();
                } else {
                    PreparedStatement preparedStatement = connection.
                            prepareStatement("INSERT INTO bank_client(name, password, money) VALUES (?,?,?)");
                    preparedStatement.setString(1, client.getName());
                    preparedStatement.setString(2, client.getPassword());
                    preparedStatement.setLong(3, client.getMoney());

                    preparedStatement.execute();
                    logger.info("User added successful");
                }
            }
        } else {
            throw new SQLException();
        }
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists bank_client (id bigint auto_increment, name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS bank_client");
        stmt.close();
    }
}
