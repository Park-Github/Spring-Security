package udemy.springsecurityapp.exception_handling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authentication Failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setContentType("application/json;charset=UTF-8"); // response content type 설정
        LocalDateTime currentDateTime = LocalDateTime.now();
        String path = request.getRequestURI(); // 요청 API 경로 추출
        // 상세 오류 메시지 추출
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : "Unauthorized";


        // Json Response 생성
        String jsonResponse =
                String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                        currentDateTime, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, path);
        response.getWriter().write(jsonResponse);
    }
}
