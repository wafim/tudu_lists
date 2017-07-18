package tudu.web.mvc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class Level3AdministrationControllerTest {
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ConfigurationService cfgService;
    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
    *  Vérifier La réponse par défaut du mock configService et faire un test avec la page configuration
    *  Méthode :  display
    *  Aide : configuaration du mock a l'aide de RETURNS_SMART_NULLS
    */
    @Test(expected = Exception.class)
    public void default_value_configService_with_page_configuration() {

        adminController.display("configuration");


    }

  /* Vérifier que le configService.updateEmailProperties est bien appelé en ne vérifiant que les valeurs user et password .
    * Aide : Spy
   * Méthode :  update
    */


/*
* Reprendre sur quelques tests ayant des assertEquals, assertNull, assertNotNull avec le framefork fest assert
*/

    /*
        * - Vérifier qu'aucune interactions n'a lieu lorsque la page demandée n'est ni "configuration" ni "users"
        * Méthode :  display
        */
    @Test
    public void display_should_not_interact_when_page_different_than_configuration_or_users() throws Exception {
        ModelAndView modelAndView = adminController.display("ather");
        assertThat(modelAndView.getViewName()).isEqualTo("administration");
    }


/*
* Reprendre sur quelques tests ayant des when, verify, doThrow en utilisant la syntaxe bdd mockito
*/

    /*
       *
       *  Vérifier dans un test que pour la page "configuration" il n'y a pas d'interaction avec userService.
       * Méthode :  display
       */
    @Test
    public void display_should_read_configService_properties_when_page_is_configuration() throws Exception {
        BDDMockito.given(cfgService.getProperty(anyString())).willReturn(new Property());
        adminController.display("configuration");
        BDDMockito.verifyZeroInteractions(userService);


    }

}
