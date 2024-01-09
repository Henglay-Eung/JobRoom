package com.ksga.springboot.employeeservice.service;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.employee.Employee;
import com.ksga.springboot.employeeservice.model.employee.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CvService {
    //TODO: get all cv
    Page<CvDto> getAllCv(Pageable pageable);

    //TODO: save cv
    CvDto saveCv(CvDto cvDto);

    //TODO: get cv by id
    CvDto getCvById(int id);

    //TODO: update cv by id
    CvDto updateCvById(int id, CvDto cvDto);

    //TODO: delete cv by id
    CvDto deleteCvById(int id);

    //TODO: search cv by name
    Page<CvDto> searchCvByName(String name, Pageable pageable);

    CvDto updateCVPublic(boolean isPublic, int id);

    List<CvDto> getCvByUserId(int id);

}
