package org.erp.custom.sd.partner;

import org.iocaste.shell.common.ViewData;

public class Common {
    public final static byte SHOW = 0;
    public final static byte CREATE = 1;
    public final static byte UPDATE = 2;
    
    public final static String[] TITLE = {
            "partner-display",
            "partner-create",
            "partner-update"
    };
    
    /**
     * 
     * @param view
     * @return
     */
    public static final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }

}
