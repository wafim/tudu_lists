package tudu.web.mvc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.AnyVararg;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;
import org.springframework.web.servlet.ModelAndView;
import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Answers.RETURNS_SMART_NULLS;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Level1AdministrationControllerTest {

    @Mock
    private ConfigurationService cfgService;
    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
    * Vérifier dans un test que pour la page "configuration" les propriétés smtp (et uniquement celles là) soit donnée au model
    * Méthode :  display
    */
    @Test
    public void display_should_put_smtp_config_properties_in_admin_model_when_page_is_configuration() throws Exception {

        when(cfgService.getProperty(anyString())).thenReturn(new Property());
        adminController.display("");
        verify(cfgService, times(5)).getProperty(matches("smtp.*"));

    }

    /*
   * Vérifier que l update ne retourne pas un modele null
   * */
    @Test
    public void update_shouldnt_return_a_null_model() throws Exception {

        AdministrationModel administrationModel = new AdministrationModel();
        administrationModel.setAction("configuration");
        administrationModel.setPropertyStaticPath("path");
        administrationModel.setGoogleAnalyticsKey("key");
        administrationModel.setSmtpHost("localhost");
        administrationModel.setSmtpPort("9898");
        administrationModel.setSmtpUser("user");
        administrationModel.setSmtpPassword("password");
        administrationModel.setSmtpFrom("smtp");

        cfgService.updateApplicationProperties(administrationModel.getPropertyStaticPath(),
                administrationModel.getGoogleAnalyticsKey());
        cfgService.updateEmailProperties(
                administrationModel.getSmtpHost(),
                administrationModel.getSmtpPort(),
                administrationModel.getSmtpUser(),
                administrationModel.getSmtpPassword(),
                administrationModel.getSmtpFrom()
        );
        when(cfgService.getProperty(anyString())).thenReturn(new Property());
        ModelAndView mv = adminController.update(administrationModel);
        assertThat(mv).isNotNull();

    }


    private Property property(String value) {
        Property property = new Property();
        property.setValue(value);
        return property;
    }
}
