package com.ksga.springboot.employeeservice.repository;

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

@Repository
@RepositoryRestResource(exported = false)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Page<Employee> findAllByStatusIsTrue(Pageable pageable);
    Page<Employee> findAllByOrderByIdDesc(Pageable pageable);
    Employee findEmployeeByIdAndStatusIsTrue(int id);
    Page<Employee> findAllByFullNameContainingIgnoreCase(String name, Pageable pageable);
//    TODO: Get Employee by uuid
    @Query("select e from Employee e where e.uuid = :uuid and e.status = true")
    Employee getAllEmployeeByUuid(@Param("uuid") String uuid);
    Employee findEmployeeByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Employee e set e.status = :status where e.id = :id")
    int updateEmployeeStatus(@Param("status") boolean status, @Param("id") int id);

    Employee findEmployeeById(int id);

//    Page<Employee> findEmployeeByFullNameContainingIgnoreCase(String name, Pageable pageable);
}
