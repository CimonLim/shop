package org.shop.admin.domain.adminuser.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Schema(description = "관리자 사용자 회원가입 요청")
public class AdminUserRegisterRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다"
    )
    @Size(max = 255, message = "이메일은 255자를 초과할 수 없습니다")
    @Schema(
            example = "admin@company.com",
            description = "관리자 이메일 아이디",
            requiredMode = RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8~20자리이며 영문, 숫자, 특수문자를 포함해야 합니다"
    )
    @Schema(
            example = "AdminPass123!",
            description = "비밀번호 (8~20자, 영문, 숫자, 특수문자 포함)",
            requiredMode = RequiredMode.REQUIRED
    )
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다")
    @Size(min = 2, max = 100, message = "이름은 2자 이상 100자 이하여야 합니다")
    @Pattern(
            regexp = "^[가-힣a-zA-Z\\s]+$",
            message = "이름은 한글과 영문만 사용 가능합니다"
    )
    @Schema(
            example = "김관리",
            description = "관리자 이름",
            requiredMode = RequiredMode.REQUIRED
    )
    private String name;

    @NotNull(message = "역할은 필수 선택값입니다")
    @Schema(
            example = "admin",
            description = "역할 이름",
            requiredMode = RequiredMode.REQUIRED
    )
    private String roleName;

    @Size(max = 50, message = "부서명은 50자를 초과할 수 없습니다")
    @Schema(
            example = "IT개발팀",
            description = "소속 부서",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private String department;

    @Size(max = 50, message = "사원번호는 50자를 초과할 수 없습니다")
    @Pattern(
            regexp = "^[A-Z0-9-]+$",
            message = "사원번호는 영문 대문자와 숫자, 하이픈만 사용 가능합니다"
    )
    @Schema(
            example = "EMP-2024-001",
            description = "사원 번호",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private String employeeId;

    @Schema(
            example = "false",
            description = "MFA(다중 인증) 활성화 여부",
            requiredMode = RequiredMode.NOT_REQUIRED,
            defaultValue = "false"
    )
    private Boolean mfaEnabled = false;

    @Schema(
            example = "09:00",
            description = "접근 허용 시작 시간 (HH:mm 형식)",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private LocalTime accessStartTime;

    @Schema(
            example = "18:00",
            description = "접근 허용 종료 시간 (HH:mm 형식)",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private LocalTime accessEndTime;

    // 비밀번호 확인 필드 (DB에는 저장되지 않음)
    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다")
    @Schema(
            example = "AdminPass123!",
            description = "비밀번호 확인",
            requiredMode = RequiredMode.REQUIRED
    )
    private String passwordConfirm;

}