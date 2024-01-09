package com.ksga.springboot.employeeservice.model.cv;

import com.ksga.springboot.employeeservice.model.cv.certifications.Certification;
import com.ksga.springboot.employeeservice.model.cv.education.Education;
import com.ksga.springboot.employeeservice.model.cv.experience.Experience;
import com.ksga.springboot.employeeservice.model.cv.information.Information;
import com.ksga.springboot.employeeservice.model.cv.language.Language;
import com.ksga.springboot.employeeservice.model.cv.personaldata.PersonalData;
import com.ksga.springboot.employeeservice.model.cv.reference.ReferenceCv;
import com.ksga.springboot.employeeservice.model.cv.skill.Skill;
import com.ksga.springboot.employeeservice.model.employee.EmployeeFilter;
import org.hibernate.annotations.Type;

import java.util.List;

public class CVUpdate {
    private String name;

    @Type(type = "jsonb")
    private PersonalData personalData;

    @Type(type = "jsonb")
    private Information information;

    @Type(type = "jsonb")
    private List<Education> educations;

    @Type(type = "jsonb")
    private List<Experience> experiences;

    @Type(type = "jsonb")
    private List<Certification> certifications;

    @Type(type = "jsonb")
    private List<Skill> skills;

    @Type(type = "jsonb")
    private List<Language> languages;

    @Type(type = "jsonb")
    private List<ReferenceCv> referenceCvs;

    public CVUpdate() {
    }

    public CVUpdate(String name, PersonalData personalData, Information information, List<Education> educations, List<Experience> experiences, List<Certification> certifications, List<Skill> skills, List<Language> languages, List<ReferenceCv> referenceCvs) {
        this.name = name;
        this.personalData = personalData;
        this.information = information;
        this.educations = educations;
        this.experiences = experiences;
        this.certifications = certifications;
        this.skills = skills;
        this.languages = languages;
        this.referenceCvs = referenceCvs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<ReferenceCv> getReferenceCvs() {
        return referenceCvs;
    }

    public void setReferenceCvs(List<ReferenceCv> referenceCvs) {
        this.referenceCvs = referenceCvs;
    }

    @Override
    public String toString() {
        return "CVUpdate{" +
                "name='" + name + '\'' +
                ", personalData=" + personalData +
                ", information=" + information +
                ", educations=" + educations +
                ", experiences=" + experiences +
                ", certifications=" + certifications +
                ", skills=" + skills +
                ", languages=" + languages +
                ", referenceCvs=" + referenceCvs +
                '}';
    }
}
