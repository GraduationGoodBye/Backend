package com.ggb.graduationgoodbye.global.test;


import com.ggb.graduationgoodbye.global.config.mybatis.MyBatisConfig;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@MybatisTest
@Import(MyBatisConfig.class)
abstract public class MyBatisTest {

}
