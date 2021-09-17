package mk.ukim.finki.wp.eshop.junit;

import mk.ukim.finki.wp.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.wp.eshop.service.impl.AuthServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthServiceImpl service;


}
