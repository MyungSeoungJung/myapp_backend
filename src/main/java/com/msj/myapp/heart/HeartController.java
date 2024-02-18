package com.msj.myapp.heart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@Slf4j
@RequiredArgsConstructor  //final 객체 생성자 자동 생성 어노테이션
@RequestMapping("/com/msj/myapp/heart")
public class HeartController {
    private final HeartService heartService;

    @PostMapping
    public ResponseEntity insert(@RequestBody @Valid HeartRequestDTO heartRequestDTO) throws Exception {
        heartService.insert(heartRequestDTO);
         return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestBody @Valid HeartRequestDTO heartRequestDTO) {
        heartService.delete(heartRequestDTO);
          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
