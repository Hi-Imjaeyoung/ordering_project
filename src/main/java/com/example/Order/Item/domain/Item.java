package com.example.Order.Item.domain;

import com.example.Order.OrderItem.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String stockQuantity;

    @Column
    private String imagePath;

    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    //TODO : Entity에서 이래도 되나 참.
    public void buy(int buyCount) throws IllegalArgumentException{
        if(buyCount>Integer.parseInt(stockQuantity)){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity = String.valueOf(Integer.parseInt(stockQuantity)-2);
    }
}
