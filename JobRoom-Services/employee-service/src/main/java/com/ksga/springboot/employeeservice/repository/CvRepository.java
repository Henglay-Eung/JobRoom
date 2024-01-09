package com.ksga.springboot.employeeservice.repository;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface CvRepository extends JpaRepository<Cv, Integer> {

    Page<Cv> findAllByStatusIsTrueOrderByIdDesc(Pageable pageable);
    Cv findCvByIdAndStatusIsTrue(int id);
    Page<Cv> getAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update ce_curriculum_vitae set is_public = :isPublic where id = :id and status = true", nativeQuery = true)
    int updateCVPublic(@Param("isPublic") boolean isPublic, @Param("id") int id);

    @Query("select c from Cv c where c.employee.id = :id and c.status=true")
    List<Cv> getCvByUserId(@Param("id") int id);
}
