package com.aurora.dubbo.dao.repository;

import com.aurora.dubbo.dao.entity.DataAccess;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author panda00hi
 * @date 2024.07.10
 */
public interface DataAccessRepository extends JpaRepository<DataAccess, Integer> {
}