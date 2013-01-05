package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.TableTool;

public class Context {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "material-editor-create",
        "material-editor-show",
        "material-editor-update"
    };

    public AbstractPage function;
    public DocumentModel materialmodel, pricesmodel, promotionsmodel;
    public DocumentModel submatmodel;
    public String matid;
    public byte mode;
    public TableTool priceshelper, promotionshelper, smaterialshelper;
    public ExtendedObject material;
    public ExtendedObject[] prices, promos, submats;
    public boolean autocode;
}
