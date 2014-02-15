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
        DocumentModelItem item, currency, countryid;
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
        countryid = new DocumentModelItem("COUNTRY_ID");
        countryid.setTableFieldName("CNTID");
        countryid.setDataElement(element);
        model.add(new DocumentModelKey(countryid));
        model.add(countryid);

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
        item.setDataElement(currency.getDataElement());
        item.setReference(currency);
        model.add(item);
        
        context.data.addValues(model, "BR", "Brasil", "BRL");
        context.data.addValues(model, "US", "United States of America", "USD");
        
        shd = new SearchHelpData("SH_COUNTRY");
        shd.setModel("COUNTRIES");
        shd.add("COUNTRY_ID");
        shd.add("NAME");
        shd.setExport("COUNTRY_ID");
        context.data.add(shd);
        
        /*
         * Regiões
         */
        model = context.data.getModel("REGION",  "REGION", null);
        element = new DataElement("REGION_KEY");
        element.setType(DataType.CHAR);
        element.setLength(4);
        element.setUpcase(true);
        item = new DocumentModelItem("REGION_ID");
        item.setTableFieldName("REGID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("COUNTRY_ID");
        item.setTableFieldName("CNTID");
        item.setDataElement(countryid.getDataElement());
        item.setReference(countryid);
        model.add(item);
        
        element = new DataElement("REGION_CODE");
        element.setType(DataType.CHAR);
        element.setLength(2);
        element.setUpcase(true);
        item = new DocumentModelItem("CODE");
        item.setTableFieldName("REGCD");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("REGION_TEXT");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(false);
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("REGTX");
        item.setDataElement(element);
        model.add(item);
        
        context.data.addValues(model, "BRAC", "BR", "AC", "Acre");
        context.data.addValues(model, "BRAL", "AL", "BR", "Alagoas");
        context.data.addValues(model, "BRAM", "AM", "BR", "Amazonas");
        context.data.addValues(model, "BRAP", "AP", "BR", "Amapá");
        context.data.addValues(model, "BRBA", "BA", "BR", "Bahia");
        context.data.addValues(model, "BRCE", "CE", "BR", "Ceará");
        context.data.addValues(model, "BRDF", "DF", "BR", "Brasília");
        context.data.addValues(model, "BRES", "ES", "BR", "Espírito Santo");
        context.data.addValues(model, "BRGO", "GO", "BR", "Goiás");
        context.data.addValues(model, "BRMA", "MA", "BR", "Maranhão");
        context.data.addValues(model, "BRMG", "MG", "BR", "Minas Gerais");
        context.data.addValues(model, "BRMS", "MS", "BR", "Mato Grosso do Sul");
        context.data.addValues(model, "BRMT", "MT", "BR", "Mato Grosso");
        context.data.addValues(model, "BRPA", "PA", "BR", "Pará");
        context.data.addValues(model, "BRPB", "PB", "BR", "Paraíba");
        context.data.addValues(model, "BRPE", "PE", "BR", "Pernambuco");
        context.data.addValues(model, "BRPI", "PI", "BR", "Piauí");
        context.data.addValues(model, "BRPR", "PR", "BR", "Paraná");
        context.data.addValues(model, "BRRJ", "RJ", "BR", "Rio de Janeiro");
        context.data.addValues(model, "BRRN", "RN", "BR", "Rio Grande do "
                + "Norte");
        context.data.addValues(model, "BRRO", "RO", "BR", "Rondônia");
        context.data.addValues(model, "BRRR", "RR", "BR", "Roraima");
        context.data.addValues(model, "BRRS", "RS", "BR", "Rio Grande do Sul");
        context.data.addValues(model, "BRSC", "SC", "BR", "Santa Catarina");
        context.data.addValues(model, "BRSE", "SE", "BR", "Sergipe");
        context.data.addValues(model, "BRSP", "SP", "BR", "São Paulo");
        context.data.addValues(model, "BRTO", "TO", "BR", "Tocantins");
    }
}
