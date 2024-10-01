package org.othertwink.JsonShop.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.othertwink.JsonShop.model.entity.enums.OrderStatus;
import org.othertwink.JsonShop.view.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UserDetails.class)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonListConverter.class)
    @JsonView(UserDetails.class)
    @NotBlank(message = "Product list can not be empty")
    private List<String> products; // наименования позиций, в бд как json строка

    @JsonView(UserDetails.class)
    @NotBlank(message = "Invalid cost")
    private BigDecimal cost;
    private OrderStatus status;

    @NotBlank(message = "Can not make order to a ghost")
    private Long userId;

    @CreationTimestamp
    @JsonView(UserDetails.class)
    private LocalDateTime creationDate;

}
