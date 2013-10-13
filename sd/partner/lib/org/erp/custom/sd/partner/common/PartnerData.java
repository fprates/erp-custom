package org.erp.custom.sd.partner.common;

import java.io.Serializable;

public class PartnerData implements Serializable {
    private static final long serialVersionUID = 18422269705026253L;
    public static final int FIS = 0;
    public static final int JUR = 1;
    private long id;
    private String name1, name2, type;
    private int etype;
    
    public final long getId() {
        return id;
    }
    
    public final int getEntityType() {
        return etype;
    }
    
    public final String getName1() {
        return name1;
    }
    
    public final String getName2() {
        return name2;
    }
    
    public final String getType() {
        return type;
    }
    
    public final void setEntityType(int type) {
        etype = type;
    }
    
    public final void setId(long id) {
        this.id = id;
    }
    
    public final void setName1(String name) {
        this.name1 = name;
    }
    
    public final void setName2(String name) {
        this.name2 = name;
    }
    
    public final void setType(String type) {
        this.type = type;
    }
}
