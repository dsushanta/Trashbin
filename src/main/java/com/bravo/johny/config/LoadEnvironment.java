package com.bravo.johny.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static com.bravo.johny.utils.CommonUtils.getResourceAsFileObject;

public class LoadEnvironment {

    public static Logger LOGGER;
    protected File databaseConfigFile;
    protected DatabaseConfig DATABASE_CONFIG = new DatabaseConfig();
    protected ObjectMapper mapper;

    public void load() {

        LOGGER = LogManager.getLogger(this);

        // Read Database Connection details
        databaseConfigFile = getResourceAsFileObject(this,
                ProjectConfig.DATABASE_PROPERTIES_FILE);
        mapper = new ObjectMapper(new YAMLFactory());
        try {
            DATABASE_CONFIG = mapper.readValue(databaseConfigFile, DatabaseConfig.class);
            LOGGER.info("message from logger !!");
        } catch (JsonParseException e) {
            LOGGER.error(e.getStackTrace());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            LOGGER.error(e.getStackTrace());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("File : "+databaseConfigFile+" not found");
        }
    }
}
