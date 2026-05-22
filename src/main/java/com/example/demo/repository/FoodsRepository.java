package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Foods;

public interface FoodsRepository extends JpaRepository<Foods, Integer> {

	List<Foods> findByCategoryId(Integer categoryId);

	List<Foods> findByCategoryIdAndUsersIdAndLimitdateLessThanEqual(Integer categoryId, Integer usersId,
			LocalDate threeDaysLater);

	List<Foods> findByUsersIdAndLimitdateLessThanEqual(Integer usersId,
			LocalDate threeDaysLater);

}
