package com.shsxt.crm.dto;

/**
 * Created by lp on 2018/5/2.
 */
public class ModuleDto {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked = false;// 默认为flase, 有值自动为true

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
