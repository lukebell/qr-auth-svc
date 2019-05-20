package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.dto.ConfigDTO;
import ar.com.svc.qr.controller.validator.Validator;
import ar.com.svc.qr.exception.ResourceAlreadyExists;
import ar.com.svc.qr.exception.ResourceNotFound;
import ar.com.svc.qr.model.entity.Config;
import ar.com.svc.qr.model.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    Validator validator;

    @Autowired
    ConfigRepository configRepository;

    @Override
    public ConfigDTO getConfigById(Long id) {
        Config config = configRepository.findById(id);
        if (config == null) {
            LOGGER.error("Error: Configuration not found");
            throw new ResourceNotFound();
        }
        return setConfigDTO(config);
    }

    @Override
    public ConfigDTO getConfigByClientCode(String clientCode) {
        Config config = configRepository.findByClientCode(clientCode);
        if (config == null) {
            LOGGER.error("Error: Configuration not found");
            throw new ResourceNotFound();
        }
        return setConfigDTO(config);
    }

    @Override
    public List<ConfigDTO> getAllConfigs() {
        List<ConfigDTO> configDTOList = new ArrayList<>();
        List<Config> configList = configRepository.findAll();

        configList.forEach(config -> configDTOList.add(
                setConfigDTO(config)
        ));

        return configDTOList;
    }

    @Override
    public synchronized ConfigDTO addConfig(ConfigDTO configDTO) {
        if (configRepository.exists(configDTO.getClientCode())) {
            LOGGER.error("Error: Configuration already exists");
            throw new ResourceAlreadyExists();
        }
        Config config = configRepository.create(new Config(
                configDTO.getClientCode(),
                configDTO.getAuthURL(),
                configDTO.getValidateURL(),
                configDTO.getSize(),
                configDTO.getTtl(),
                configDTO.getCreatedBy()), null);

        return setConfigDTO(config);
    }

    @Override
    public synchronized int updateConfig(Long id, ConfigDTO configDTO) {
        if (!configRepository.exists(configDTO.getClientCode())) {
            LOGGER.error("Error: Configuration not found");
            throw new ResourceNotFound();
        }

        return configRepository.updateById(id, new Config(
                configDTO.getClientCode(),
                configDTO.getAuthURL(),
                configDTO.getValidateURL(),
                configDTO.getSize(),
                configDTO.getTtl()
        ));
    }

    @Override
    public int deleteConfig(Long id) {
        return configRepository.deleteById(id);
    }

    private ConfigDTO setConfigDTO(Config config) {
        return new ConfigDTO(
                config.getId(),
                config.getClientCode(),
                config.getAuthURL(),
                config.getValidateURL(),
                config.getSize(),
                config.getTtl(),
                config.getCreatedBy(),
                config.getCreatedDate());
    }
}
