package tudu.web.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by mwafi on 29/06/2017.
 */
public class SecurityHelper {
    public Authentication getAuthentification() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
