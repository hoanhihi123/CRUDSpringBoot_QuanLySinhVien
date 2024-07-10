package com.example.demo.dto.request;

import com.example.demo.util.Constant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SinhvienDTO {
    @NotBlank(message = "tên không được null hoặc để trống")
    @Size(max = 50,message = "name không được vượt quá 50 ký tự")
    private String name;
    @NotBlank(message = "số điện thoại không được null hoặc để trống")
    @Pattern(regexp = Constant.regexPhoneNumber,message = "số điện thoại không hợp lệ")
    private String phoneNumber;
    @NotNull(message = "năm sinh không được null")
    private Integer yob;
    @NotNull(message = "nganhHoc không đươc null")
    private NganhHoc nganhHoc;
    @Getter
    public static class NganhHoc {
        private Integer id;
        private String maNganh;
        private String tenNganh;
    }
}
