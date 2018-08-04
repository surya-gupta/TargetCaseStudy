package com.target.api.filemanager.model;

import lombok.Data;

@Data
public class FileDetail
{
    private String name;
    private long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
