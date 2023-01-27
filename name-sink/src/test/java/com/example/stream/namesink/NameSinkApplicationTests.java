package com.example.stream.namesink;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@ExtendWith(OutputCaptureExtension.class)
class NameSinkApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testNameSink(CapturedOutput output) {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
				TestChannelBinderConfiguration
						.getCompleteConfiguration(NameSinkApplication.class))
				.web(WebApplicationType.NONE)
				.run()) {

			InputDestination source = context.getBean(InputDestination.class);

			Long currentTime = new Date().getTime();
			Person person = new Person("Robert", currentTime);

			MessageConverter converter = context.getBean(CompositeMessageConverter.class);
			Map<String, Object> headers = new HashMap<>();
			headers.put("contentType", "application/json");
			MessageHeaders messageHeaders = new MessageHeaders(headers);
			Message<?> message = converter.toMessage(person, messageHeaders);

			source.send(message);

			Awaitility.await().until(output::getOut, value -> value.contains("Robert\n" + currentTime));
		}
	}
}
