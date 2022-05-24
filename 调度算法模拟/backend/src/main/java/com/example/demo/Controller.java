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
    @PostMapping("/FCFS")
    @ResponseBody
    public String dealFCFS(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(json);
        ArrayList<JCB> jcbs = objectMapper.readValue(json, new TypeReference<ArrayList<JCB>>() {});
        for(JCB tmp : jcbs){
            System.out.println(tmp.toString());
        }
        FCFS fcfs = new FCFS(jcbs);
        ArrayList<JCB> ans = fcfs.FCFSAlgorithm();
        return objectMapper.writeValueAsString(ans);
    }
    @PostMapping("/HRRN")
    @ResponseBody
    public List<JCB> dealHRRN(@RequestBody Map<String,String> map){
        ArrayList<JCB> jcbs = new ArrayList<>();
        HRRN hrrn = new HRRN(jcbs);
        return hrrn.HRRNAlgorithm();
    }
    @PostMapping("/SJF")
    @ResponseBody
    public List<JCB> dealSJF(@RequestBody Map<String,String> map) {
        ArrayList<JCB> jcbs = new ArrayList<>();
        SJF sjf = new SJF(jcbs);
        return sjf.SJFAlgorithm();
    }
}
