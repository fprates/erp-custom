package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
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
        DataElement customerid, customernm;
        SearchHelpData shd;
        
        customerid = elementchar("AEGIS_CUSTOMER_ID", 10, DataType.UPPERCASE);
        customernm = elementchar("AEGIS_CUSTOMER_NM", 35, !DataType.UPPERCASE);
        
        model = tag("customer_head", modelInstance(
                "AEGIS_CUSTOMER_HEAD", "AEGISCSTMRHD"));
        tag("customerid", searchhelp(model.key(
                "CUSTOMER_ID", "CSTMR", customerid), "AEGIS_CUSTOMER"));
        model.item(
                "NAME1", "NAME1", customernm);
        model.item(
                "NAME2", "NAME2", customernm);
        
        shd = searchHelpInstance("AEGIS_CUSTOMER", "AEGIS_CUSTOMER_HEAD");
        shd.setExport("CUSTOMER_ID");
        shd.add("CUSTOMER_ID");
        shd.add("NAME1");
        
        cmodel = cmodelInstance("AEGIS_CUSTOMER");
        cmodel.header("customer_head");
    }

}
