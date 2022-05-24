package com.example.demo;

import com.example.demo.algorithm.FCFS;
import com.example.demo.algorithm.HRRN;
import com.example.demo.algorithm.SJF;
import com.example.demo.domain.JCB;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class Controller {
    ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/FCFS")
    @ResponseBody
    public String dealFCFS(@RequestBody String json) throws JsonProcessingException {
        ArrayList<JCB> jcbs = makeJcb(json);
        FCFS fcfs = new FCFS(jcbs);
        ArrayList<JCB> ans = fcfs.FCFSAlgorithm();
        return objectMapper.writeValueAsString(ans);
    }
    @PostMapping("/HRRN")
    @ResponseBody
    public String dealHRRN(@RequestBody String json) throws JsonProcessingException {
        ArrayList<JCB> jcbs = makeJcb(json);
        HRRN hrrn = new HRRN(jcbs);
        ArrayList<JCB> ans = hrrn.HRRNAlgorithm();
        return objectMapper.writeValueAsString(ans);
    }
    @PostMapping("/SJF")
    @ResponseBody
    public String dealSJF(@RequestBody String json) throws JsonProcessingException {
        ArrayList<JCB> jcbs = makeJcb(json);
        SJF sjf = new SJF(jcbs);
        ArrayList<JCB> ans = sjf.SJFAlgorithm();
        return objectMapper.writeValueAsString(ans);
    }
    private ArrayList<JCB> makeJcb(String json) throws JsonProcessingException {
        ArrayList<JCB> jcbs = objectMapper.readValue(json, new TypeReference<ArrayList<JCB>>() {});
        return jcbs;
    }
}
