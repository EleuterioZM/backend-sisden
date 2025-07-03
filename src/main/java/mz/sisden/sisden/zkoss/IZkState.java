/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

public interface IZkState {
    Boolean getPopup();

    Boolean getCreate();

    Boolean getUpdate();

    Boolean getCreateOrUpdate();

    Boolean getRead();

    Boolean getReadOrUpdate();

    Boolean getMultipleSelection();

    Boolean getSingleSelection();

    Boolean getSelection();

    Boolean getVisible();

    Boolean getReadonly();

    Boolean getButtonVisible();

    Boolean getDisabled();

    void setPopup(Boolean popup);

    void setCreate(Boolean create);

    void setUpdate(Boolean update);

    void setCreateOrUpdate(Boolean createOrUpdate);

    void setRead(Boolean read);

    void setReadOrUpdate(Boolean readOrUpdate);

    void setMultipleSelection(Boolean multipleSelection);

    void setSingleSelection(Boolean singleSelection);

    void setSelection(Boolean selection);

    void setVisible(Boolean visible);

    void setReadonly(Boolean readonly);

    void setButtonVisible(Boolean buttonVisible);

    void setDisabled(Boolean disabled);
}
