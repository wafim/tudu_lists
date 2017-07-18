package tudu.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Level1UserServiceImplMockitoTest {


    User user = new User();
    @Mock
    EntityManager entityManager;
    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        user.setLogin("test_user");
        user.setFirstName("First name");
        user.setLastName("Last name");
    }

    /*
    Type : Test état
    Vérifier que l utilisateur retourné par le service correspond bien à celui attendu.
    Méthode : findUser
    Aide : utilisation when pour mocker l appel a entityManager.find et retourner le user en variable de classe
    */
    @Test
    public void find_user_should_return_the_user() {

        when(entityManager.find(User.class, "test_user")).thenReturn(user);
        User userResult = userService.findUser("test_user");
        assertEquals(user.getLogin(), userResult.getLogin());
        assertEquals(user.getFirstName(), userResult.getFirstName());
        assertEquals(user.getLastName(), userResult.getLastName());

    }

    @Test
    /*
    Type : Test Comportement
    Vérifier que l appel a l'entity manager a bien été effectué avec le bon user
    Méthode : updateUser
    Aide : Utilisation de verify
    */
    public void update_user_should_call_entityManager_merge() {
        userService.updateUser(user);
        verify(entityManager).merge(user);

    }

    @Test
    /*
    Type : Test état
    Vérifier que l'appel a findUser("toto") entraine bien un appel a entityManager.find avec le meme login.
    Méthode : findUser
    */
    public void user_should_be_retrieved() {

        when(entityManager.find(User.class, "test_user")).thenReturn(user);
        User userResult = userService.findUser("test_user");
        verify(entityManager).find(User.class, "test_user");
    }


    /*
    Vérifier qu'une exception de type ObjectRetrievalFailureException est bien levée si l entityManager find renvoie null
    Méthode : findUser
    */
    @Test(expected = ObjectRetrievalFailureException.class)
    public void error_should_be_thrown_when_a_user_is_not_found() {
        when(entityManager.find(User.class, "test_user")).thenReturn(null);
        userService.findUser("test_user");

    }

    /*
    Vérifier qu'une exception de type UserAlreadyExistsException est bien levée si le login existe deja
    Méthode : createUser
    */

    @Test(expected = UserAlreadyExistsException.class)
    public void exception_should_be_thrown_when_creating_an_already_existed_user() throws UserAlreadyExistsException {
        when(entityManager.find(User.class, "test_user")).thenReturn(user);
        userService.createUser(user);
    }

    /*
    Type : Test Comportement
    Vérifier que l'utilisateur a bien été sauvegardé
    Méthode : createUser
    */
    @Test
    public void new_user_should_be_saved() throws UserAlreadyExistsException {
        when(entityManager.find(User.class, "test_user")).thenReturn(null);
        userService.createUser(user);
        verify(entityManager).persist(user);
    }


}
