package uk.gov.companieshouse.siccode.api.Logging;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import uk.gov.companieshouse.api.interceptor.InternalUserInterceptor;
import uk.gov.companieshouse.siccode.api.SicCodeApiApplication;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new InternalUserInterceptor(SicCodeApiApplication.APPLICATION_NAME_SPACE));
        registry.addInterceptor(new LoggingInterceptor());
    }
}
