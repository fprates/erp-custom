package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class MasterData {

    public static final void install(InstallContext context) {
        DocumentModel model;
        DocumentModelItem item, currency;
        DataElement element;
        SearchHelpData shd;
        
        /*
         * Moedas
         */
        model = context.data.getModel("CURRENCY", "CURRENCY", null);
        
        element = new DataElement("CURRENCY_ID");
        element.setType(DataType.CHAR);
        element.setLength(3);
        element.setUpcase(true);
        currency = new DocumentModelItem("CURRENCY_ID");
        currency.setTableFieldName("CURID");
        currency.setDataElement(element);
        model.add(currency);
        model.add(new DocumentModelKey(currency));

        element = new DataElement("CURRENCY_TEXT");
        element.setType(DataType.CHAR);
        element.setLength(20);
        element.setUpcase(false);
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("CURTX");
        item.setDataElement(element);
        model.add(item);
        
        context.data.addValues(model, "BRL", "Real");
        context.data.addValues(model, "USD", "Dólar Americano");
        context.data.addValues(model, "EUR", "Euro");
        context.data.addValues(model, "GBP", "Libras Esterlinas");
        context.data.addValues(model, "CAN", "Dólar Canadense");
        context.data.addValues(model, "JPY", "Yene Japonês");

        shd = new SearchHelpData("SH_CURR");
        shd.setModel("CURRENCY");
        shd.add("CURRENCY_ID");
        shd.add("TEXT");
        shd.setExport("CURRENCY_ID");
        context.data.add(shd);
        
        /*
         * Países
         */
        model = context.data.getModel("COUNTRIES", "COUNTRIES", null);
        element = new DataElement("COUNTRY_ID");
        element.setType(DataType.CHAR);
        element.setLength(2);
        element.setUpcase(true);
        item = new DocumentModelItem("COUNTRY_ID");
        item.setTableFieldName("CNTID");
        item.setDataElement(element);
        model.add(new DocumentModelKey(item));
        model.add(item);

        element = new DataElement("COUNTRY_NAME");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(true);
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("CNTNM");
        item.setDataElement(element);
        model.add(item);

        item = new DocumentModelItem("CURRENCY");
        item.setTableFieldName("CURID");
        item.setDataElement(element);
        item.setReference(currency);
        model.add(item);
        
        context.data.addValues(model, "BR", "BRASIL", "BRL");
        context.data.addValues(model, "US", "UNITED STATES OF AMERICA", "USD");
        
        shd = new SearchHelpData("SH_COUNTRY");
        shd.setModel("COUNTRIES");
        shd.add("COUNTRY_ID");
        shd.add("NAME");
        shd.setExport("COUNTRY_ID");
        context.data.add(shd);
    }
}
