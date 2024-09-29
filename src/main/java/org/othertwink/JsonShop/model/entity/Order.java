package org.othertwink.JsonShop.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.othertwink.JsonShop.model.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonListConverter.class)
    private List<String> stringValues; // перечисление позиций, в бд как json строка

    private BigDecimal cost;
    private OrderStatus status;

}
