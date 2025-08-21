package com.romander.managementsystem.repository;

import com.romander.managementsystem.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser_Id(Long userId);
}
