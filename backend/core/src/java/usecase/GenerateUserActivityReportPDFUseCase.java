package usecase;

import exception.ResourceNotFoundException;
import input.GenerateUserActivityReportPDFInput;
import model.Order;
import model.OrderStatus;
import model.User;
import output.OrderRepository;
import output.PdfGeneratorPort;
import output.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GenerateUserActivityReportPDFUseCase implements GenerateUserActivityReportPDFInput
{

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PdfGeneratorPort  pdfGeneratorPort;

    public GenerateUserActivityReportPDFUseCase(UserRepository userRepository,
                                                OrderRepository orderRepository,
                                                PdfGeneratorPort pdfGeneratorPort) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.pdfGeneratorPort = pdfGeneratorPort;
    }

    @Override
    public byte[] generateUserActivityReport(Long id) {

        User user= userRepository.findUserById(id);
        if(user==null){
            throw new ResourceNotFoundException("User not found");
        }

        List<Order> orderList=orderRepository.getOrders(id);
        LocalDateTime reportAt=LocalDateTime.now();

        int cantTotalOrders=orderList.size();

        Predicate<Order> pred= s->s.getStatus().compareTo(OrderStatus.APPROVED)==0;

        BigDecimal totalAmountOrdersAprov=orderList.stream().
                filter(pred).
                map(order -> order.getAmount()).
                reduce(BigDecimal.ZERO,BigDecimal::add);

        List<UserActivityReportResponse.OrderData> mappedOrders = orderList.stream()
                .map(o -> new UserActivityReportResponse.OrderData(
                        o.getId(),
                        o.getCreatedAt(),
                        o.getAmount(),
                        o.getStatus()))
                .collect(Collectors.toList());


        UserActivityReportResponse report= new UserActivityReportResponse(
                reportAt,
                user.getId(),
                user.getEmailUser().getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                cantTotalOrders,
                totalAmountOrdersAprov,
                mappedOrders);

        byte[] reportPdf=pdfGeneratorPort.generatePdf(report);


        return reportPdf;
    }
}
