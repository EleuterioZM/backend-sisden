/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.controller.viewer;

import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.SystemFile;
import mz.sisden.sisden.utils.Extensions;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkController;
import mz.sisden.sisden.zkoss.ZkUtils;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static mz.sisden.sisden.utils.Extensions.videos;
import static mz.sisden.sisden.zkoss.ZkArgument.SYSTEM_FILE;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.*;

@Slf4j
@ZkComponent
public class SystemFileViewerController extends ZkController {
    @Wire
    private Iframe iframeSystemFile;

    @Wire
    private Image imageSystemFile;

    @Wire
    private Label labelTitle;

    @Wire
    private A aClose, aDownloadSystemFile;

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        return super.doBeforeCompose(page, parent, compInfo);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        try {
            this.show();
        } catch (Exception e) {
            ZkAlerts.warn("Problemas ao mostrar o ficheiro!");
            ZkUtils.back(this.getComponent());
        }
    }

    public void show() throws IOException {
        SystemFile systemFile = ZkUtils.getAttribute(SYSTEM_FILE.name());

        if (isNull(systemFile)) {
            ZkAlerts.warn("Ficheiro não encontrado!");
            ZkUtils.back(this.getComponent());
            return;
        }

        byte[] data = systemFile.getByteData();
        String extension = systemFile.getExtension();
        String contentType = Extensions.all().get(extension);
        String path = systemFile.getPath();
        String fileName = systemFile.getFileName();

        if ((isNull(data) || isEmpty(data)) && isNotBlank(path)) {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            if (isEmpty(bytes)) {
                ZkAlerts.warn("O ficheiro " + fileName + " não foi encontrado para apresentar!");
                ZkUtils.back(this.getComponent());
                return;
            }
        }

        this.setTitle(fileName);

        if (equalsAnyIgnoreCase(extension, "pdf") || videos.containsKey(lowerCase(extension))) {
            AMedia aMedia = new AMedia(fileName, extension, contentType, data);
            this.iframeSystemFile.setContent(aMedia);
            this.iframeSystemFile.setVisible(true);
            this.aDownloadSystemFile.addEventListener(Events.ON_CLICK, event -> Filedownload.save(data, contentType, fileName));
        } else if (Extensions.images.containsKey(lowerCase(extension))) {
            AImage aImage = new AImage(fileName, data);
            this.imageSystemFile.setContent(aImage);
            this.imageSystemFile.setVisible(true);
            this.aDownloadSystemFile.addEventListener(Events.ON_CLICK, event -> Filedownload.save(data, contentType, fileName));
        } else {
            Filedownload.save(data, contentType, fileName);
            ZkAlerts.info("O ficheiro " + fileName + " foi descarregado, veja os seus downloads!");
            //ZkUtils.back(this.getComponent());
        }
    }

    public void setTitle(String title) {
        if (nonNull(this.labelTitle)) {
            this.labelTitle.setValue(title);
        }

        if (nonNull(this.aClose)) {
            aClose.addEventListener(Events.ON_CLICK, event -> ZkUtils.back(this.getComponent()));
        }
    }
}
