package com.target.api.filemanager.controller;

import com.target.api.filemanager.model.FileDetail;
import com.target.api.filemanager.process.ProcessDataRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/file", method = RequestMethod.POST)
public class FileAPI
{
    private static final Logger LOG = LoggerFactory.getLogger(FileAPI.class);
    @Autowired
    private ProcessDataRequest processData;
    @RequestMapping(value = "/getDetailFromDirectory", method = RequestMethod.POST)
    public @ResponseBody List<FileDetail> getDetailFromDirectory(@RequestBody HashMap<String, Object> request)
    {
        LOG.info("Process begins to get file details .... {}",request.get("dir"));
        String dir=(String ) request.get("dir");
        Assert.isTrue(dir!=null && !dir.isEmpty(),"Enter a valid directory!!!!");
        return processData.getFiles(dir);
    }
}