package tudu.web.mvc;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import tudu.domain.Property;
import tudu.domain.User;
import tudu.service.ConfigurationService;
import tudu.service.UserService;
import tudu.service.impl.UserServiceImpl;

import javax.persistence.EntityManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_SMART_NULLS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Level2AdministrationControllerTest {

    @Mock
    private ConfigurationService cfgService;
    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
     * - Vérifier qu'aucune interactions n'a lieu lorsque la page demandée n'est ni "configuration" ni "users"
     * Méthode :  display
     */
    @Test
    public void display_should_not_interact_when_page_different_than_configuration_or_users() throws Exception {
        ModelAndView modelAndView = adminController.display("ather");
        assertEquals("administration", modelAndView.getViewName());
    }

    /*
    *
    *  Vérifier dans un test que pour la page "configuration" il n'y a pas d'interaction avec userService.
    * Méthode :  display
    */
    @Test
    public void display_should_read_configService_properties_when_page_is_configuration() throws Exception {
        when(cfgService.getProperty(anyString())).thenReturn(new Property());
        adminController.display("configuration");
        verify(userService, times(0)).getNumberOfUsers();

    }


    /*
    * - Vérifer que pour l'action "enableUser" le service afférent est appelé et que disableUser ne l'est pas
    * Méthode :  update
    */
    @Test
    public void update_enable_user_on_enableUser_action() throws Exception {
        AdministrationModel administrationModel = new AdministrationModel();
        administrationModel.setAction("enableUser");
        administrationModel.setLogin("login");


        ModelAndView mv = adminController.update(administrationModel);
        verify(userService, times(1)).enableUser(administrationModel.getLogin());
        verify(userService, times(0)).disableUser(administrationModel.getLogin());

    }

    /*
    * - Vérifer que pour l'action "disableUser" le service afférent est appelé et que enableUser ne l'est pas (d'une manière différente)
    * Méthode :  update
    */
    @Test
    public void update_can_disable_user_on_disableUser_action() throws Exception {
        AdministrationModel administrationModel = new AdministrationModel();
        administrationModel.setAction("disableUser");
        administrationModel.setLogin("login");


        ModelAndView mv = adminController.update(administrationModel);
        verify(userService, times(0)).enableUser(administrationModel.getLogin());
        verify(userService, times(1)).disableUser(administrationModel.getLogin());

    }

    /*
    *
    *  Vérifier que pour l'action appelle findUsersByLogin après un enableUser ou un disableUser
    * Méthode :  update
    */
    @Test
    public void update_should_fetch_users_on_login_after_disabling_suer() throws Exception {

        AdministrationModel administrationModel = new AdministrationModel();
        administrationModel.setAction("enableUser");
        administrationModel.setLogin("login");
        administrationModel.setSearchLogin("search");

        userService.enableUser(administrationModel.getLogin());
        adminController.update(administrationModel);


        administrationModel.setAction("disableUser");

        userService.disableUser(administrationModel.getLogin());
        adminController.update(administrationModel);

        verify(userService, atLeastOnce()).findUsersByLogin(administrationModel.getSearchLogin());

    }


}
