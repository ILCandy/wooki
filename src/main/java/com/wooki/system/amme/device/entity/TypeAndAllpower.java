package com.wooki.system.amme.device.entity;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/12
 * Time : 上午11:38
 */
public class TypeAndAllpower {
    /**
     * 总电量
     */
    private Double allpower;
    /**
     * 类型
     */
    private Integer type;

    public Double getAllpower() {
        return allpower;
    }

    public void setAllpower(Double allpower) {
        this.allpower = allpower;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
