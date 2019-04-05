package ru.geekbrains.pocket.messenger.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.geekbrains.pocket.messenger.client.controller.AuthController;
import ru.geekbrains.pocket.messenger.client.controller.ClientController;
import ru.geekbrains.pocket.messenger.database.HibernateUtil;
import ru.geekbrains.pocket.messenger.database.entity.Message;
import ru.geekbrains.pocket.messenger.database.entity.User;
import ru.geekbrains.pocket.messenger.database.entity.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Если программа должна выполнить какую-то бизнес-логику - она делает
 * это через сервисы. Сервис содержит внутри себя UserDao, и в своих
 * методах вызывает методы DAO. Это может показаться дублированием функций
 * (почему бы просто не вызывать методы из dao-объекта), но при большом
 * количестве объектов и сложной логике разбиение приложения на слои дает
 * огромные преимущества (это good practice").
 *
 */
public class DataBaseService {
    private static final Logger log = LogManager.getLogger(DataBaseService.class);

    private static DataBaseService instance;

    private UserDAO usersDao;
    private UserProfileDAO usersProfileDao;
    private MessageDAO messageDao;

    public DataBaseService() {
        usersDao = new UserDAO();
        usersProfileDao = new UserProfileDAO();
        messageDao = new MessageDAO();
    }

    public static DataBaseService getInstance() {
        if (instance == null) {
            instance = new DataBaseService();
        }
        return instance;
    }

    public void setUserDB(String username) {
        HibernateUtil.setUsername(username);
    }

    public void setUserDB(User user) {
        HibernateUtil.setUsername(user.getAccount_name());
    }

    public void insertUser(User user) {
        UserProfile userProfile = new UserProfile(user.getProfile());
        usersProfileDao.insert(userProfile);
        userProfile = usersProfileDao.getByUsername(userProfile.getUsername());
        user.setProfile(userProfile);
        usersDao.insert(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public User getUser(long id) {
        return usersDao.get(id);
    }

    public User getUserByEmail(String email) {
        return usersDao.getByEmail(email);
    }

    public User getUser(String username) {
        return usersDao.getByUsername(username);
    }

    public User getUserbyUid(String uid) {
        return usersDao.getByUid(uid);
    }

    public List<User> getAllUsers() {
        return usersDao.get();
    }

    public List<String> getAllUserNames() {
        List<String> names = new ArrayList<>();
        for (User user : usersDao.get()) {
            names.add(user.getAccount_name());
        }
        return names;
    }

    public List<Long> getAllUserId() {
        return usersDao.getColumnOfData("id");
    }

    public List<String> getAllUserUid() {
        return usersDao.getColumnOfDataAsStringList("uid");
    }

    public void addMessage(Long receiverId, Long senderID, Message message) {
        messageDao.addSentMessage(senderID, message);
        messageDao.addReceivedMessage(receiverId, message);
        System.out.println("BD addMessage "+ receiverId+" "+senderID+" "+message);
    }

    public List<Message> getChat(User user1, User user2){
        return messageDao.get(user1, user2);
    }

    public void deleteChat(User user1, User user2){
        messageDao.delete(user1, user2);
    }

    public Message findMessageById(long id) {
        return messageDao.findMessageById(id);
    }

    public void close(){
        usersDao.close();
        messageDao.close();
    }
}