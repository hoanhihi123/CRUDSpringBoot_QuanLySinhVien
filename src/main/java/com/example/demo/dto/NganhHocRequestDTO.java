package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NganhHocRequestDTO {

    @NotBlank(message = "Vui lòng không để trống mã ngành học")
    @Pattern(regexp ="^MN[0-9]{1,48}$"
            , message = "Vui lòng nhập mã ngành học theo định dạng MN + số bất kỳ (0-9)")
    @Size( max = 50, message = "Mã ngành học chỉ nhận tối đa 50 ký tự")
    private String maNganh;

    @NotBlank(message = "Vui lòng không để trống tên ngành học")
    @Size( max = 300, message = "Tên ngành học chỉ nhận tối đa 300 ký tự")
    private String tenNganh;

    private Boolean isDeleted;

}
