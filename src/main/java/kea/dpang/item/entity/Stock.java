package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity {

    @Id
    @Column(name = "stock_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @OneToOne
    @Column(name = "item_id", nullable = false)
    private Item itemId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public void decreaseQuantity(int amount) {
        if (quantity < amount) {
            throw new IllegalArgumentException("재고가 충분하지 않습니다.");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}
