package org.othertwink.JsonShop.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.othertwink.JsonShop.view.UserDetails;
import org.othertwink.JsonShop.view.UserSummary;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({UserSummary.class, UserDetails.class})
    private Long id;
    @JsonView({UserSummary.class, UserDetails.class})
    @NotBlank(message = "Name can not be empty")
    private String name;
    private String address;
    @Column(unique = true, nullable = false)
    @JsonView({UserSummary.class, UserDetails.class})
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Invalid email")
    private String email;

}