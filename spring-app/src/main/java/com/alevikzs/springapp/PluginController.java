package com.alevikzs.springapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/plugin")
@RequiredArgsConstructor
public class PluginController {

    private final PluginService pluginService;

    @PostMapping("/upload")
    public void upload(final @RequestParam(value = "file") MultipartFile multipartFile) {
        this.pluginService.upload(multipartFile);
    }

    @PostMapping("/execute")
    public Object execute(final @Valid @RequestBody ExecuteRequest request) {
        return this.pluginService.execute(request.getClassName(), request.getMethod());
    }

}
