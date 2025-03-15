package store.mo.communityboardapi.order.dto;

import org.springframework.context.ApplicationEvent;

public class OrderPlacedEvent {
    private final String orderId;
    private final String userEmail;

    public OrderPlacedEvent(String orderId, String userEmail) {
        this.orderId = orderId;
        this.userEmail = userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
