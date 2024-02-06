package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SalesReportRepository extends JpaRepository<User, Long> {
}
