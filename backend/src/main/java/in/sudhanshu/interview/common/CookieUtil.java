package in.sudhanshu.interview.common;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

    public void addAccessTokenCookies(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(15 * 60);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if ("access_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public void clearAccessTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
