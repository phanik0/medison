package com.medison.pacs.study.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {

    public static Specification<Study> hasPatientCode(String patientCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("pid"), patientCode);
    }

    public static Specification<Study> hasPatientName(String patientName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("pname"), "%" + patientName + "%");
    }

    public static Specification<Study> hasModality(String modality) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("modality"), modality);
    }

    public static Specification<Study> hasReportStatus(Long reportStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("reportstatus"), reportStatus);
    }

    public static Specification<Study> hasExamStatus(Long examStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("examstatus"), examStatus);
    }

    static Specification<Study> hasStudyDateBetween(String startDate, String endDate) {
        return (root, query, builder) -> builder.between(root.get("studydate"), startDate.replace("-", ""), endDate.replace("-", ""));
    }

    Study findByStudykey(Long studykey);
}
