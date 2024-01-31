package com.example.Order.Ordering.repository;

import com.example.Order.Ordering.domain.Ordering;
import com.example.Order.Ordering.service.OrderingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface OrderingRepo extends JpaRepository<Ordering,Long> {
    List<Ordering> findByOrderByCreatedTimeDesc();
}
