package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "losts")
public class Losts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "foods_id")
	private Integer foodsid;

	@Column(name = "users_id")
	private Integer usersid;

	private Integer lostquantity;

	private LocalDate lostdate;

	public Integer getId() {
		return id;
	}

	public Integer getFoodsid() {
		return foodsid;
	}

	public Integer getUsersid() {
		return usersid;
	}

	public Integer getLostquantity() {
		return lostquantity;
	}

	public LocalDate getLostdate() {
		return lostdate;
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

	public void setLostquantity(Integer lostquantity) {
		this.lostquantity = lostquantity;
	}

	public void setLostdate(LocalDate lostdate) {
		this.lostdate = lostdate;
	}

}
