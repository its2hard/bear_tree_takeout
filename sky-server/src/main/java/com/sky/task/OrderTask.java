package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron = "0 * * * * ? ")//每一分钟
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());

        List<Orders> orders = orderMapper.GetByStatusAndOrderTime(Orders.PENDING_PAYMENT,LocalDateTime.now().plusMinutes(-15));
        if(orders!=null&&!orders.isEmpty()){
            orders.forEach(order->{
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }

    @Scheduled(cron = "0 0 1 * * ? ")//每天凌晨一点
    public void processDeliveryOrder(){
        log.info("处理派送中的订单");
        List<Orders> orders = orderMapper.GetByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS,LocalDateTime.now().plusMinutes(-60));
        orders.forEach(order->{
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        });
    }
}
