package org.shop.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public abstract class BaseEntityUuid extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT uuid_generate_v4()", name = "id", updatable = false, nullable = false)
    private UUID id;

}
