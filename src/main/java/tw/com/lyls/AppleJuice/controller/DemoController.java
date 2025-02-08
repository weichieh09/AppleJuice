package tw.com.lyls.AppleJuice.controller;

import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.lyls.AppleJuice.domain.mssql.Animal;
import tw.com.lyls.AppleJuice.domain.mysql.Car;
import tw.com.lyls.AppleJuice.repository.mssql.AnimalRepository;
import tw.com.lyls.AppleJuice.repository.mysql.CarRepository;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "Demo API", description = "示範 SpringDoc OpenAPI 使用方式")
public class DemoController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping
    @Operation(summary = "示範 GET 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<Map<String, Object>> demoGet() {

        Animal animal = animalRepository.findById(1L).orElse(null);
        log.info("Animal: {}", animal);

        Car car = carRepository.findById(1L).orElse(null);
        log.info("Car: {}", car);

        Map<String, Object> result = MapUtil.newHashMap();
        result.put("animal", animal);
        result.put("car", car);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    @Operation(summary = "示範 POST 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoPost() {
        log.info("DemoController.demoPost() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

    @DeleteMapping
    @Operation(summary = "示範 DELETE 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoDelete() {
        log.info("DemoController.demoDelete() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

    @PutMapping
    @Operation(summary = "示範 PUT 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoPut() {
        log.info("DemoController.demoPut() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

}
