package mk.ukim.finki.wp.eshop.junit;

import lombok.SneakyThrows;
import mk.ukim.finki.wp.eshop.model.Role;
import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.eshop.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.eshop.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.eshop.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.wp.eshop.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl service;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name","surname", Role.ROLE_ADMIN, false);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }

    @SneakyThrows
    @Test
    public void testSuccessRegister(){
        String birthday = "1999-12-19";

        User user = this.service.register("username", "password", "password", "name","surname", Role.ROLE_ADMIN, birthday);

        Mockito.verify(this.service).register("username", "password", "password", "name","surname", Role.ROLE_ADMIN, birthday);

        Assert.assertNotNull("User is null", user);

        Assert.assertEquals("name do not match", "name", user.getName());

        Assert.assertEquals("surname do not match", "surname", user.getSurname());

        Assert.assertEquals("username do not match", "username", user.getUsername());

        Assert.assertEquals("password do not match", "password", user.getPassword());

        Assert.assertEquals("roles do not match", Role.ROLE_ADMIN, user.getRole());
    }


    @SneakyThrows
    @Test
    public void testNullUsername(){
        String birthday = "1999-12-19";
        Assert.assertThrows("InvalidUsernameOrPasswordException expected", InvalidUsernameOrPasswordException.class,
                ()->this.service.register(null, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(null, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday);
    }

    @SneakyThrows
    @Test
    public void testEmptyUsername(){
        String birthday = "1999-12-19";
        String username = "";
        Assert.assertThrows("InvalidUsernameOrPasswordException expected", InvalidUsernameOrPasswordException.class,
                ()->this.service.register(username, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(username, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday);
    }
    @SneakyThrows
    @Test
    public void testNullPassword(){
        String birthday = "1999-12-19";
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidUsernameOrPasswordException expected", InvalidUsernameOrPasswordException.class,
                ()->this.service.register(username, password, "password", "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(username, password, "password", "name","surname", Role.ROLE_ADMIN, birthday);
    }

    @SneakyThrows
    @Test
    public void testEmptyPassword(){
        String birthday = "1999-12-19";
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidUsernameOrPasswordException expected", InvalidUsernameOrPasswordException.class,
                ()->this.service.register(username, password, "password", "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(username, password, "password", "name","surname", Role.ROLE_ADMIN, birthday);
    }

    @SneakyThrows
    @Test
    public void testPasswordMismatch(){
        String birthday = "1999-12-19";
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected", PasswordsDoNotMatchException.class,
                ()->this.service.register(username, password, confirmPassword, "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(username, password, confirmPassword, "name","surname", Role.ROLE_ADMIN, birthday);
    }

    @SneakyThrows
    @Test
    public void testDuplicateUsername(){
        String birthday = "1999-12-19";
        User user = new User("username", "password", "name", "surname", Role.ROLE_ADMIN, false);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected", UsernameAlreadyExistsException.class,
                ()->this.service.register(username, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday));
        Mockito.verify(this.service).register(username, "password", "password", "name","surname", Role.ROLE_ADMIN, birthday);
    }
}
