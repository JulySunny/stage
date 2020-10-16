package com.gabriel.stage.controller;

import com.gabriel.stage.annotation.NoRequiredLoginToken;
import com.gabriel.stage.annotation.RateLimitAnno;
import com.gabriel.stage.common.Result;
import com.gabriel.stage.service.ICarService;
import com.gabriel.stage.service.ITestService;
import com.gabriel.stage.task.WxMessageTask;
import com.gabriel.stage.vo.TestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author: Gabriel
 * @date: 2020/1/17 15:04
 * @description 测试类controller
 */
@Slf4j
@RestController
@RequestMapping("/v1/api")
@Api(tags = "测试API")
public class TestController {
    public static  ThreadLocal<Integer> tl=new ThreadLocal<>();


    @Autowired
    private ICarService iCarService;


    @Autowired
    private ITestService iTestService;


    @Autowired
    private WxMessageTask wxMessageTask;
    /**
     * Mybatis Plus Demo
     * @return
     */
    @GetMapping("/test")
    @RateLimitAnno(limitNum = 1)
    @NoRequiredLoginToken
    @ApiOperation("测试swagger方法")
    public Result<TestVO> test() throws InterruptedException {
//         Thread.sleep(5000);
        TestVO testVO=new TestVO();
        testVO.setBigDecimal(new BigDecimal(1.2262));
//        wxMessageTask.pushMessage();
        return Result.success(testVO);
    }


    /**
     * 测试ThreadLocal
     * @return
     */
    @GetMapping("/testTL")
    public String testThreadLocal(){
        log.info("测试ThreadLocal开始");
        int i = new Random().nextInt(100);
        System.out.println(i);
        tl.set(i);

        Integer target =tl.get();
        tl.remove();

        log.info("测试ThreadLocal结束");
        return target.toString();
    }

    /**
     * SQL优化测试,添加测试数据
     */
    @GetMapping("/add")
    public void createData(){
        log.info("数据插入开始");

        iCarService.createData();

        log.info("数据插入结束");
    }

    /**
     * 测试FastJson序列化器
     */
    @GetMapping("/test/json")
    public Result testJson(){
        TestVO testVO = new TestVO();
        BigDecimal value = BigDecimal.valueOf(111.222);
        testVO.setBigDecimal(value);
        return Result.success(testVO);

    }
}
