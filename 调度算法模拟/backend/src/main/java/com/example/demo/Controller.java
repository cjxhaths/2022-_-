package com.example.demo;

import com.example.demo.algorithm.FCFS;
import com.example.demo.algorithm.HRRN;
import com.example.demo.algorithm.PSA;
import com.example.demo.algorithm.SJF;
import com.example.demo.domain.JCB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
@RestController
public class Controller {
    @PostMapping("/FCFS")
    @ResponseBody
 public Object FCFSController(@RequestBody Map<String,String> map){
        ArrayList<JCB> jcbs = new ArrayList<>();
        FCFS fcfs = new FCFS(jcbs);
        return fcfs.FCFSAlgorithm();
    }
    @PostMapping("/HRRN")
    @ResponseBody
    public Object FCFSController(@RequestBody Map<String,String> map){
        ArrayList<JCB> jcbs = new ArrayList<>();
        HRRN hrrn = new HRRN(jcbs);
        return hrrn.HRRNAlgorithm();
    }
    @PostMapping("/PSA")
    @ResponseBody
    public Object FCFSController(@RequestBody Map<String,String> map){
        ArrayList<JCB> jcbs = new ArrayList<>();
        PSA psa = new PSA(jcbs);
        return psa.PSAAlgorithm();
    }
    @PostMapping("/SJF")
    @ResponseBody
    public Object FCFSController(@RequestBody Map<String,String> map){
        ArrayList<JCB> jcbs = new ArrayList<>();
        SJF sjf = new SJF(jcbs);
        return sjf.SJFAlgorithm();
    }
}
