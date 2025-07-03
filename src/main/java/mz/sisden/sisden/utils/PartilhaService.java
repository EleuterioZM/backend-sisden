/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.Configuration;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
@Service
public class PartilhaService {

    @Value("${app.partilha.smb_url}")
    String smbUrl;
    @Value("${app.partilha.username}")
    String username;
    @Value("${app.partilha.password}")
    String password;

    private SmbFile rootSmbFile;
    private CIFSContext baseContext;
    private CIFSContext contextWithCred;

    @PostConstruct
    public void init() throws MalformedURLException, CIFSException {
        Properties prop = new Properties();
        prop.put("jcifs.smb.client.enableSMB2", "true");
        prop.put("jcifs.smb.client.disableSMB1", "false");
        prop.put("jcifs.traceResources", "true");
        Configuration config = new PropertyConfiguration(prop);
        this.baseContext = new BaseContext(config);
        this.contextWithCred = baseContext.withCredentials(new NtlmPasswordAuthenticator(smbUrl, username, password));

        this.rootSmbFile = new SmbFile(smbUrl, contextWithCred);
    }

    @PreDestroy
    public void destroy() {
        this.rootSmbFile.close();

        try {
            this.contextWithCred.close();
        } catch (CIFSException e) {
            log.error("Failed to close CIFSContext", e);
        }

        try {
            this.baseContext.close();
        } catch (CIFSException e) {
            log.error("Failed to close CIFSContext", e);
        }
    }

    public SmbFile getRelativeSmbFile(String relativePath) throws MalformedURLException, UnknownHostException, SmbException {
        Path path = Paths.get(relativePath);

        SmbFile newSmbFileDirs = new SmbFile(this.rootSmbFile, path.getParent().toString().replace("\\", "/") + "/");
        if (!newSmbFileDirs.exists()) {
            newSmbFileDirs.mkdirs();
        }

        SmbFile newSmbFile = new SmbFile(newSmbFileDirs, path.getFileName().toString().replace("\\", "/"));
        if (!newSmbFile.exists()) {
            newSmbFile.createNewFile();
        }

        return newSmbFile;
    }

}
