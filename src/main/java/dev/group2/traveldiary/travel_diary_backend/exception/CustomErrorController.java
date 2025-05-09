package dev.group2.traveldiary.travel_diary_backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.defaults()
        );
        if ( (int) attributes.get("status") == 403) {
            attributes.put("message", "Unauthorized access to operation");
        }
        if ( (int) attributes.get("status") == 405) {
            attributes.put("message", "Method Not Allowed,Bad Request");
        }
        if ( (int) attributes.get("status") == 401) {
            attributes.put("message", "Unauthorized access to operation");
        }

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", attributes.get("status"));
        errorBody.put("error", attributes.get("error"));
        errorBody.put("message", attributes.get("message") != null ? attributes.get("message") : "No message available");
        errorBody.put("path", attributes.get("path"));

        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }
}