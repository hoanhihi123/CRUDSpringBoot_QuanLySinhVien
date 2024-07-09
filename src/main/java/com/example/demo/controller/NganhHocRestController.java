package com.example.demo.controller;

import com.example.demo.exceptioncustom.DuplicateValueException;
import com.example.demo.exceptioncustom.NotFoundRecordExistInDatabaseException;
import com.example.demo.entity.NganhHoc;
import com.example.demo.service.NganhHocProcedureService;
import com.example.demo.service.NganhHocService;
import com.example.demo.service.SinhVienService;
import com.example.demo.util.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    @PostMapping("/create")
    public ResponseEntity<?> taoMoiNganhHoc(@Valid @RequestBody NganhHoc nganhHoc)
    {
        try{
            if(nganhHoc==null){
                throw new NullPointerException("Ngành học đang chứa giá trị null!");
            }

            if(nganhHocService.demSoLuongNganhHoc_theoMaNganhHoc(nganhHoc.getMaNganh())>0){
                throw new DuplicateValueException("Mã ngành học " + nganhHoc.getMaNganh() + " đã tồn tại trong database!\nVui lòng nhập mã ngành khác");
            }
            NganhHoc newNganhHoc = nganhHocService.taoMoiNganhHoc(nganhHoc);
            return ResponseEntity.status(HttpStatus.CREATED).body(newNganhHoc);

        }catch (HttpMessageNotReadableException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NullPointerException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (DuplicateValueException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi TẠO ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    /*
     * Mục đích: api lấy tất cả ngành học th eo số trang truyền vào , hoặc lấy mặc định số trang xem là 0 nếu không truyền vào số trang xem
     * Input   : số trang muốn xem hiện tại
     * Output  : trả về danh sách bản ghi theo số trang truyền vào nếu hợp lệ
     *           trả về thông báo lỗi nếu xảy ra lỗi trong quá trình chạy hệ thống
     *
     * */
    @GetMapping("/getall")
    public ResponseEntity<?> layDanhSachNganhHoc(
            @RequestParam(name = "page", defaultValue = "0") int currentPage
    ){
        Pageable pageable = PageRequest.of(currentPage, Constant.NUMBER_PAGE);
        List<NganhHoc> listNganhHoc = nganhHocService.layDanhSachNganhHocVaPhanTrang(pageable).getContent();

        try{
            if(listNganhHoc.size()==0){
                return ResponseEntity.ok("Không có bản ghi nào trong Database!");
            }

            return ResponseEntity.ok(listNganhHoc);

        }catch (NullPointerException exception){
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Danh sách ngành học null!");
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình XEM danh sách ngành học!\nNguyên nhân do:"+exception.getMessage());
        }
    }

    //
    /*
     * Mục đích: api xóa ngành học theo id
     * Input   : truyền vào tham số id ngành học
     * Output  : xóa thành công đưa ra thông báo Xóa Ngành Học thành công khỏi database! với trạng thái 200
     *           hiển thị lỗi tương ứng trong quá trình thực hiện api xóa ngành học
     *
     * */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> xoaNganhHocTheoId(
            @PathVariable Integer id
    ) {
        try{
            if(id==null){
                throw new NullPointerException("Giá trị id truyền từ URL is null!");
            }

            if(sinhVienService.checkExistIdNganhHocIntableSinhVien(id)>0){
                nganhHocService.xoaMemNganhHoc_theoId(id);
                return ResponseEntity.ok("Ngành học bạn muốn xóa có trong bảng sinh viên\nHệ thống đã xóa mềm ngành học giúp bạn!");
            }else {
                Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
                if(!nganhHocOptional.isPresent()){
                    throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
                }

                nganhHocService.xoaNganhHocBangId(id);
                return ResponseEntity.ok("Xóa Ngành Học thành công khỏi database!");
            }

        }catch (NullPointerException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NotFoundRecordExistInDatabaseException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi XÓA ngành học!Nguyên nhân: " + ex.getMessage());
        }

    }

    /*
     * Mục đích: cập nhật trạng thái theo id
     * Input   : id  = id của ngành học , và Object ngành học truyền từ body
     * Output  : hiển thị ngành học vừa cập nhật thành công với trạng thái 200
     *           hiển thị lỗi tương ứng trong quá trình thực hiện api cập nhật
     *
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> capNhatNganhHoc(
            @PathVariable Integer id,
            @Valid @RequestBody NganhHoc nganhHoc
    ) {
        try{
            if(id==null){
                throw new NullPointerException("Giá trị id truyền từ url is null!");
            }

            if(nganhHoc==null){
                throw new NullPointerException("Giá trị Object Ngành Học nhận request từ Client is null!");
            }

            // check ngành học có tồn tại bản ghi ko?
            Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
            if(!nganhHocOptional.isPresent()){
                throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
            }

            // check trùng mã không ?
            if(nganhHocService.demSoLuongNganhHoc_theoMaNganhHoc(nganhHoc.getMaNganh())>0){
                throw new DuplicateValueException("Mã ngành học " + nganhHoc.getMaNganh() + " đã tồn tại trong database!\nVui lòng nhập mã ngành khác");
            }

            NganhHoc nganhHocUpdate = nganhHocOptional.get();
            nganhHocUpdate.setMaNganh(nganhHoc.getMaNganh());
            nganhHocUpdate.setTenNganh(nganhHoc.getTenNganh());
            if(nganhHoc.getIsDeleted()!=null){
                nganhHocUpdate.setIsDeleted(nganhHoc.getIsDeleted());
            }
            return ResponseEntity.ok(nganhHocService.capNhatNganhHoc(nganhHocUpdate));

        }catch (HttpMessageNotReadableException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NullPointerException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NotFoundRecordExistInDatabaseException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (DuplicateValueException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi SỬA ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    // lấy chi tiết ngành học theo id
    /*
     * Mục đích: lấy chi tiết ngành học theo id
     * Input   : id ngành học
     * Output  : hiển thị thông tin của Object ngành học tương ứng với id truyền vào với trạng thái 200
     *
     *
     * */
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> xemChiTietNganhHocTheoId(
            @PathVariable Integer id
    ){
        try{
            if(id==null){
                throw new NullPointerException("Giá trị id truyền từ URL is null!");
            }

            // check ngành học có tồn tại bản ghi ko?
            Optional<NganhHoc> nganhHocOptional = nganhHocService.layNganhHoc_theoId(id);
            if(!nganhHocOptional.isPresent()){
                throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
            }

            return ResponseEntity.ok(nganhHocOptional.get());

        }catch (NullPointerException ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi XEM CHI TIẾT ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    /*
     * Mục đích: lấy danh sách ngành học sử dụng procedure
     * Input   : không có
     * Output  : + danh sách tất cả các ngành học đã được khởi tạo và lưu trữ trong database
     *           + hiển thị lỗi nếu trong quá trình runtime xảy ra lỗi tương ứng
     *
     * */
    @GetMapping("/getList")
    public ResponseEntity<?> layDanhSachNganhHoc_procedure(){
        List<NganhHoc> listNganhHoc = nganhHocProcedureService.layDanhSachNganhHoc();

        try{
            if(listNganhHoc.size()==0){
                return ResponseEntity.ok("Không có bản ghi nào trong Database!");
            }
            return ResponseEntity.ok(listNganhHoc);

        }catch (NullPointerException exception){
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Danh sách ngành học null!");
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình XEM danh sách ngành học!\nNguyên nhân do:"+exception.getMessage());
        }
    }

}