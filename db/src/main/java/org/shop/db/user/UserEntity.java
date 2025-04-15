package org.shop.db.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;
import org.shop.db.user.enums.UserRole;

import jakarta.persistence.*;
import org.shop.db.user.enums.UserStatus;


@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "users", schema = "app")
public class UserEntity extends BaseEntityUuid {


    @Column(unique = true,nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    private String address;


    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @Setter
    private UserRole role;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @Setter
    private UserStatus status;
}
