package org.shop.api.domain.user.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@Schema(description = "사용자 회원가입 요청")
public class UserRegisterRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다"
    )
    @Size(max = 255, message = "이메일은 255자를 초과할 수 없습니다")
    @Schema(
            example = "lsm@naver.com",
            description = "이메일 아이디",
            requiredMode = RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8~20자리이며 영문, 숫자, 특수문자를 포함해야 합니다"
    )
    @Schema(
            example = "Password123!",
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
            example = "홍길동",
            description = "사용자 이름",
            requiredMode = RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank(message = "전화번호는 필수 입력값입니다")
    @Pattern(
            regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)"
    )
    @Schema(
            example = "010-1234-5678",
            description = "전화번호",
            requiredMode = RequiredMode.REQUIRED
    )
    private String phoneNumber;

    @Size(max = 500, message = "주소는 500자를 초과할 수 없습니다")
    @Schema(
            example = "서울특별시 강남구 테헤란로 123 4층",
            description = "주소",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private String address;
}