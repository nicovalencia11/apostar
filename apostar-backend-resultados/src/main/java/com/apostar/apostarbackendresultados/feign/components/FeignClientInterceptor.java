package com.apostar.apostarbackendresultados.feign.components;

import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Override
    public void apply(RequestTemplate template) {
        String dynamicUrl = null;
        try {
            dynamicUrl = configurationService.getConfiguration(1).getEndPoint();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        template.target(dynamicUrl);
    }
}
