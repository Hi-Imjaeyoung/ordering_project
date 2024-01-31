package com.example.Order.Ordering.domain;

import com.example.Order.Member.domain.Member;
import com.example.Order.OrderItem.domain.OrderItem;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false, name ="member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status OrderStatus;

    @OneToMany(mappedBy = "ordering",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    // Setter를 사용하지 않고 구현 ^^
    public Ordering(Member member, List<OrderItem> orderItems){
        this.member = member;
        this.orderItems = new ArrayList<>();
        this.OrderStatus=Status.ORDERED;
        for(OrderItem orderItem:orderItems){
            this.orderItems.add(OrderItem.builder()
                            .item(orderItem.getItem())
                            .quantity(orderItem.getQuantity())
                            .ordering(this)
                    .build());
        }
    }
    public void cancelOrder(){
        OrderStatus = Status.CANCELED;
    }
}
