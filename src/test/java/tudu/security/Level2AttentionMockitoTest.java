package tudu.security;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;
import tudu.service.impl.UserServiceImpl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

//Niveau2
//Enlever le @Ignore et trouver pourquoi la deuxieme methode ne fonctionne pas comme prevue comparee a la premiere
public class Level2AttentionMockitoTest {
    @Mock
    UserServiceImpl userService;
    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setLogin("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        Role userRole = new Role();
        userRole.setRole(RolesEnum.ROLE_USER.toString());
        user.getRoles().add(userRole);
        when(userService.findUser("test_user")).thenReturn(user);


        UserDetails springSecurityUser = userDetailsService
                .loadUserByUsername("test_user");

        assertEquals(user.getLogin(), springSecurityUser.getUsername());
        assertEquals(user.getPassword(), springSecurityUser.getPassword());
        assertNotNull(user.getLastAccessDate());
        assertEquals(1, springSecurityUser.getAuthorities().size());
        assertEquals(RolesEnum.ROLE_USER.toString(),
                springSecurityUser.getAuthorities().iterator().next().getAuthority());

    }

    @Test
    public void testLoadUserByUsername_erreur() {
        User user = new User();
        user.setLogin("test_user");
        user.setPassword("password");
        user.setEnabled(true);
        Role userRole = new Role();
        userRole.setRole(RolesEnum.ROLE_USER.toString());
        user.getRoles().add(userRole);
        when(userService.findUser("test_user")).thenReturn(user);


        UserDetails springSecurityUser = userDetailsService
                .loadUserByUsername("test_user");

        assertEquals(user.getLogin(), springSecurityUser.getUsername());
        assertEquals(user.getPassword(), springSecurityUser.getPassword());
        assertNotNull(user.getLastAccessDate());
        assertEquals(1, springSecurityUser.getAuthorities().size());
        assertEquals(RolesEnum.ROLE_USER.toString(),
                springSecurityUser.getAuthorities().iterator().next().getAuthority());

    }

//	 @Test
//    public void testLoadUserByUsername_danger() {
//        User user = new User();
//        user.setLogin("test_user");
//        user.setPassword("password");
//        user.setEnabled(true);
//        Role userRole = new Role();
//        userRole.setRole(RolesEnum.ROLE_USER.toString());
//        user.getRoles().add(userRole);
//        when(userService.findUserJoke("test_user")).thenReturn(user);
//
//
//        UserDetails springSecurityUser = userDetailsService
//                .loadUserByUsernameJoke( "test_user" );
//
//        assertEquals(user.getLogin(), springSecurityUser.getUsername());
//        assertEquals(user.getPassword(), springSecurityUser.getPassword());
//        assertNotNull(user.getLastAccessDate());
//        assertEquals(1, springSecurityUser.getAuthorities().size());
//        assertEquals(RolesEnum.ROLE_USER.toString(),
//                springSecurityUser.getAuthorities().iterator().next().getAuthority());
//
//    }
}
