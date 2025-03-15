package store.mo.communityboardapi.order.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import store.mo.communityboardapi.ApplicationTests;

@SpringBootTest(classes = OrderService.class)
@ExtendWith(SpringExtension.class)
public class EventTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void checkBean() {
        boolean hasBean = applicationContext.containsBean("orderService");
        System.out.println("🛠 OrderService 빈 존재 여부: " + hasBean);
    }

    @Test
    public void orderEventTest() {
        // given
        String orderId = "0";
        String userEmail = "jym2013@gg.com";

        // when
        orderService.placeOrder(orderId, userEmail);

        // then
        System.out.println("테스트 완료");

    }


}
