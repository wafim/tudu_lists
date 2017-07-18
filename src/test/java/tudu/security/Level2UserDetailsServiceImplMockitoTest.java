package tudu.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;
import tudu.service.impl.UserServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

//Niveau2
public class Level2UserDetailsServiceImplMockitoTest {
    @Mock
    UserServiceImpl userService;
    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }


    /*
    * Simuler une levée d'exceptions - tester que la methode lève bien une UsernameNotFoundException si la méthode findBy lève une ObjectRetrievalFailureException
    * Méhode : loadUserByUsername

    */
    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsername_throw_UsernameNotFoundException() {
        User user = new User();
        user.setLogin("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        Role userRole = new Role();
        userRole.setRole(RolesEnum.ROLE_USER.toString());
        user.getRoles().add(userRole);
        when(userService.findUser("test_user")).thenThrow(new ObjectRetrievalFailureException(User.class, user.getLogin()));

        userDetailsService.loadUserByUsername(user.getLogin());

    }
}
