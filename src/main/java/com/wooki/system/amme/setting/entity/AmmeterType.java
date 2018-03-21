package com.wooki.system.amme.setting.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-04
 */
@TableName("ammeter_type")
public class AmmeterType extends Model<AmmeterType> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 类别名字
     */
	private String name;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AmmeterType{" +
			"id=" + id +
			", name=" + name +
			"}";
	}
}
