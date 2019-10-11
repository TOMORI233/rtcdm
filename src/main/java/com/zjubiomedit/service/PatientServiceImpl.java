package com.zjubiomedit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.dao.Register.DoctorLoginRepository;
import com.zjubiomedit.dao.Register.HospitalsRepository;
import com.zjubiomedit.dao.Register.ProvincesRepository;
import com.zjubiomedit.dao.patient.AdmissionDao;
import com.zjubiomedit.dao.patient.BodySignDao;
import com.zjubiomedit.dao.patient.PatientInfoDao;
import com.zjubiomedit.dao.patient.PatientLoginDao;
import com.zjubiomedit.domain.patient.*;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientLoginDao patientLoginDao;
    @Autowired
    PatientLoginInfo loginInfo;
    @Autowired
    PatientInfoDao patientDao;
    @Autowired
    BodySignDao bodySignDao;
    @Autowired
    AdmissionDao admissionDao;
    @Autowired
    ProvincesRepository provincesRepository;
    @Autowired
    HospitalsRepository hospitalsRepository;
    @Autowired
    DoctorLoginRepository doctorLoginRepository;

    @Override
    public String doLogin(String patientId, String password) {
        Optional<PatientLogin> optional = patientLoginDao.findByPatientIdentifier(patientId);
        if (optional.isPresent()) {
            PatientLogin patientLogin = optional.get();
            if (patientLogin.getIsDisabled() == 0) {
                if (patientLogin.getPassword().equals(password)) {
                    loginInfo.getInfo(patientId);
                    Map<String, Object> map = new HashMap<>();
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                    map.put("flag", Utils.SUCCESS);
                    map.put("loginUserInfo", loginInfo);
                    return gson.toJson(map);
                }

                else {
                    return jsonFlag(Utils.FAILURE);
                }
            } else {
                return jsonFlag(Utils.DISABLED);
            }
        } else {
            return jsonFlag(Utils.FAILURE);
        }
    }

    @Override
    public String validRegister(String patientId, String name) {
        Optional<PatientLogin> optional = patientLoginDao.findByPatientIdentifier(patientId);
        if (optional.isPresent()) {
            return jsonFlag(Utils.FAILURE);
        } else {
            return jsonFlag(Utils.SUCCESS);
        }
    }

    @Override
    public String doRegister(String patientId, String info) {
        JsonReader reader = new JsonReader(new StringReader(info));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();

        try {
            String patientName = object.get("patientName").getAsString();
            String identityNumber = object.get("identityCardNumber").getAsString();
            String sex = object.get("sex").getAsString();
            String education = object.get("education").getAsString();
            String profession = object.get("profession").getAsString();
            String phoneNumber = object.get("phoneNumber").getAsString();
            String address = object.get("address").getAsString();
            String password = object.get("password").getAsString();
            String height = object.get("height").getAsString();
            String weight = object.get("weight").getAsString();
            boolean smoke = object.get("smoke").getAsBoolean();
            String date = object.get("birthDate").getAsString();
            String doctor = object.get("doctor").getAsString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = format.parse(date);
            Date now = new Date();

            PatientInfo patientInfo = new PatientInfo(patientId, patientName, sex, birthDate, profession, education, identityNumber, address, phoneNumber, doctor, null, smoke);
            patientDao.save(patientInfo);

            PatientLogin login = new PatientLogin(patientId, patientName, password, identityNumber, patientName, phoneNumber, now);
            patientLoginDao.save(login);

            BodySign pHeight = new BodySign(patientId, Utils.HEIGHT, Utils.CM, height, now, now);
            BodySign pWeight = new BodySign(patientId, Utils.WEIGHT, Utils.KG, weight, now, now);
            bodySignDao.save(pWeight);
            bodySignDao.save(pHeight);

            Admission admission = new Admission(patientId, now);
            admissionDao.save(admission);

            return jsonFlag(Utils.SUCCESS);
        } catch (ParseException e) {
            e.printStackTrace();
            return jsonFlag(Utils.UNKNOWN);
        } catch (NullPointerException e){
            e.printStackTrace();
            return jsonFlag(Utils.FAILURE);
        }

    }

    private String jsonFlag(Integer flag) {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        map.put("flag", flag);
        return gson.toJson(map);
    }

    @Override
    public Object getAllProvince() {
        return provincesRepository.findAll();
    }

    @Override
    public Object getAllHospital(String province) {
        return hospitalsRepository.findAllByProvinceCode(province);
    }

    @Override
    public Object getAllDoctor(String hospital) {
        return doctorLoginRepository.findAllByHospital(hospital);
    }

//    @Override
//    public Object registerNewPatient() {
//        return null;
//    }
}
