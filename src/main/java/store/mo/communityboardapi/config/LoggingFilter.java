package store.mo.communityboardapi.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@WebFilter("/*")
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 요청 정보 로깅
        String requestInfo = getRequestInfo(httpRequest);
        logger.info("Incoming Request:\n{}", requestInfo);

        // 요청 처리
        chain.doFilter(request, response);

        // 응답 정보 로깅
        String responseInfo = getResponseInfo(httpResponse);
        logger.info("Outgoing Response:\n{}", responseInfo);
    }

    private String getRequestInfo(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String headers = Collections.list(request.getHeaderNames()).stream()
                .map(name -> name + ": " + request.getHeader(name))
                .collect(Collectors.joining("\n"));

        String body = getRequestBody(request);

        return String.format(
                "{\n  \"method\": \"%s\",\n  \"uri\": \"%s\",\n  \"headers\": {\n%s\n  },\n  \"body\": \"%s\"\n}",
                method, uri, indent(headers, 4), body
        );
    }

    private String getResponseInfo(HttpServletResponse response) {
        int status = response.getStatus();

        return String.format(
                "{\n  \"status\": %d\n}",
                status
        );
    }

    private String getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "Unable to read request body";
        }
    }

    private String indent(String text, int spaces) {
        String indentation = String.join("", Collections.nCopies(spaces, " "));
        return text.lines().map(line -> indentation + line).collect(Collectors.joining("\n"));
    }
}
