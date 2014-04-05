package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;

public class InstallContext {

    public InstallData data;
    public DocumentModel partnertype;
    public DocumentModel partner;
    public DocumentModel addresstype;
    public DocumentModel address;
    public DocumentModel contact;
    public DocumentModel communication;
    public DocumentModelItem region;
    
    public InstallContext() {
        data = new InstallData();
    }
}
