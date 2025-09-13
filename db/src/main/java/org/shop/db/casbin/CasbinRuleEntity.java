package org.shop.db.casbin;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityInt;

import jakarta.persistence.*;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "casbin_rule", schema = "app")
public class CasbinRuleEntity extends BaseEntityInt {

    @Column(nullable = false, length = 100)
    private String ptype;

    @Column(length = 100)
    private String v0;

    @Column(length = 100)
    private String v1;

    @Column(length = 100)
    private String v2;

    @Column(length = 100)
    private String v3;

    @Column(length = 100)
    private String v4;

    @Column(length = 100)
    private String v5;
}