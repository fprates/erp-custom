package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class DeliveryInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement deliverynumber, insertdate, insertuser;
        
        deliverynumber = elementnumc("AEGIS_DELIVERY_NUMBER", 10);
        insertdate = elementdate("AEGIS_INSERT_DATE");
        insertuser = elementchar("AEGIS_INSERT_USER", 12, DataType.UPPERCASE);
        
        model = modelInstance("AEGIS_DELIVERY_HEAD", "AEGISDLVRHD");
        model.key("NUMBER", "DLVRY", deliverynumber);
        model.item("INSERT_DATE", "INSDT", insertdate);
        model.item("INSERT_USER", "INSUS", insertuser);
    }

}
