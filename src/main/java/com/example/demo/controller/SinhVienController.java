package com.example.demo.controller;

import com.example.demo.dto.response.NganhHocDto;
import com.example.demo.entity.NganhHoc;
import com.example.demo.entity.SinhVien;
import com.example.demo.dto.request.SinhvienDTO;
import com.example.demo.exception.NotFoundRecordExistInDatabaseException;
import com.example.demo.repository.INganhHocRepository;
import com.example.demo.service.NganhHocService;
import com.example.demo.service.SinhVienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author hai
 * đây là controller quản lý api /students
 */
@RestController
@RequestMapping("/students")
@Validated
@Tag(name ="Sinh Viên controller")
@Slf4j
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private NganhHocService nganhHocService;
    /**
     * api lấy danh sách tất cả các sinh viên (có thể theo tên gợi ý)
     * @param name từ khoá gợi ý theo tên sinh viên
     * @return dánh sách sinh viên cần tìm,nếu danh sách trống trả về null và mã thông báo 204
     */
    @GetMapping("")
    @Operation(summary = "lấy danh sách sinh viên")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "204 Response", summary = "no data return"))}),
            @ApiResponse(responseCode = "200", description = "success", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
            examples = @ExampleObject(name = "200 Response",summary = "success",value = """
                    [
                        {
                            "id": "f80e3764-00cd-4a89-adce-43b44d9aedae",
                            "nganhHoc": {
                                "id": 1,
                                "maNganh": "UDPM",
                                "tenNganh": "Ung dung phan mem",
                                "isDeleted": null
                            },
                            "name": "hai",
                            "yob": 2002,
                            "phoneNumber": "04234"
                        },
                        {
                            "id": "df6b263a-555b-46b6-9da2-523f46df73b2",
                            "nganhHoc": {
                                "id": 2,
                                "maNganh": "PTPM",
                                "tenNganh": "Phat trien phan mem",
                                "isDeleted": null
                            },
                            "name": "nam",
                            "yob": 2004,
                            "phoneNumber": "0888247367"
                        },
                        {
                            "id": "b6bb5210-e74c-4327-9646-54aaeac77cef",
                            "nganhHoc": {
                                "id": 1,
                                "maNganh": "UDPM",
                                "tenNganh": "Ung dung phan mem",
                                "isDeleted": null
                            },
                            "name": "toan",
                            "yob": 2002,
                            "phoneNumber": "0888247366"
                        }
                    ]
                    """))})
    })
    public ResponseEntity<List<SinhVien>> getList(@RequestParam(required = false) String name){
            List<SinhVien> list ;
            if(name==null){
                list = sinhVienService.getListSinhVien();
            }else {
                list = sinhVienService.getListSinhVienByNameContaining(name);
            }
            if(list.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /**
     * api trả về sinh viên theo id sinh viên
     * @param id id của sinh viên
     * @return đối tượng sinh viên cần tìm, nếu không có trả về null và mã code 404
     */
    @Operation(summary = "lấy sinh viên theo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "not found", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "404 Response", summary = "not found"))}),
            @ApiResponse(responseCode = "200", description = "success", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "200 Response",summary = "success",value = """
                        {
                            "id": "f80e3764-00cd-4a89-adce-43b44d9aedae",
                            "nganhHoc": {
                                "id": 1,
                                "maNganh": "UDPM",
                                "tenNganh": "Ung dung phan mem",
                                "isDeleted": null
                            },
                            "name": "hai",
                            "yob": 2002,
                            "phoneNumber": "04234"
                        }
                    """))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable("id") UUID id){
        Optional<SinhVien> o = sinhVienService.getSinhVienById(id);
        if(o.isPresent()) {
            return new ResponseEntity<>(o.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * api thêm mới một sinh viên
     * @param sinhVien đối tượng sinh viên truyền vào
     * @return đối tượng sinh viên được thêm vào db
     */
    @Operation(summary = "thêm sinh viên")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "201 Response",summary = "created",value = """
                        {
                            "id": "f80e3764-00cd-4a89-adce-43b44d9aedae",
                            "nganhHoc": {
                                "id": 1,
                                "maNganh": "UDPM",
                                "tenNganh": "Ung dung phan mem",
                                "isDeleted": null
                            },
                            "name": "hai",
                            "yob": 2002,
                            "phoneNumber": "04234"
                        }
                    """))})
    })
    @PostMapping("")
    public ResponseEntity<SinhVien> add(@Valid @RequestBody SinhvienDTO sinhVien){
        SinhVien sv = sinhVienService.createSinhVien(sinhVien);
        return new ResponseEntity<>(sv,HttpStatus.CREATED);
    }

    /**
     * api xoá một sinh viên
     * @param id id đối tượng sinh viên cần xoá
     * @return mã code thông báo kết quả hành động
     */
    @Operation(summary = "xoá sinh viên theo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "204 Response", summary = "deleted"))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id){
            sinhVienService.deleteSinhVienById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * api update sinh viên
     * @param id id của đối tượng sinh viên cần update
     * @param sinhVien object sinh viên mang thông tin cần update
     * @return đói tương vừa được upate
     */
    @Operation(summary = "sửa sinh viên theo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "200 Response",summary = "success",value = """
                        {
                            "id": "f80e3764-00cd-4a89-adce-43b44d9aedae",
                            "nganhHoc": {
                                "id": 1,
                                "maNganh": "UDPM",
                                "tenNganh": "Ung dung phan mem",
                                "isDeleted": null
                            },
                            "name": "hai",
                            "yob": 2002,
                            "phoneNumber": "04234"
                        }
                    """))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<SinhVien> update(@PathVariable("id") UUID id,@Valid @NotNull @RequestBody SinhvienDTO sinhVien){
        Optional<SinhVien> optionalSinhVien = sinhVienService.getSinhVienById(id);
        Optional<NganhHoc> optionalNganhHoc = nganhHocService.layNganhHoc_theoId(sinhVien.getNganhHoc().getId());
        if(optionalSinhVien.isPresent()&&optionalNganhHoc.isPresent()){
            SinhVien sv = optionalSinhVien.get();
            sv.setName(sinhVien.getName());
            sv.setNganhHoc(optionalNganhHoc.get());
            sv.setPhoneNumber(sinhVien.getPhoneNumber());
            sv.setYob(sinhVien.getYob());
            return new ResponseEntity<>(sinhVienService.updateSinhVien(sv),HttpStatus.OK);
        }else {
            throw new NotFoundRecordExistInDatabaseException("không tìm thấy đối tượng sinh viên hoặc ngành học");
        }
    }

    /**
     * api thống kê số lượng sinh viên theo ngành học
     * @return List danh sách ngành học gồm tên ngành , mã ngành, số lượng sinh viên của ngành học này từ trước tới nay
     */
    @Operation(summary = "thống kê")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "200 Response",summary = "success",value = """
                                [
                                             {
                                                 "ten": "Ngành Du L?ch",
                                                 "ma": "MN002",
                                                 "soLuong": 0
                                             },
                                             {
                                                 "ten": "Phat trien phan mem",
                                                 "ma": "PTPM",
                                                 "soLuong": 1
                                             },
                                             {
                                                 "ten": "Ung dung phan mem",
                                                 "ma": "UDPM",
                                                 "soLuong": 2
                                             }
                                         ]
                            """))})
    })
    @GetMapping("/thong_ke")
    public ResponseEntity<List<NganhHocDto>> thongKe(){
            List<NganhHocDto> list = sinhVienService.thongKe();
            return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
