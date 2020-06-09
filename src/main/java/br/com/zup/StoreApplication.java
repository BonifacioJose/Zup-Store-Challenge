package br.com.zup;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Bean
	public static JaegerTracer getTracer() {
		Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
		Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true).withSender(new Configuration.SenderConfiguration().withAgentHost("jaeger"));
		Configuration config = new Configuration("zup-challenge-store").withSampler(samplerConfig).withReporter(reporterConfig);
		return config.getTracer();
	}
}
