package com.target.api.filemanager.process;

import com.target.api.filemanager.model.FileDetail;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessDataRequest
{
    public List<FileDetail> getFiles(String directoryName)
    {
        List<FileDetail> list= new ArrayList<>();
        File directory = new File(directoryName);
        Assert.isTrue(directory.isDirectory(),"No such directory found!!!");
        File[] listFiles = directory.listFiles();
        for (File file : listFiles)
        {
            if (file.isFile())
            {
                FileDetail record=new FileDetail();
                record.setName(file.getName());
                record.setSize(file.length());
                list.add(record);
            }
        }
        return list;
    }
}
