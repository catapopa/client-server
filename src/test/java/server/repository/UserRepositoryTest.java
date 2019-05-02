package server.repository;

import domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void save() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        String username = new String(array, Charset.forName("UTF-8"));
        String password = new String(array, Charset.forName("UTF-8"));
        User user = new User(username, password);
        Assert.assertEquals(userRepository.create(user), user);
    }

    @Test
    public void getAll() {
        List<User> users = userRepository.getAll();
        Assert.assertNotEquals(users.size(), null);
    }

    @Test
    public void findOne() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        String username = new String(array, Charset.forName("UTF-8"));
        String password = new String(array, Charset.forName("UTF-8"));
        User user = new User(username, password);
        userRepository.create(user);
        Assert.assertEquals(userRepository.findOne(user.getUsername()).getUsername(), user.getUsername());
        Assert.assertEquals(userRepository.findOne(user.getUsername()).getPassword(), user.getPassword());
    }
}