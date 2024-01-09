package com.ksga.springboot.employeeservice.service;

import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.cvupload.CvUploadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CvUploadService {
    //TODO: get all cv-upload
    Page<CvUploadDto> getAllCvUpload(Pageable pageable);

    //TODO: save cv-upload
    CvUploadDto saveCvUpload(CvUploadDto cvUploadDto);

    //TODO: get cv-upload by id
    CvUploadDto getCvUploadById(int id);

    //TODO: update cv-upload by id
    CvUploadDto updateCvUploadById(int id, CvUploadDto cvUploadDto);

    //TODO: delete cv-upload by id
    CvUploadDto deleteCvUploadById(int id);

    //TODO: search cv-upload by name
    Page<CvUploadDto> searchCvUploadByName(String name, Pageable pageable);

    List<CvUploadDto> getCvByUserId(int id);


    CvUploadDto updateCVPublic(boolean isPublic, int id);
}
