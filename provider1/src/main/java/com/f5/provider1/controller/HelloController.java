package com.f5.provider1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    private DiscoveryClient client;
    @Autowired(required = false)
    private Registration registration;
    private final Logger logger= LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/")
    public String home() {
        ServiceInstance instance=serviceInstance();
        String result="host:port="+instance.getUri()+","+"service_id:"+instance.getServiceId();
        logger.info(result);
        return "hello F5 home";
    }
    @RequestMapping("/helloeureka")
    @ResponseBody
    public Map<String,String> hello() {
        ServiceInstance instance=serviceInstance();
        String result="host:port="+instance.getUri()+","+"service_id:"+instance.getServiceId();
        Map<String,String> map=new HashMap<>();
        map.put("1","F5 LTM");
        map.put("2","YK LTM");
        map.put("3","F5 WAF");
        map.put("4","YK WAF");
        map.put("5","F5 DNS");
        map.put("6","YK DNS");
        logger.info(result);
        return map;
    }
    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list=client.getInstances(registration.getServiceId());
        if (list !=null && list.size()> 0) {
            for (ServiceInstance itm:list) {
                if(itm.getPort()==8090) {
                    return itm;
                }
            }
        }
        return null;
    }

}
