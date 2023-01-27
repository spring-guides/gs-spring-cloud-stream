package com.example.stream.namesink;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NameSinkConfiguration {

	// tag::namesink[]
	@Bean
	public Consumer<Person> nameSink() {
		return person -> {
			System.out.println(person.name());
			System.out.println(person.processedTimestamp());
		};
	}
	// end::namesink[]
}
