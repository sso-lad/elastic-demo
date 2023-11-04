package org.el.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.el.entity.Entity;
import org.el.service.ElsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class ElsController extends BaseController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ElsService elsService;

    /**
     * 根据日期查询实体
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/findByDate.do")
    public String findByDate() throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(elsService.getSometing());
        return s;
    }

    /**
     * 对type进行分组,统计每个分组后的count和value
     * @return
     */
   @GetMapping("/findByDateAndSum.do")
   public String findByDateAndSum() throws JsonProcessingException {
       String blk = elsService.findGroupTypByDateAndSum("2023-01-01 00:00:00", "2023-10-29 00:00:00");
       return blk;
   }

    /**
     * 保存一万条测试数据
     * @return
     */
   @GetMapping("/save.do")
   public String save(){
       String save = elsService.save();
       return save;
   }

    /**
     * 使用spring-data-els自定义api构造查询条件
     * @return
     */
    @GetMapping("/findByApi.do")
    public String findByApi() throws JsonProcessingException {
        String blk = elsService.findDataByApi();
        return blk;
    }
}
