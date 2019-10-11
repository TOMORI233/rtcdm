package com.zjubiomedit.service;

/**
 * @Author yiiyuanliu
 * @Date 2018/4/10
 */
public interface DataService {
    /**
     * 提交数据，插入数据库
     * @param jsonString
     * @return
     */
    String commitData(String jsonString);

    /**
     * 获取患者记录数据
     * @param jsonString
     * @return
     */
    String fetchData(String jsonString);

    /**
     * 获取患者前n条记录数据
     * @param jsonString
     * @return
     */
    String latestData(String jsonString);


    /**
     * short message service author:leiyi sheng
     * 上传患者留言数据
     * @param jsonString
     * @return
     */
    String commitMessage(String jsonString);

    /**
     * 获取最近n条留言数据
     * @param jsonString
     * @return
     */
    String latestMessage(String jsonString);

    /**
     * 标记医生消息为已读
     * mark = 1
     */
    String updateMessage(String jsonString);
}
