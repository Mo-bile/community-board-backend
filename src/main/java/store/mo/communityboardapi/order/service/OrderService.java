package store.mo.communityboardapi.order.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import store.mo.communityboardapi.order.dto.OrderPlacedEvent;

@Service
public class OrderService {
    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void placeOrder(String orderId, String userEmail) {
        System.out.println("✅ 주문 처리 완료: " + orderId);

        // 이벤트 발행 로그 추가
        System.out.println("🚀 이벤트 발행 중: " + userEmail);
        publisher.publishEvent(new OrderPlacedEvent(orderId,userEmail));

    }
}
