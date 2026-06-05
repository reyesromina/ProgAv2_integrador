package usecase;

import exception.ResourceNotFoundException;
import model.Order;
import model.User;
import model.UserStatus;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.OrderRepository;
import output.PdfGeneratorPort;
import output.UserRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserActivityReportPDFTest {

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    PdfGeneratorPort pdfGeneratorPort;

    @Mock
    Clock clock;


    @Test
    public void GenerateUserActivityReportPDF_Success() {
        //ArgumentCaptor es una herramienta de mockito permite captar la informacion del c.u y el adapter
        //para verificar que los datos que se envian para transformarse en pdf este bien calculada
        ArgumentCaptor<UserActivityReportResponse> captor = ArgumentCaptor.forClass(UserActivityReportResponse.class);
        String code = UUID.randomUUID().toString();

        //clock
        Instant fixedInstant = Instant.parse("2026-02-14T10:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        LocalDateTime today=LocalDateTime.now(fixedClock);

        //llamado al constructor del c.u
        GenerateUserActivityReportPDFUseCase usecase= new GenerateUserActivityReportPDFUseCase(userRepository,orderRepository,pdfGeneratorPort);

        Long id=1L;
        User user = User.createUserFactory(
                "romi@gmail.com",
                "secret123",
                LocalDateTime.now(),
                code
        );
        user.activate(code,today);

        Order order1 = Order.create(user, new BigDecimal("150"), fixedClock);
        Order order2 = Order.create(user, new BigDecimal("200"), fixedClock);
        Order order3 = Order.create(user, new BigDecimal("50"), fixedClock);

        order1.process(fixedClock);
        order1.approve(fixedClock);

        order2.process(fixedClock);
        order2.approve(fixedClock);

        order3.cancel(fixedClock);

        List<Order> expectedOrders=List.of(order1, order2, order3);


        when(userRepository.findUserById(id)).thenReturn(user);
        when(orderRepository.getOrders(id)).thenReturn(expectedOrders);
        //llamado al c.u
        usecase.generateUserActivityReport(1L);

        //capturar la clase response que se envia como argumento al metodo de la interfaz PdfGeneratePort
        verify(pdfGeneratorPort).generatePdf(captor.capture());


        //asignar a una variable los datos capturados con el captor
        UserActivityReportResponse datosEnviados = captor.getValue();

        //verificaciones.
        assertEquals(new BigDecimal("350"), datosEnviados.getTotalAmount());
        assertEquals(UserStatus.ACTIVE, datosEnviados.getUserStatus());
        assertEquals(3, datosEnviados.getOrders().size());

    };


    @Test
    void GenerateUserActivityReportPDF_NotSuccess_UserNULL() {

        GenerateUserActivityReportPDFUseCase useCase= new GenerateUserActivityReportPDFUseCase
                (userRepository,orderRepository,pdfGeneratorPort);

        when(userRepository.findUserById(1L))
                .thenReturn(null);

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.generateUserActivityReport(1L)
        );

        verifyNoInteractions(pdfGeneratorPort);
    }
}

