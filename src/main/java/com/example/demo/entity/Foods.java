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

@Entity
@Table(name = "foods")
public class Foods {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 商品ID

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories category; // カテゴリー

	@Column(name = "users_id")
	private Integer usersId; // ユーザーID

	@Column(name = "foods_name")
	private String foodname; // 食品名

	private Boolean limits; // 消費か賞味か

	@Column(name = "createat")
	private LocalDateTime createAt; // 登録日

	@Column(name = "limits_date")
	private Date limitdate; // 期限が何日か

	private Integer quantity; // 数量

	public Foods() {
	}

	public Foods(Integer usersId, Categories category, String foodname, Boolean limits, LocalDateTime createAt,
			Date limitdate,
			Integer quantity) {
		super();
		this.usersId = usersId;
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

	public Categories getCategory() {
		return category;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public String getFoodname() {
		return foodname;
	}

	public Boolean getLimits() {
		return limits;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public Date getLimitdate() {
		return limitdate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public void setLimits(Boolean limits) {
		this.limits = limits;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public void setLimitdate(Date limitdate) {
		this.limitdate = limitdate;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
