package com.example.demo.controller;

import com.example.demo.exceptioncustom.NotFoundRecordExistInDatabaseException;
import com.example.demo.model.NganhHoc;
import com.example.demo.service.NganhHocProcedureService;
import com.example.demo.service.NganhHocService;
import com.example.demo.exceptioncustom.DuplicateCodeException;
import com.example.demo.service.SinhVienService;
import com.example.demo.util.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nganhHoc")
public class NganhHocRestController {
    @Autowired
    NganhHocService nganhHocService;

    @Autowired
    SinhVienService sinhVienService;

    @Autowired
    NganhHocProcedureService nganhHocProcedureService;

    // tạo 1 ngành học
    @PostMapping("/create")
    public ResponseEntity<?> createNganhHoc(@Valid @RequestBody NganhHoc nganhHoc)
    {
        try{
            if(nganhHoc==null){
                throw new NullPointerException("Ngành học đang chứa giá trị null!");
            }

            if(nganhHocService.checkExistMaNganhHoc(nganhHoc.getMaNganh())>0){
                throw new DuplicateCodeException("Mã ngành học " + nganhHoc.getMaNganh() + " đã tồn tại trong database!\nVui lòng nhập mã ngành khác");
            }
            NganhHoc newNganhHoc = nganhHocService.createNganhHoc(nganhHoc);
            return ResponseEntity.status(HttpStatus.CREATED).body(newNganhHoc);

        }catch (HttpMessageNotReadableException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NullPointerException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (DuplicateCodeException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi TẠO ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    // Lấy tất cả ngành học
    @GetMapping("/getall")
    public ResponseEntity<?> getListNganhHoc(
            @RequestParam(name = "page", defaultValue = "0") int currentPage
    ){
        Pageable pageable = PageRequest.of(currentPage, Constant.numberPage);
        List<NganhHoc> listNganhHoc = nganhHocService.getAllNganhHoc(pageable).getContent();

        try{
            if(listNganhHoc.size()==0){
                return ResponseEntity.ok("Không có bản ghi nào trong Database!");
            }

            return ResponseEntity.ok(listNganhHoc);

        }catch (NullPointerException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Danh sách ngành học null!");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình XEM danh sách ngành học!\nNguyên nhân do:"+exception.getMessage());
        }
    }

    // Xóa ngành học theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNganhHocById(
            @PathVariable Integer id
    ) {
        try{
            if(id==null){
                throw new NullPointerException("Giá trị id truyền từ URL is null!");
            }

            if(sinhVienService.checkExistIdNganhHocIntableSinhVien(id)>0){
                nganhHocService.xoaMemNganhHocById(id);
                return ResponseEntity.ok("Ngành học bạn muốn xóa có trong bảng sinh viên\nHệ thống đã xóa mềm ngành học giúp bạn!");
            }else {
                Optional<NganhHoc> nganhHocOptional = nganhHocService.getNganhHocById(id);
                if(!nganhHocOptional.isPresent()){
                    throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
                }

                nganhHocService.deleteNganhHocById(id);
                return ResponseEntity.ok("Xóa Ngành Học thành công khỏi database!");
            }

        }catch (NullPointerException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NotFoundRecordExistInDatabaseException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi XÓA ngành học!Nguyên nhân: " + ex.getMessage());
        }

    }

    // cập nhật ngành học theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNganhHoc(
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
            Optional<NganhHoc> nganhHocOptional = nganhHocService.getNganhHocById(id);
            if(!nganhHocOptional.isPresent()){
                throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
            }

            // check trùng mã không ?
            if(nganhHocService.checkExistMaNganhHoc(nganhHoc.getMaNganh())>0){
                throw new DuplicateCodeException("Mã ngành học " + nganhHoc.getMaNganh() + " đã tồn tại trong database!\nVui lòng nhập mã ngành khác");
            }
            return ResponseEntity.ok(nganhHocService.updateNganhHoc(id,nganhHoc).get());

        }catch (HttpMessageNotReadableException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NullPointerException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (NotFoundRecordExistInDatabaseException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (DuplicateCodeException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi SỬA ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    // lấy chi tiết ngành học theo id
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(
            @PathVariable Integer id
    ){
        try{
            if(id==null){
                throw new NullPointerException("Giá trị id truyền từ URL is null!");
            }

            // check ngành học có tồn tại bản ghi ko?
            Optional<NganhHoc> nganhHocOptional = nganhHocService.getNganhHocById(id);
            if(!nganhHocOptional.isPresent()){
                throw new NotFoundRecordExistInDatabaseException("Không có bản ghi nào tương ứng với id = " + id + "!");
            }

            return ResponseEntity.ok(nganhHocOptional.get());

        }catch (NullPointerException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi XEM CHI TIẾT ngành học!\nNguyên nhân do:"+ex.getMessage());
        }
    }

    // lấy danh sách ngành học sử dụng procedure
    @GetMapping("/getList")
    public ResponseEntity<?> getAllDanhSachNganhHoc(){
        List<NganhHoc> listNganhHoc = nganhHocProcedureService.layDanhSachNganhHoc();

        try{
            if(listNganhHoc.size()==0){
                return ResponseEntity.ok("Không có bản ghi nào trong Database!");
            }

            return ResponseEntity.ok(listNganhHoc);

        }catch (NullPointerException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Danh sách ngành học null!");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình XEM danh sách ngành học!\nNguyên nhân do:"+exception.getMessage());
        }
    }

}
