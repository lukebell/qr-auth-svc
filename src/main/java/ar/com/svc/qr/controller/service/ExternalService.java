package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.model.repository.ConfigRepository;
import ar.com.svc.qr.util.JerseyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ExternalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalService.class);

    @Autowired
    JerseyClient jerseyClient;

    @Autowired
    ConfigRepository configRepository;

}
