package udemy.springsecurityapp.exception_handling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setHeader("eazybank-denied-reason", "Authentication Failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        response.setContentType("application/json;charset=UTF-8"); // response content type 설정
        LocalDateTime currentDateTime = LocalDateTime.now();
        String path = request.getRequestURI(); // 요청 API 경로 추출
        // 상세 오류 메시지 추출
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ?
                accessDeniedException.getMessage() : "Authentication Failed";


        // Json Response 생성
        String jsonResponse =
                String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                        currentDateTime, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message, path);
        response.getWriter().write(jsonResponse);
    }
}
