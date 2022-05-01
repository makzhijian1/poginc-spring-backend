package com.poginc.backendmaster.controller;

import com.poginc.backendmaster.entity.pog_Mock;
import com.poginc.backendmaster.repository.MockRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(description = "Default Controller - Use this to get a test response")
public class defaultController {

    @Autowired
    private MockRepository mockRepo;

    @GetMapping("/default")
    public String index() {
        return "Hello from Pog Inc!";
    }

    @GetMapping("/mock")
    public List<pog_Mock> getMockRecord() {
        return this.mockRepo.findAll();
    }

    @ApiOperation(value = "Insert a mock record for testing. pid can be left blank.")
    @PostMapping("/mock")
    public pog_Mock createMockRecord(@RequestBody pog_Mock mockRecord) {
        return this.mockRepo.save(mockRecord);

    }

}