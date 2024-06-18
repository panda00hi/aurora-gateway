package com.aurora.gateway.common;

import com.aurora.gateway.web.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * spring环境基础测试
 *
 * @author panda00hi
 * @date 2023.11.11
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public abstract class SpringBaseTest {

}
