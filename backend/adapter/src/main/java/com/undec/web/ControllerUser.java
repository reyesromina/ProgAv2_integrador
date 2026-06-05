package com.undec.web;

import com.undec.controller.dto.OrderRequest;
import com.undec.controller.dto.OrderResponse;
import com.undec.controller.dto.UserRequest;
import com.undec.controller.dto.UserResponse;
import input.*;
import input.dto.TokenResponse;
import model.Order;
import model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import output.PdfGeneratorPort;

@RestController
@RequestMapping("/users")
public class ControllerUser {
    private final CreateOrderInput createOrderInput;
    private final GetUserByIdInput getUserByIdInput;
    private final GenerateUserActivityReportPDFInput generateUserActivityReportPDFInput;
    private final RegisterUserInput registerUserInput;
    private final LoginUserInput loginUserInput;

    public ControllerUser(CreateOrderInput createOrderInput, GetUserByIdInput getUserByIdInput, GenerateUserActivityReportPDFInput generateUserActivityReportPDFInput,
                          RegisterUserInput registerUserInput, LoginUserInput loginUserInput) {

        this.createOrderInput = createOrderInput;
        this.getUserByIdInput = getUserByIdInput;
        this.generateUserActivityReportPDFInput = generateUserActivityReportPDFInput;
        this.registerUserInput = registerUserInput;
        this.loginUserInput = loginUserInput;
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        try {
            Order newOrder = createOrderInput.createOrder(id, request.getAmount());

            return ResponseEntity.ok(OrderResponse.fromDomainOrder(newOrder));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = getUserByIdInput.getUserById(id);

            return ResponseEntity.ok(UserResponse.fromDomainUser(user));

        } catch (ResourceAccessException e) {
            return ResponseEntity.status(404).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error inesperado: " + e.getMessage());
        }
    }

    @GetMapping(value = "/{id}/activity/export/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    //public ResponseEntity<byte[]>
    public ResponseEntity<?> exportPdf(@PathVariable Long id) {

        try {
            byte[] pdfContent = generateUserActivityReportPDFInput.generateUserActivityReport(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_usuario.pdf")
                    .body(pdfContent);
        } catch (ResourceAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        try {
            TokenResponse newUser = registerUserInput.registerUser(request.getEmail(), request.getPassword());

            return ResponseEntity.status(201).body(newUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest request) {
        try {
            TokenResponse newUser =  loginUserInput.login(request.getEmail(), request.getPassword()
            );
            return ResponseEntity.status(200).body(newUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
