package ee.erik.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @ManyToOne
    private Package orderPackage;

    public Order(OrderStatus status, Package orderPackage) {
        this.status = status;
        this.orderPackage = orderPackage;
    }
}
