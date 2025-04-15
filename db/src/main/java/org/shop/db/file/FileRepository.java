package org.shop.db.file;

import org.shop.db.file.enums.EntityType;
import org.shop.db.file.enums.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    // 특정 엔티티의 모든 파일 조회 (정렬 순서대로)
    List<FileEntity> findAllByEntityTypeAndEntityIdOrderByDisplayOrderAsc(
            EntityType entityType, UUID entityId);

    // 특정 엔티티의 특정 타입 파일들 조회
    List<FileEntity> findAllByEntityTypeAndEntityIdAndFileType(
            EntityType entityType, UUID entityId, FileType fileType);

    // 특정 엔티티의 대표 파일 조회
    Optional<FileEntity> findByEntityTypeAndEntityIdAndIsMainTrue(
            EntityType entityType, UUID entityId);

    // 파일 타입별 조회 (페이징)
    Page<FileEntity> findAllByFileType(FileType fileType, Pageable pageable);

    // 특정 엔티티의 파일 수 조회
    long countByEntityTypeAndEntityId(EntityType entityType, UUID entityId);

    // 특정 엔티티의 특정 타입 파일 수 조회
    long countByEntityTypeAndEntityIdAndFileType(
            EntityType entityType, UUID entityId, FileType fileType);

    // 대표 파일 변경을 위한 기존 대표 파일 해제
    @Modifying
    @Query("UPDATE FileEntity f SET f.isMain = false " +
            "WHERE f.entityType = :entityType AND f.entityId = :entityId AND f.isMain = true")
    void unsetMainFile(EntityType entityType, UUID entityId);

    // 특정 URL을 사용하는 파일 존재 여부 확인
    boolean existsByFileUrl(String fileUrl);

    // 특정 엔티티의 파일들 삭제
    void deleteAllByEntityTypeAndEntityId(EntityType entityType, UUID entityId);

    // 특정 엔티티의 특정 타입 파일들 삭제
    void deleteAllByEntityTypeAndEntityIdAndFileType(
            EntityType entityType, UUID entityId, FileType fileType);
}
