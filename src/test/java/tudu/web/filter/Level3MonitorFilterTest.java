package tudu.web.filter;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import tudu.domain.RolesEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Level3MonitorFilterTest {

    @Mock
    SecurityHelper securityHelperMock;
    @Mock
    Log logMock;

    @InjectMocks
    MonitorFilter monitorFilter = new MonitorFilter();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    /*
  Verifier que l on passe bien dans les branches
   *               if (principal instanceof String) {
                       userName = "anonymous";
                   } else {
                       User springSecurityUser = (User) principal;
                       userName = springSecurityUser.getUsername();
                   }

    *
    * Duree max : 10 minutes
    */
    @Test
    public void test_authentication_1() throws Exception {
        //given
        Authentication authentication = mock(Authentication.class);
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("anonymous_user");
        when(securityHelperMock.getAuthentification()).thenReturn(authentication);

        monitorFilter.setMonitored(true);

        ServletRequest reqMock = Mockito.mock(ServletRequest.class);
        ServletResponse respMock = Mockito.mock(ServletResponse.class);
        FilterChain chainMock = Mockito.mock(FilterChain.class);

        //when
        monitorFilter.doFilter(reqMock, respMock, chainMock);
        //then
        verify(logMock).debug(matches(".*anonymous.*"));

    }

    @Test
    public void test_authentication_2() throws Exception {
        //given
        Authentication authentication = mock(Authentication.class);
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("username");
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityHelperMock.getAuthentification()).thenReturn(authentication);

        monitorFilter.setMonitored(true);

        ServletRequest reqMock = Mockito.mock(ServletRequest.class);
        ServletResponse respMock = Mockito.mock(ServletResponse.class);
        FilterChain chainMock = Mockito.mock(FilterChain.class);

        //when
        monitorFilter.doFilter(reqMock, respMock, chainMock);

        //then
    }
}
