package store.mo.communityboardapi.order.service.component;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import store.mo.communityboardapi.order.dto.OrderPlacedEvent;

@Component
public class OrderEmailListener {

//    @Async
    @EventListener
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderPlaced(@NotNull OrderPlacedEvent event) {
        System.out.println("event.getUserEmail() = " + event.getUserEmail());
        System.out.println("📧 트랜잭션 후 이벤트 수신됨! 이메일 전송: " + event.getUserEmail());

    }

}
