package com.example.demo.controller;

import com.example.demo.dto.NganhHocDto;
import com.example.demo.dto.NganhHocRequestDTO;
import com.example.demo.entity.NganhHoc;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.NganhHocProcedureService;
import com.example.demo.service.NganhHocService;
import com.example.demo.service.SinhVienService;
import com.example.demo.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * Người tạo class: Hoan
 * Mục đích class : Tạo các API xử lý CRUD
 *
 * */
@RestController
@RequestMapping("/nganhHoc")
public class NganhHocRestController {

    @Autowired
    NganhHocService nganhHocService;

    @Autowired
    SinhVienService sinhVienService;

    @Autowired
    NganhHocProcedureService nganhHocProcedureService;

    /*
     * Mục đích: api thực hiện tạo ngành học
     * Input   : Object nhận từ Client NganhHoc
     * Output  : Nếu tạo ngành học thành công: trả về Object NganhHoc và trạng thái 201
     *           Nếu tạo ngành học thất bại, xảy ra lỗi: trả về lỗi tương ứng
     *
     * */
    @Operation(summary = "Tạo mới ngành học"
            , description = "Endpoint này trả về ngành học mới được tạo thành công"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST - Yêu cầu HTTP không có nội dung hoặc dữ liệu chưa phù hợp với quy định validate"),
            @ApiResponse(responseCode = "409", description = "CONFLICT - Dữ liệu chuyển từ JSON sang Java không hợp lệ hoặc Dữ liệu trùng lặp"),
            @ApiResponse(responseCode = "201", description = "CREATED - tạo mới thành công"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> taoMoiNganhHoc(
            @Valid @RequestBody NganhHocRequestDTO nganhHocRequest
    )
    {
            if(nganhHocRequest==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ResponseObject.builder()
                                .message("Object ngành học chứa giá trị null không hợp lệ!")
                                .status(HttpStatus.BAD_REQUEST)
                                .build());
            }

            if(nganhHocService.demSoLuongNganhHoc_theoMaNganhHoc(nganhHocRequest.getMaNganh())>0){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        ResponseObject.builder()
                                .message("Mã ngành học bị trùng lặp! \nVui lòng nhập mã ngành học khác!")
                                .status(HttpStatus.CONFLICT)
                                .object(null)
                                .build());
            }

            NganhHoc newNganhHoc = nganhHocService.taoMoiNganhHoc(nganhHocRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ResponseObject.builder()
                            .message("Tạo ngành học mới thành công")
                            .status(HttpStatus.CREATED)
                            .object(newNganhHoc).build()
            );
    }

    /*
     * Mục đích: api lấy tất cả ngành học th eo số trang truyền vào , hoặc lấy mặc định số trang xem là 0 nếu không truyền vào số trang xem
     * Input   : số trang muốn xem hiện tại
     * Output  : trả về danh sách bản ghi theo số trang truyền vào nếu hợp lệ
     *           trả về thông báo lỗi nếu xảy ra lỗi trong quá trình chạy hệ thống
     *
     * */
    @Operation(summary = "Lấy danh sách ngành học theo số trang muốn xem"
            , description = "Endpoint này trả về danh sách ngành học với số trang tương ứng"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "200", description = "OK - lấy dữ liệu thành công hoặc không có dữ liệu để trả về"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> layDanhSachNganhHoc(
            @RequestParam(name = "page", defaultValue = "0") int currentPage
    ){
        Pageable pageable = PageRequest.of(currentPage, Constant.NUMBER_PAGE);
        List<NganhHoc> listNganhHoc = nganhHocService.layDanhSachNganhHocVaPhanTrang(pageable).getContent();
        if(listNganhHoc.size()==0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .message("Không có bản ghi Ngành Học nào trong trang hiện tại")
                            .status(HttpStatus.OK)
                            .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Lấy danh sách ngành học thành công tại trang hiện tại")
                        .status(HttpStatus.OK)
                        .object(listNganhHoc)
                        .build());

    }


    /*
     * Mục đích: api xóa ngành học theo id
     * Input   : truyền vào tham số id ngành học
     * Output  : xóa thành công đưa ra thông báo Xóa Ngành Học thành công khỏi database! với trạng thái 200
     *           hiển thị lỗi tương ứng trong quá trình thực hiện api xóa ngành học
     *
     * */
    @Operation(summary = "Xóa ngành học theo id"
            , description = "Endpoint này trả về thông báo thành công xóa ngành học nếu xóa thành công"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "200", description = "OK - lấy dữ liệu thành công hoặc không có dữ liệu để trả về"),
            @ApiResponse(responseCode = "204", description = "NO_CONTENT - không có dữ liệu phù hợp để trả về từ Server"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST - Yêu cầu HTTP không có nội dung hoặc dữ liệu chưa " +
                    "                                           phù hợp với quy định validate"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> xoaNganhHocTheoId(
            @PathVariable Integer id
    ) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .message("Giá trị id truyền từ URL is null!")
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }

        if (sinhVienService.checkExistIdNganhHocIntableSinhVien(id) > 0) {
            nganhHocService.xoaMemNganhHoc_theoId(id);

            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .message("Ngành học bạn muốn xóa có trong bảng sinh viên\nHệ thống đã xóa mềm ngành học giúp bạn!")
                            .status(HttpStatus.OK)
                            .build());
        } else {
            Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
            if (!nganhHocOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                        ResponseObject.builder()
                                .message("Xóa Ngành Học thành công khỏi database!")
                                .status(HttpStatus.NO_CONTENT)
                                .build());
            }

            nganhHocService.xoaNganhHocBangId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .message("Xóa Ngành Học thành công khỏi database!")
                            .status(HttpStatus.OK)
                            .build());
        }
    }

    /*
     * Mục đích: cập nhật trạng thái theo id
     * Input   : id  = id của ngành học , và Object ngành học truyền từ body
     * Output  : hiển thị ngành học vừa cập nhật thành công với trạng thái 200
     *           hiển thị lỗi tương ứng trong quá trình thực hiện api cập nhật
     *
     * */
    @Operation(summary = "Cập nhật thông tin ngành học"
            , description = "Endpoint này trả về thông tin mới được cập nhật của ngành học"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "200", description = "OK - Cập nhật dữ liệu thành công"),
            @ApiResponse(responseCode = "204", description = "NO_CONTENT - Không có dữ liệu trả về từ Server"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST - Yêu cầu HTTP không có nội dung hoặc dữ liệu chưa phù hợp với quy định validate"),
            @ApiResponse(responseCode = "409", description = "CONFLICT - Dữ liệu chuyển từ JSON sang Java không hợp lệ hoặc Dữ liệu trùng lặp"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> capNhatNganhHoc(
            @PathVariable Integer id,
            @Valid @RequestBody NganhHocRequestDTO nganhHocRequest
    ) {
        if(id==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .message("Giá trị id truyền từ URL is null!")
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }

        if(nganhHocRequest==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .message("Giá trị Object Ngành Học nhận request từ Client is null!")
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }

        // check ngành học có tồn tại bản ghi ko?
        Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
        if(nganhHocOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    ResponseObject.builder()
                            .message("Không có bản ghi nào tương ứng với id = " + id + "!")
                            .status(HttpStatus.NO_CONTENT)
                            .build());
        }

        // check trùng mã không ?
        if(nganhHocService.demSoLuongNganhHoc_theoMaNganhHoc(nganhHocRequest.getMaNganh())>0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ResponseObject.builder()
                            .message("Mã ngành học " + nganhHocRequest.getMaNganh() + " đã tồn tại trong database!\nVui lòng nhập mã ngành khác")
                            .status(HttpStatus.CONFLICT)
                            .build());
        }

        NganhHoc nganhHocUpdate = nganhHocOptional.get();
        nganhHocUpdate.setMaNganh(nganhHocRequest.getMaNganh());
        nganhHocUpdate.setTenNganh(nganhHocRequest.getTenNganh());
        if(nganhHocRequest.getIsDeleted()!=null) {
            nganhHocUpdate.setIsDeleted(nganhHocRequest.getIsDeleted());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Cập nhật thông tin ngành học thành công")
                        .status(HttpStatus.OK)
                        .object(nganhHocService.capNhatNganhHoc(nganhHocUpdate))
                        .build());
    }

    /*
     * Mục đích: lấy chi tiết ngành học theo id
     * Input   : id ngành học
     * Output  : hiển thị thông tin của Object ngành học tương ứng với id truyền vào với trạng thái 200
     *
     * */
    @Operation(summary = "Xem chi tiết ngành học", description = "Endpoint này trả về thông tin chi tiết ngành học theo id"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "200", description = "OK - Xem chi tiết ngành học theo id truyền vào thành công"),
            @ApiResponse(responseCode = "204", description = "NO_CONTENT - Không có dữ liệu trả về từ Server"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST - Yêu cầu HTTP không có nội dung hoặc dữ liệu chưa phù hợp với quy định validate"),
            @ApiResponse(responseCode = "409", description = "CONFLICT - Dữ liệu chuyển từ JSON sang Java không hợp lệ hoặc Dữ liệu trùng lặp"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseObject> xemChiTietNganhHocTheoId(
            @PathVariable Integer id
    ){
        if(id==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .message("Giá trị id truyền từ URL is null!")
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }

        // check ngành học có tồn tại bản ghi ko?
        Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
        if(nganhHocOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    ResponseObject.builder()
                            .message("Không có bản ghi nào tương ứng với id = " + id + "!")
                            .status(HttpStatus.NO_CONTENT)
                            .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Xem dữ liệu ngành học chi tiết thành công")
                        .status(HttpStatus.OK)
                        .object(nganhHocOptional.get())
                        .build());


    }

    /*
     * Mục đích: lấy danh sách ngành học sử dụng procedure
     * Input   : không có
     * Output  : + danh sách tất cả các ngành học đã được khởi tạo và lưu trữ trong database
     *           + hiển thị lỗi nếu trong quá trình runtime xảy ra lỗi tương ứng
     *
     * */
    @Operation(summary = "Lấy danh sách tất cả ngành học bằng procedure"
            , description = "Endpoint này trả về danh sách tất cả ngành học"
            , tags = {"Ngành Học"}
            , responses = {
            @ApiResponse(responseCode = "200", description = "OK - lấy dữ liệu thành công hoặc không có dữ liệu để trả về"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server gặp phải lỗi không mong muốn")
    })
    @GetMapping("/getList")
    public ResponseEntity<ResponseObject> layDanhSachNganhHoc_procedure(){

        List<NganhHoc> listNganhHoc = nganhHocProcedureService.layDanhSachNganhHoc();

        if(listNganhHoc.size()==0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .message("Không có bản ghi Ngành Học nào trong Database")
                            .status(HttpStatus.OK)
                            .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Lấy danh sách tất cả ngành học thành công")
                        .status(HttpStatus.OK)
                        .object(listNganhHoc)
                        .build());
    }


}

