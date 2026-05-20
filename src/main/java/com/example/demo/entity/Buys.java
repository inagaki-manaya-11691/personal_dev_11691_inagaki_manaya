package com.example.demo.entity;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "buys")
public class Buys {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "foods_id")
	private Integer foodsid;

	@Column(name = "users_id")
	private Integer usersid;

	private Integer buyquantity;

	private Timestamp buydate;

	private String memo;

	private Timestamp buyAt;

	public Integer getId() {
		return id;
	}

	public Integer getFoodsid() {
		return foodsid;
	}

	public Integer getUsersid() {
		return usersid;
	}

	public Integer getBuyquantity() {
		return buyquantity;
	}

	public Timestamp getBuydate() {
		return buydate;
	}

	public String getMemo() {
		return memo;
	}

	public Timestamp getBuyAt() {
		return buyAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFoodsid(Integer foodsid) {
		this.foodsid = foodsid;
	}

	public void setUsersid(Integer usersid) {
		this.usersid = usersid;
	}

	public void setBuyquantity(Integer buyquantity) {
		this.buyquantity = buyquantity;
	}

	public void setBuydate(Timestamp buydate) {
		this.buydate = buydate;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setBuyAt(Timestamp buyAt) {
		this.buyAt = buyAt;
	}

}
