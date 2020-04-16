package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserService {
    private Logger logger = Logger.getLogger(UserService.class.getName());

    private static UserService instance;
    private AtomicLong maxId = new AtomicLong(0);
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    private UserService() {
        adminDefault();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        return dataBase.values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void adminDefault() {
        User user = new User("admin", "admin");
        Long id = maxId.incrementAndGet();
        dataBase.put(id, new User(id, "admin", "admin"));

        user = new User("mark", "mark");
        Long id2 = maxId.incrementAndGet();
        dataBase.put(id2, new User(id2, "mark", "mark"));

        user = new User("fds", "fds");
        Long id3 = maxId.incrementAndGet();
        dataBase.put(id3, new User(id3, "fds", "fds"));
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        Long longId = maxId.incrementAndGet();
        User newUser = new User(longId, user.getEmail(), user.getPassword());
        if (isExistsThisUser(newUser)) {
            logger.log(Level.INFO, "Пользователь с таким именем уже зарегистрирован");
            return false;
        }
        return dataBase.put(longId, newUser) == null;
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        for (Map.Entry<Long, User> entry : dataBase.entrySet()) {
            if (Objects.equals(user.getEmail(), entry.getValue().getEmail())) {
                return entry.getKey() != null;
            }
        }
        return false;
    }

    public List<User> getAllAuth() {
        return authMap.values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean authUser(User user) {
        Long longId = maxId.incrementAndGet();
        User newUser = new User(longId, user.getEmail(), user.getPassword());
        if (!isExistsThisUser(newUser)) {
            logger.log(Level.INFO, "Пользователь с таким именем не зарегистрирован пожалуйста пройдите регистрацию");
            return false;
        }
        return authMap.put(longId, newUser) == null;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {

        return authMap.get(id) != null;
    }


}
