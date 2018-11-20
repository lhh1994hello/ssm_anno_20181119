package com.atguigu.crud.bean;

import java.io.Serializable;
import java.util.Date;

public class Department implements Serializable {
 
 
	private static final long serialVersionUID = 1918612982103679923L;

	private Integer deptId;

    private String deptName;

    private Date createTime;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}