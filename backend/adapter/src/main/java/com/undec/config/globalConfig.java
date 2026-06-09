package com.undec.config;

import input.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import output.*;
import usecase.*;

import java.time.Clock;
@Configuration
public class globalConfig {
    @Bean
    public Clock clock() {return Clock.systemDefaultZone();}

    @Bean
    public CreateOrderInput createOrderInput(OrderRepository orderRepository, UserRepository userRepository, Clock clock) {
        return new CreateOrderUseCase(orderRepository, userRepository, clock);
    }

    @Bean
    public RegisterUserInput registerUserInput(UserRepository userRepository, Clock clock, PasswordEncoderPort passwordEncoder, TokenService tokenService) {
        return new RegisterUserUseCase(userRepository, clock,passwordEncoder,tokenService);
    }
    @Bean
    public LoginUserInput loginUserInput(UserRepository userRepository,PasswordEncoderPort passwordEncoder, TokenService tokenService) {
        return new LoginUserUseCase(userRepository,passwordEncoder,tokenService);
    }


    @Bean
    public GetUserByIdInput getUserByIdInput(UserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }
    @Bean
    public ActivateUserInput activateUserInput(UserRepository userRepository, Clock clock) {
        return new ActivateUserScheduled(userRepository, clock);
    }
    @Bean
    public GenerateUserActivityReportPDFInput generateUserActivityReportPDFInput(UserRepository userRepository,
                                                                                 OrderRepository orderRepository,PdfGeneratorPort pdfGeneratorPort) {
        return new GenerateUserActivityReportPDFUseCase(userRepository, orderRepository, pdfGeneratorPort);
    }
    @Bean
    public CreateProjectInput createProjectInput(TokenService tokenService, UserRepository userRepository, ProjectRepository projectRepository) {
        return new CreateProjectUseCase(tokenService, userRepository, projectRepository);
    }
    @Bean
    public CreateTaskInput createTaskUseCase(Clock clock, ProjectRepository projectRepository , TaskRepository taskRepository) {
        return new CreateTaskUseCase(clock,projectRepository,taskRepository);
    }
}
