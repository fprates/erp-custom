package org.erp.custom.sd.partner.common;

import java.util.Set;
import java.util.TreeSet;

public class Partner implements Comparable<Partner> {
    private Set<PartnerAddress> addresses;
    private int codigo;
    
    public Partner() {
        this.addresses = new TreeSet<PartnerAddress>();
    }
    
    /**
     * 
     * @param address
     */
    public final void add(PartnerAddress address) {
        addresses.add(address);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Partner partner) {
        return codigo - partner.getCodigo();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        Partner partner;
        
        if (object == this)
            return true;
        
        if (!(object instanceof Partner))
            return false;
        
        partner = (Partner)object;
        
        return (codigo - partner.getCodigo() == 0)? true : false;
    }
    
    /**
     * 
     * @return
     */
    public final int getCodigo() {
        return codigo;
    }
    
    /**
     * 
     * @param codigo
     */
    public final void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
