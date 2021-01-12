package com.example.demo_reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;

@SpringBootApplication
public class Demo_reviewsApplication {
    @Autowired
	private Environment env;

	/**
	 * @methodName jaegerTracer
	 * @return     io.opentracing.Tracer
	 * @decription io.opentracing.Tracer를 정의하고 Bean 등록.
	 *             SamplerConfigration, ReporterConfiguration, ServiceName 을 정의
	 */
	@Bean
	public io.opentracing.Tracer tracer() {
		SamplerConfiguration samplerConfig = new SamplerConfiguration().withType("const").withParam(1);
		ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
		return Configuration.fromEnv(env.getProperty("opentracing.jaeger.service-name")).withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
	}

	public static void main(String[] args) {
		SpringApplication.run(Demo_reviewsApplication.class, args);
	}
}
