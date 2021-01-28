package com.bh.jdbc.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 创建实体类
 */
@Entity  // 表明该类 (UserEntity) 为一个实体类
// @Table该标注与 @Entity 标注并列使用
@Table(name = "branch")    //声明此对象映射到数据库的数据表，通过它可以为实体指定表(talbe)
public class Branch {
    @Id   //@Id注释指定表的主键，它可以有多种生成方式
    private Integer id; //部门id
    @Column  //Column 纵队，列
    private String branchName; //部门名称
    @Column
    private String location; //部门位置

    //get set方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
