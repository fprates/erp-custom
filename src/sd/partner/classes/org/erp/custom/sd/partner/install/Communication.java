package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class Communication {

    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item;
        DocumentModel model;
        SearchHelpData shdata;
        
        model = context.data.getModel("CUSTOM_COMMUNICATION", "COMMUNIC", null);
        
        element = new DataElement("CUSTOM_COMMUNICATION.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement("CUSTOM_COMMUNICATION.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        model.add(item);
        
        context.data.addValues(model, 0, "TELEFONE");
        context.data.addValues(model, 1, "CELULAR");
        context.data.addValues(model, 2, "FAX");
        context.data.addValues(model, 3, "E-MAIL");
        
        shdata = new SearchHelpData("SH_COMMUNICATION");
        shdata.add("CODIGO");
        shdata.add("DESCRICAO");
        shdata.setModel("CUSTOM_COMMUNICATION");
        shdata.setExport("CODIGO");
        context.data.add(shdata);
    }
}
