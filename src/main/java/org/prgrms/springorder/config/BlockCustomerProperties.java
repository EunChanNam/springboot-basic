package org.prgrms.springorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConstructorBinding
@ConfigurationProperties(prefix = "file.block-customer")
@Profile("dev")
public class BlockCustomerProperties {

    private final String path;

    private final String storedName;

    private final String storedExtension;

    public BlockCustomerProperties(String path, String storedName, String storedExtension) {
        this.path = path;
        this.storedName = storedName;
        this.storedExtension = storedExtension;
    }

    public String getPath() {
        return path;
    }

    public String getStoredName() {
        return storedName;
    }

    public String getStoredExtension() {
        return storedExtension;
    }
}
