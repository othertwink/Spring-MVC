package org.othertwink.JsonShop.view.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.othertwink.JsonShop.model.entity.Order;
import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.view.UserDetails;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDTO {
    @JsonView(UserDetails.class)
    private User user;
    @JsonView(UserDetails.class)
    private List<Order> orders;
    @JsonView(UserDetails.class)
    private BigDecimal totalCost;
}
