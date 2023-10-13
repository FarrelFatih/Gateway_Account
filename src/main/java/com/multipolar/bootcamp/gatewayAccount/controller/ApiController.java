package com.multipolar.bootcamp.gatewayAccount.controller;

import java.time.LocalDateTime;
import java.util.List;
// import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gatewayAccount.dto.AccountDTO;
import com.multipolar.bootcamp.gatewayAccount.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gatewayAccount.kafka.AccessLog;
import com.multipolar.bootcamp.gatewayAccount.service.AccessLogService;
import com.multipolar.bootcamp.gatewayAccount.util.RestTemplateUtil;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final String BANKACCOUNT_URL = "http://localhost:8083/bankAccount";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService accessLogService;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper,
            AccessLogService accessLogService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAccounts(HttpServletRequest request) throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(BANKACCOUNT_URL, new ParameterizedTypeReference<>() {
            });
            AccessLog accessLog = new AccessLog("GET", BANKACCOUNT_URL, response.getStatusCodeValue(), "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getAccounts/{id}")
    public ResponseEntity<?> getAccountsById(@PathVariable String id, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(BANKACCOUNT_URL + "/" + id,
                    new ParameterizedTypeReference<>() {
                    });
            AccessLog accessLog = new AccessLog("GET by Id", BANKACCOUNT_URL, response.getStatusCodeValue(), "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET by Id", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getAccounts/{accountNumber}")
    public ResponseEntity<?> getAccountsByNumber(@PathVariable String accountNumber, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(BANKACCOUNT_URL + "/accountNumber" + accountNumber,
                    new ParameterizedTypeReference<>() {
                    });
            AccessLog accessLog = new AccessLog("GET by Number", BANKACCOUNT_URL, response.getStatusCodeValue(),
                    "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET by Number", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed",
                    clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/createAccounts")
    public ResponseEntity<?> postCustomers(@RequestBody AccountDTO accountDTO, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.post(BANKACCOUNT_URL, accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog("CREATE", BANKACCOUNT_URL, response.getStatusCodeValue(), "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("CREATE", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PutMapping("/updateAccounts/{id}")
    public ResponseEntity<?> updateAccountById(@RequestBody AccountDTO accountDTO, @PathVariable String id,
            HttpServletRequest request)
            throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.put(BANKACCOUNT_URL + "/" + id, accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog("Update by Id", BANKACCOUNT_URL, response.getStatusCodeValue(),
                    "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("Update by Id", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed",
                    clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteAccounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.delete(BANKACCOUNT_URL + "/" + id);
            AccessLog accessLog = new AccessLog("DELETE", BANKACCOUNT_URL, response.getStatusCodeValue(),
                    "Success",
                    clientIP, userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("DELETE", BANKACCOUNT_URL, ex.getRawStatusCode(), "Failed",
                    clientIP,
                    userAgent, LocalDateTime.now());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
}
