package org.example.authservice.repository;

import org.example.authservice.shared.entity.LoginStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LoginStatisticRepository extends JpaRepository<LoginStatistic, Date> {

}
