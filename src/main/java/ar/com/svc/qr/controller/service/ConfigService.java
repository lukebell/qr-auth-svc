package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.dto.ConfigDTO;

import java.util.List;

public interface ConfigService {

    List<ConfigDTO> getAllConfigs();

    ConfigDTO getConfigById(Long id);

    ConfigDTO getConfigByClientCode(String clientCode);

    ConfigDTO addConfig(ConfigDTO config);

    int updateConfig(Long id, ConfigDTO config);

    int deleteConfig(Long id);

}
