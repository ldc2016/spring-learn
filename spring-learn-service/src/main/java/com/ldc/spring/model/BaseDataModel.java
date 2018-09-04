package com.ldc.spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * created by liudacheng on 2018/9/4.
 */
@NoArgsConstructor
@Getter
@Setter
public class BaseDataModel {

    private String creator;
    private String updater;
    private Date createTime;
    private Date updateTime;
    private int version;

}
