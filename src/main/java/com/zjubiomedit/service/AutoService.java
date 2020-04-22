package com.zjubiomedit.service;

public interface AutoService {
    void autoInsertDocUser(Long count);

    void autoGenerateWeeklyAlert();

    void autoGenerateCompliance();

    void autoGenerateDailyAlert();

    void autoGenerateManagePlan();

    void autoGenerateEvaluation();

    void autoInsertPatUser(Long count);

    Long getDocCount();

    Long getPatCount();

    void autoInsertRecord(Long patientID);

    void autoInsertReferral(Long patientID);

    void autoReviewRegister();
}
