package com.mardenluiz.harpa.api;

import com.mardenluiz.harpa.api.config.TestAwsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
@Import(TestAwsConfig.class)
class HarpaApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
