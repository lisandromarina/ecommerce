package com.ecommerce.utils.imageKit;

import io.imagekit.sdk.ImageKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ImageKitConfig {
    @Value("${imagekit.credential.privatekey}")
    private String privateKey;
    @Value("${imagekit.credential.publickey}")
    private String publicKey;
    @Value("${imagekit.credential.urlEndpoint}")
    private String urlEndpoint;
    @Bean
    public ImageKit imageKit() {
        io.imagekit.sdk.config.Configuration config = new io.imagekit.sdk.config.Configuration(
                publicKey,
                privateKey,
                urlEndpoint
        );
        ImageKit.getInstance().setConfig(config);
        return ImageKit.getInstance();
    }
}
