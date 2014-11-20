package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.packagetool.common.SearchHelpData;

public class CustomerInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        ComplexModelInstall cmodel;
        DataElement customerid, customernm1, customernm2;
        SearchHelpData shd;
        MessagesInstall message;
        
        message = messageInstance("pt_BR");
        message.put("CUSTOMER_ID", "Cliente");
        message.put("NAME1", "Nome 1");
        message.put("NAME2", "Nome 2");
        
        customerid = elementchar("AEGIS_CUSTOMER_ID", 10, DataType.UPPERCASE);
        customernm1 = elementchar("AEGIS_CUSTOMERNM1", 35, !DataType.UPPERCASE);
        customernm2 = elementchar("AEGIS_CUSTOMERNM2", 35, !DataType.UPPERCASE);
        
        model = tag("customer_head", modelInstance(
                "AEGIS_CUSTOMER_HEAD", "AEGISCSTMRHD"));
        tag("customerid", searchhelp(model.key(
                "CUSTOMER_ID", "CSTMR", customerid), "AEGIS_CUSTOMER"));
        model.item(
                "NAME1", "NAME1", customernm1);
        model.item(
                "NAME2", "NAME2", customernm2);
        
        shd = searchHelpInstance("AEGIS_CUSTOMER", "AEGIS_CUSTOMER_HEAD");
        shd.setExport("CUSTOMER_ID");
        shd.add("CUSTOMER_ID");
        shd.add("NAME1");
        
        cmodel = cmodelInstance("AEGIS_CUSTOMER");
        cmodel.header("customer_head");
    }

}
