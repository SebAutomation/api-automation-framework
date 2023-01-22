package config;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Properties;


public class ConfigurationService {

    private Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    public Properties properties;
    private static final String RESOURCES_PATH = "./src/test/resources/";
    private String password;

    public static final String GLOBAL_CONFIG_PATH = RESOURCES_PATH + "GlobalConfig.properties";

    public void configureSsl(String propertyPath, String certificate) {
        KeyStore trust = null;

        if (certificate.equals("spid_s117043")) {
            password = properties.getProperty(GLOBAL_CONFIG_PATH, "spid_s117043password");
        } else if (certificate.equals("spid_s114557")) {
            password = properties.getProperty(GLOBAL_CONFIG_PATH, "spid_s114557password");
        } else if (certificate.equals("spid_s117557")) {
            password = properties.getProperty(GLOBAL_CONFIG_PATH, "spid_s114557password");
        } else if (certificate.equals("user")) {
            password = properties.getProperty(GLOBAL_CONFIG_PATH, "userPass");
        } else Assert.fail("Unknown certificate: " + certificate);

        String pathToCert = getProperty(propertyPath, certificate + "certPath");
        String decodedPassword = decodePassword(password);

        String trustStorePath = getProperty(propertyPath, "trustStorePath");
        String trustStorePassword = decodePassword(getProperty(propertyPath, "trustStorePassword"));

        try {
            trust = KeyStore.getInstance("JKS");

            trust.load(
                    new FileInputStream(trustStorePath),
                    trustStorePassword.toCharArray());
        } catch (Exception ex) {
            logger.error("Error while loading keystore >>>>>>", ex);
            ex.printStackTrace();
        }

        SSLConfig config = new SSLConfig()
                .keyStore(pathToCert, decodedPassword)
                .trustStore(trust)
                .and()
                .allowAllHostnames();
        RestAssured.config = RestAssured.config().sslConfig(config);
    }

    public String getProperty(String propertyPath, String key) {
        if (properties == null) {
            readProperties(propertyPath);
        }
        return properties.getProperty(key);
    }

    public void readProperties(String propertyPath) {
        try {
            File propertiesFile = new File(propertyPath);
            FileInputStream propertiesFileInputStream = new FileInputStream(propertiesFile);
            properties = new Properties();
            properties.load(propertiesFileInputStream);
        } catch (IOException e) {
            logger.error("Failure to read configuration file: {}", e);
        }
    }

    private String encodePassword(String password) {
        return new String(Base64.encodeBase64(password.getBytes()));
    }

    private String decodePassword(String encodedPassword) {
        return new String(Base64.decodeBase64(password.getBytes()));
    }

}
