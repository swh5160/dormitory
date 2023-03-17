package linc.fun.dormitory.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.service.FileService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author yqlin
 * @date 2022/4/15 21:14
 * @description
 */
@RequestMapping("/api/file")
@ResponseResult
@Api(tags = "FileController", description = "文件管理")
public class FileController {
    @Resource
    private FileService service;

    @SneakyThrows
    @ApiOperation(value = "上传文件接口")
    @TokenCheck(required = false)
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }
}
