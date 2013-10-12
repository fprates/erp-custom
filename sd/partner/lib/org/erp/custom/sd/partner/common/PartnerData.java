package org.erp.custom.sd.partner.common;

import java.io.Serializable;

public class PartnerData implements Serializable {
    private static final long serialVersionUID = 18422269705026253L;
    private String name, type;
    
    public final String getName() {
        return name;
    }
    
    public final String getType() {
        return type;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public final void setType(String type) {
        this.type = type;
    }
}
