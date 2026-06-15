package com.undec.web;

import com.undec.controller.dto.*;
import com.undec.persistence.repository.ProjectRepositoryImpl;
import input.*;
import input.dto.TokenResponse;
import model.Order;
import model.Project;
import model.Task;
import model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import output.PdfGeneratorPort;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ControllerUser {
    private final CreateOrderInput createOrderInput;
    private final GetUserByIdInput getUserByIdInput;
    private final GenerateUserActivityReportPDFInput generateUserActivityReportPDFInput;
    private final RegisterUserInput registerUserInput;
    private final LoginUserInput loginUserInput;
    private final CreateProjectInput createProjectInput;
    private final CreateTaskInput createTaskInput;
    private final GetTasksByProjectInput getTasksByProjectInput;
    private final GetProjectsInput getProjectsInput;
    public ControllerUser(CreateOrderInput createOrderInput, GetUserByIdInput getUserByIdInput, GenerateUserActivityReportPDFInput generateUserActivityReportPDFInput,
                          RegisterUserInput registerUserInput, LoginUserInput loginUserInput, CreateProjectInput createProjectInput, CreateTaskInput createTaskInput, GetTasksByProjectInput getTasksByProjectInput, GetProjectsInput getProjectsInput) {

        this.createOrderInput = createOrderInput;
        this.getUserByIdInput = getUserByIdInput;
        this.generateUserActivityReportPDFInput = generateUserActivityReportPDFInput;
        this.registerUserInput = registerUserInput;
        this.loginUserInput = loginUserInput;
        this.createProjectInput=createProjectInput;
        this.createTaskInput=createTaskInput;
        this.getTasksByProjectInput=getTasksByProjectInput;
        this.getProjectsInput = getProjectsInput;
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

    @PostMapping("/Project")
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String authorizationHeader,@RequestBody ProjectRequest request) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
           Project newProject = createProjectInput.createProject(token,request.getName(),request.getDescription(),request.getProjectStatus());

            return ResponseEntity.ok(ProjectResponse.fromDomainProject(newProject));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{projectId}/task")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request,@PathVariable Long projectId) {
        try {

            var task = createTaskInput.createTask(
                    projectId,
                    request.getEstimateHours()
                    , request.getStatus(),
                    request.getFinishedAt(),
                    request.getCreatedAt(), request.getTitle());
            return ResponseEntity.ok(TaskResponse.fromDomainTask(task));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<?> findTaskByStatus (@PathVariable Long projectId) {
        try {
            List<Task> tasks = getTasksByProjectInput.getTasksByProject(projectId);
            List<TaskResponse> response = tasks.stream().map(TaskResponse::fromDomainTask).toList();
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<?> findAllProjects(@PathVariable Long userId) {
        try {
            List<Project> projects = getProjectsInput.getProjects(userId);
            List<ProjectResponse> response = projects.stream()
                    .map(ProjectResponse::fromDomainProject)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
