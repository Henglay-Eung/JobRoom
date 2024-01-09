package com.ksga.springboot.employeeservice.service.Impl;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.employee.Employee;
import com.ksga.springboot.employeeservice.model.employee.EmployeeDto;
import com.ksga.springboot.employeeservice.repository.CvRepository;
import com.ksga.springboot.employeeservice.repository.EmployeeRepository;
import com.ksga.springboot.employeeservice.rest.message.PaginationUtils;
import com.ksga.springboot.employeeservice.service.CvService;
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
public class CvServiceImpl implements CvService {
    private CvRepository cvRepository;
    private EmployeeRepository employeeRepository;
    private EntityManager em;

    @Autowired
    public void setCvRepository(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public void setEm(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Page<CvDto> getAllCv(Pageable pageable) {

        Page<Cv> cvPage = cvRepository.findAllByStatusIsTrueOrderByIdDesc(pageable);

        PaginationUtils<CvDto, Page<Cv>> paging = new PaginationUtils<>(CvDto.class);
        paging.setData(cvPage);

        return paging;
    }

    @Override
    public CvDto saveCv(CvDto cvDto) {
        cvDto.setStatus(true);
        cvDto.setPublic(true);
        if (employeeRepository.findEmployeeByIdAndStatusIsTrue(cvDto.getEmployee().getId()) != null) {
            Cv cv1 = mapper.map(cvDto, Cv.class);
            Cv cvResult = cvRepository.save(cv1);
            return mapper.map(cvResult, CvDto.class);
        }
        return null;
    }

    @Override
    public CvDto getCvById(int id) {
        Cv cv = cvRepository.findCvByIdAndStatusIsTrue(id);
        if (cv != null)
            return mapper.map(cv, CvDto.class);
        else
            return null;
    }

    @Override
    public CvDto updateCvById(int id, CvDto cvDto) {
        Cv cv = cvRepository.findCvByIdAndStatusIsTrue(id);
        if (cv != null) {

            Cv cvResult = mapper.map(cvDto, Cv.class);
            cvResult.setPublic(cv.isPublic());
            cvResult.setEmployee(cv.getEmployee());
            cvResult.setId(id);
            cvResult.setStatus(cv.isStatus());

            Cv result = cvRepository.save(cvResult);
            return mapper.map(result, CvDto.class);
        } else {
            return null;
        }
    }

    @Override
    public CvDto deleteCvById(int id) {
        Cv cv = cvRepository.findCvByIdAndStatusIsTrue(id);
        if (cv != null) {

            cv.setId(id);
            cv.setStatus(false);
            Cv cvResult = cvRepository.save(cv);
            return mapper.map(cvResult, CvDto.class);

        } else {
            return null;
        }
    }

    @Override
    public Page<CvDto> searchCvByName(String name, Pageable pageable) {
        Page<Cv> cvPage = cvRepository.getAllByNameContainingIgnoreCase(name, pageable);

        PaginationUtils<CvDto, Page<Cv>> paging = new PaginationUtils<>(CvDto.class);
        paging.setData(cvPage);

        return paging;
    }

    @Override
    public CvDto updateCVPublic(boolean isPublic, int id) {
        if (cvRepository.updateCVPublic(isPublic, id)>0) {
            Cv cv = cvRepository.findCvByIdAndStatusIsTrue(id);
            return mapper.map(cv, CvDto.class);
        }
        return null;
    }

    @Override
    public List<CvDto> getCvByUserId(int id) {
        List<Cv> cvList = cvRepository.getCvByUserId(id);
        List<CvDto> cvDtoList = new ArrayList<>();
        for (Cv reply : cvList) {
            cvDtoList.add(mapper.map(reply, CvDto.class));
        }
        return cvDtoList;
    }
}
