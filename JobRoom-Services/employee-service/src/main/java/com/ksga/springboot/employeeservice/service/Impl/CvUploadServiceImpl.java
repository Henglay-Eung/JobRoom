package com.ksga.springboot.employeeservice.service.Impl;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.cvupload.CvUpload;
import com.ksga.springboot.employeeservice.model.cvupload.CvUploadDto;
import com.ksga.springboot.employeeservice.repository.CvUploadRepository;
import com.ksga.springboot.employeeservice.repository.EmployeeRepository;
import com.ksga.springboot.employeeservice.rest.message.PaginationUtils;
import com.ksga.springboot.employeeservice.service.CvUploadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Service
public class CvUploadServiceImpl implements CvUploadService {

    private CvUploadRepository cvUploadRepository;
    private final ModelMapper mapper = new ModelMapper();
    private EmployeeRepository employeeRepository;
    private EntityManager em;

    @Autowired
    public void setCvUploadRepository(CvUploadRepository cvUploadRepository) {
        this.cvUploadRepository = cvUploadRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEm(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Page<CvUploadDto> getAllCvUpload(Pageable pageable) {
        Page<CvUpload> cvUploadPage = cvUploadRepository.findAllByStatusIsTrue(pageable);
        PaginationUtils<CvUploadDto, Page<CvUpload>> paging = new PaginationUtils<>(CvUploadDto.class);
        paging.setData(cvUploadPage);

        return paging;
    }

    @Override
    public CvUploadDto saveCvUpload(CvUploadDto cvUploadDto) {
        if (employeeRepository.findEmployeeByIdAndStatusIsTrue(cvUploadDto.getEmployee().getId()) != null) {
            cvUploadDto.setStatus(true);
            cvUploadDto.setPublic(true);
            System.out.println(cvUploadDto.toString());
            CvUpload cvUpload = mapper.map(cvUploadDto, CvUpload.class);
            System.out.println(cvUpload.toString());
            CvUpload cvUploadResult = cvUploadRepository.save(cvUpload);
            return mapper.map(cvUploadResult, CvUploadDto.class);
        }
        return null;
    }

    @Override
    public CvUploadDto getCvUploadById(int id) {

        CvUpload cvUpload = cvUploadRepository.findCvUploadByIdAndStatusIsTrue(id);
        if (cvUpload != null)
            return mapper.map(cvUpload, CvUploadDto.class);
        else
            return null;
    }

    @Override
    public CvUploadDto updateCvUploadById(int id, CvUploadDto cvUploadDto) {
        CvUpload cvUpload = cvUploadRepository.findCvUploadByIdAndStatusIsTrue(id);
        System.out.println(cvUpload);
        if (cvUpload != null) {

            CvUpload cvUploadResult = mapper.map(cvUploadDto, CvUpload.class);
            cvUploadResult.setId(id);
            cvUploadResult.setStatus(cvUpload.isStatus());
            cvUploadResult.setEmployee(cvUpload.getEmployee());

            CvUpload result = cvUploadRepository.save(cvUploadResult);
            return mapper.map(result, CvUploadDto.class);
        } else {
            return null;
        }
    }

    @Override
    public CvUploadDto deleteCvUploadById(int id) {
        CvUpload cvUpload = cvUploadRepository.findCvUploadByIdAndStatusIsTrue(id);
        if (cvUpload != null) {

            cvUpload.setId(id);
            cvUpload.setStatus(false);
            CvUpload cvUploadResult = cvUploadRepository.save(cvUpload);
            return mapper.map(cvUploadResult, CvUploadDto.class);

        } else {
            return null;
        }
    }

    @Override
    public Page<CvUploadDto> searchCvUploadByName(String name, Pageable pageable) {
        Page<CvUpload> cvUploadPage = cvUploadRepository.getAllByNameContainingIgnoreCase(name, pageable);

        PaginationUtils<CvUploadDto, Page<CvUpload>> paging = new PaginationUtils<>(CvUploadDto.class);
        paging.setData(cvUploadPage);

        return paging;
    }

    @Override
    public List<CvUploadDto> getCvByUserId(int id) {
        List<CvUpload> cvUploads = cvUploadRepository.getCvByUserId(id);
        List<CvUploadDto> cvUploadDtos = new ArrayList<>();
        for (CvUpload cvUpload : cvUploads) {
            cvUploadDtos.add(mapper.map(cvUpload, CvUploadDto.class));
        }
        return cvUploadDtos;
    }

    @Override
    public CvUploadDto updateCVPublic(boolean isPublic, int id) {
        if (cvUploadRepository.updateCVPublic(isPublic, id)>0) {
            CvUpload cv = cvUploadRepository.findCvUploadByIdAndStatusIsTrue(id);
            return mapper.map(cv, CvUploadDto.class);
        }
        return null;
    }
}
