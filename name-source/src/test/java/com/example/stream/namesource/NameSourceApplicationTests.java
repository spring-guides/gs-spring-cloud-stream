package com.example.stream.namesource;

import org.junit.jupiter.api.Test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NameSourceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testUsageDetailSender() {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
				TestChannelBinderConfiguration
						.getCompleteConfiguration(NameSourceApplication.class))
				.web(WebApplicationType.NONE)
				.run()) {

			OutputDestination target = context.getBean(OutputDestination.class);
			Message<byte[]> sourceMessage = target.receive(10000, "processorinput");

			MessageConverter converter = context.getBean(CompositeMessageConverter.class);

			String name = (String) converter
					.fromMessage(sourceMessage, String.class);

			assertThat(name).isEqualTo("Christopher Pike");
		}
	}
}
