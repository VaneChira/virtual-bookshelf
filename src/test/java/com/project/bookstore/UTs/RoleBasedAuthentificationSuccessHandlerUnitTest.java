package com.project.bookstore.UTs;

import com.project.bookstore.security.RoleBasedAuthentificationSuccessHandler;
import com.project.bookstore.security.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Pure unit test for {@link RoleBasedAuthentificationSuccessHandler}: request/response
 * are mocked; Authentication is a real UsernamePasswordAuthenticationToken (a concrete
 * value object) rather than a mock, since Mockito's wildcard-generics stubbing for
 * Collection&lt;? extends GrantedAuthority&gt; doesn't play well with thenReturn.
 */
@ExtendWith(MockitoExtension.class)
class RoleBasedAuthentificationSuccessHandlerUnitTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final RoleBasedAuthentificationSuccessHandler handler = new RoleBasedAuthentificationSuccessHandler();

    @Test
    void onAuthenticationSuccess_redirectsToAdmin_whenUserHasRoleAdmin() throws Exception {
        final var authentication = new UsernamePasswordAuthenticationToken(
                "admin@example.com", "password", List.of(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN)));

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/admin");
    }

    @Test
    void onAuthenticationSuccess_redirectsToHome_whenUserDoesNotHaveRoleAdmin() throws Exception {
        final var authentication = new UsernamePasswordAuthenticationToken(
                "user@example.com", "password", List.of(new SimpleGrantedAuthority(RoleName.ROLE_USER)));

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/");
    }
}
