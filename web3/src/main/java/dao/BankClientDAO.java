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
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank_client");) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                long money = resultSet.getLong("money");
                list.add(new BankClient(name, password, money));
            }

        } catch (SQLException e) {
            logger.info("Unable to get a list users");
        }
        return list;
    }

    public boolean validateClient(String name, String password) {
        return false;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) {

    }

    public BankClient getClientById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("select * from bank_client where id = ?");) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            long money = resultSet.getLong("money");
            return new BankClient(name, password, money);
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public boolean isClientHasSum(String name, Long expectedSum) {
        return false;
    }

    public long getClientIdByName(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select id from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        Long id = result.getLong(1);
        result.close();
        stmt.close();
        return id;
    }

    public BankClient getClientByName(String name) {
        return null;
    }

    public void addClient(BankClient client) throws SQLException {
        if (!client.getName().isEmpty() || !client.getPassword().isEmpty()) {
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
                    try (PreparedStatement preparedStatement = connection.
                            prepareStatement("INSERT INTO bank_client(name, password, money) VALUES (?,?,?)");) {
                        preparedStatement.setString(1, client.getName());
                        preparedStatement.setString(2, client.getPassword());
                        preparedStatement.setLong(3, client.getMoney());

                        preparedStatement.execute();
                        logger.info("User added successful");
                    } catch (SQLException e) {
                        System.out.println("I couldn't add a user");
                    }
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
