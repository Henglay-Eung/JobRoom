package com.ksga.springboot.employeeservice.repository;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.cvupload.CvUpload;
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
public interface CvUploadRepository extends JpaRepository<CvUpload, Integer> {

    Page<CvUpload> findAllByStatusIsTrue(Pageable pageable);
    CvUpload findCvUploadByIdAndStatusIsTrue(int id);
    Page<CvUpload> getAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("select c from CvUpload c where c.employee.id = :id and c.status=true")
    List<CvUpload> getCvByUserId(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update ce_cv_uploads set is_public = :isPublic where id = :id and status = true", nativeQuery = true)
    int updateCVPublic(@Param("isPublic") boolean isPublic, @Param("id") int id);
}
