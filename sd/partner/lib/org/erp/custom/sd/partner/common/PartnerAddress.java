package org.erp.custom.sd.partner.common;

public class PartnerAddress implements Comparable<PartnerAddress> {
    private Partner partner;
    private int index;
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(PartnerAddress address) {
        int compare = partner.compareTo(address.getPartner());
        
        if (compare != 0)
            return compare;
        
        return index - address.getIndex();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        PartnerAddress address;
        
        if (object == this)
            return true;
        
        if (!(object instanceof PartnerAddress))
            return false;
        
        address = (PartnerAddress)object;
        if (!(partner.equals(address.getPartner())))
            return false;
        
        return ((index - address.getIndex()) == 0)? true : false;
    }
    
    /**
     * 
     * @return
     */
    public final int getIndex() {
        return index;
    }
    
    /**
     * 
     * @return
     */
    public final Partner getPartner() {
        return partner;
    }
    
    /**
     * 
     * @param index
     */
    public final void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * 
     * @param partner
     */
    public final void setPartner(Partner partner) {
        this.partner = partner;
    }

}
