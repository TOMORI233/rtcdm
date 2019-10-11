package com.zjubiomedit.service;

public interface PRService {
    /**
     * 根据patientid获取康复档案
     * @param patientId
     * @return
     */
    String getPRManagerInfo(String patientId);

    /**
     * 用于更新康复档案weekPlan的status
     * @param patientId
     * @param weekPlan
     * @return
     */
    String updatePRManagerInfo(String patientId, String weekPlan);

    /**
     * 根据patientId type获取某时间段的康复记录数据
     * @param type ALL BE WT WD
     * @return
     */
    String getDataRecordList(String patientId, String startTime, String endTime, String type);

    /**
     * 获取某项记录num个数据
     * @param patientId
     * @param type BE WT WD
     * @param num
     * @return
     */
    String getLastDataRecordList(String patientId, String type, int num);
    /**
     * 根据patientId exerciseId 提交康复记录数据
     * 同时检测是否存在预警，并返回message
     * @param jsonString
     * @return
     */
    String saveDataRecord(String jsonString);

}
