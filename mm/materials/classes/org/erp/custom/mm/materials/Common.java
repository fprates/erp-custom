package org.erp.custom.mm.materials;

import org.iocaste.shell.common.ViewData;

public class Common {

    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "material-editor-create",
        "material-editor-show",
        "material-editor-update"
    };
    
    public static final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
}
