package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class DeliveryInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        ComplexModelInstall cmodel;
        DataElement deliverynumber, deliveryitem, insertdate, insertuser;
        SearchHelpData shd;
        DocumentModelItem delivery;
        
        deliverynumber = elementnumc("AEGIS_DELIVERY_NUMBER", 10);
        deliveryitem = elementnumc("AEGIS_DLVRY_ITEM_KEY", 16);
        insertdate = elementdate("AEGIS_INSERT_DATE");
        insertuser = elementchar("AEGIS_INSERT_USER", 12, DataType.UPPERCASE);
        
        /*
         * cabeçalho da entrega
         */
        model = tag("delivery_head",
                modelInstance("AEGIS_DELIVERY_HEAD", "AEGISDLVRHD"));
        delivery = searchhelp(model.key(
                "NUMBER", "DLVRY", deliverynumber), "AEGIS_DELIVERY");
        searchhelp(model.reference(
                "REFERENCE", "RFRNC", getItem("docid")), "AEGIS_SD_NUMBER");
        model.item(
                "INSERT_DATE", "INSDT", insertdate);
        model.item(
                "INSERT_USER", "INSUS", insertuser);
        
        shd = searchHelpInstance("AEGIS_DELIVERY", "AEGIS_DELIVERY_HEAD");
        shd.setExport("NUMBER");
        shd.add("NUMBER");
        
        context.getInstallData().addNumberFactory("AEGISDLVRY");
        
        /*
         * itens da entrega
         */
        model = tag("delivery_items", modelInstance(
                "AEGIS_DELIVERY_ITEM", "AEGISDLVRIT"));
        model.key(
                "ITEM_KEY", "ITKEY", deliveryitem);
        model.reference(
                "DELIVERY", "DLVRY", delivery);
        searchhelp(model.reference(
                "MATERIAL", "MATCD", getItem("materialcode")),
                "AEGIS_MATERIAL");
                
        cmodel = cmodelInstance("AEGIS_DELIVERY");
        cmodel.header("delivery_head");
        cmodel.item("items", "delivery_items");
    }

}
