package com.domaciproizvodi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domaciproizvodi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
