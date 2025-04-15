package org.shop.db.file;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityLong;
import org.shop.db.file.enums.EntityType;
import org.shop.db.file.enums.FileType;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "files", schema = "app")
public class FileEntity extends BaseEntityLong {

    @Column(name = "entity_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(name = "file_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "original_name", length = 255)
    private String originalName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(length = 500)
    private String description;
}
