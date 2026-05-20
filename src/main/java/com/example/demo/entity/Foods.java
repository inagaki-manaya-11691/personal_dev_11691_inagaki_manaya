package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import aQute.bnd.annotation.headers.Category;

@Entity
@Table(name = "foods")
public class Foods {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 商品ID

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category; // カテゴリー

	@Column(name = "user_id")
	private Integer userId; // ユーザーID

	@Column(name = "food_name")
	private String foodname; // 食品名

	private Boolean limits; // 消費か賞味か

	@Column(name = "createat")
	private LocalDateTime createAt; // 登録日

	@Column(name = "limits_date")
	private Date limitdate; // 期限が何日か

	@Transient // 永続化対象外
	private Integer quantity; // 数量

	public Foods() {
	}

	public Foods(Category category, String foodname, Boolean limits, LocalDateTime createAt, Date limitdate,
			Integer quantity) {
		super();
		this.category = category;
		this.foodname = foodname;
		this.limits = limits;
		this.createAt = createAt;
		this.limitdate = limitdate;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public Boolean getLimits() {
		return limits;
	}

	public void setLimits(Boolean limits) {
		this.limits = limits;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(Date limitdate) {
		this.limitdate = limitdate;
	}

}
