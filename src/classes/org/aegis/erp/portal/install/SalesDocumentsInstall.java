package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class SalesDocumentsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        ComplexModelInstall cmodel;
        DataElement documentnr, docitemkey, docitem, quantity, date;
        DataElement paymentcode, paymenttext, amount;
        DocumentModelItem payment, unitid, currency;
        SearchHelpData shd;
        MessagesInstall messages = messageInstance("pt_BR");

        messages.put("CURRENCY", "Moeda");
        messages.put("ITEM", "Item");
        messages.put("MATERIAL", "Material");
        messages.put("PRICE", "Preço");
        messages.put("QUANTITY", "Quantidade");
        messages.put("QUANTITY_UNIT", "Un.Quant.");
                
        paymentcode = elementnumc(
                "AEGIS_PAYMNT_CODE", 3);
        paymenttext = elementchar(
                "AEGIS_PAYMNT_TEXT", 20, !DataType.UPPERCASE);
        documentnr = elementnumc(
                "AEGIS_DOCUMENT_NUMBER", 10);
        docitemkey = elementnumc(
                "AEGIS_DOC_ITEM_KEY", 16);
        docitem = elementnumc(
                "AEGIS_DOCUMENT_ITEM", 6);
        quantity = elementdec(
                "AEGIS_QUANTITY", 15, 3);
        date = elementdate(
                "AEGIS_DATE");
        amount = elementdec(
                "AEGIS_AMOUNT", 12, 3);
        
        currency = new DummyModelItem(
                "CURRENCY", "CURRENCY_ID");
        unitid = new DummyModelItem(
                "MEASURE_UNITS", "UNIT_ID");
        
        /*
         * condições de pagamento
         */
        model = modelInstance("AEGIS_PAYMENT", "AEGISPAYMNT");
        payment = searchhelp(model.key(
                "CODE", "PAYCD", paymentcode), "AEGIS_PAYMENT");
        model.item(
                "TEXT", "PAYTX", paymenttext);
        
        insert(model, 0, "à vista");
        insert(model, 1, "10 dias");
        
        shd = searchHelpInstance("AEGIS_PAYMENT", "AEGIS_PAYMENT");
        shd.setExport("CODE");
        shd.add("CODE");
        shd.add("TEXT");
        
        /*
         * cabeçalho
         */
        model = tag("sd_head", modelInstance("AEGIS_SD_HEAD", "AEGISSDHD"));
        tag("docid", searchhelp(model.key(
                "NUMBER", "DOCID", documentnr), "AEGIS_SD_NUMBER"));
        searchhelp(model.reference(
                "CUSTOMER", "CSTMR", getItem("customerid")), "AEGIS_CUSTOMER");
        model.item(
                "DATE", "DOCDT", date);
        model.item(
                "EXPIRES", "EXPDT", date);
        searchhelp(model.reference(
                "PAYMENT", "PAYCD", payment), "AEGIS_PAYMENT");
        
        shd = searchHelpInstance("AEGIS_SD_NUMBER", "AEGIS_SD_HEAD");
        shd.setExport("NUMBER");
        shd.add("NUMBER");

        
        context.getInstallData().addNumberFactory("AEGIS_SD");
        
        /*
         * Itens
         */
        model = tag("sd_items", modelInstance("AEGIS_SD_ITEM", "AEGISSDIT"));
        model.key(
                "ITEM_KEY", "ITKEY", docitemkey);
        searchhelp(model.reference(
                "DOCUMENT", "SDDOC", getItem("docid")), "AEGIS_AD_DOCUMENTS");
        model.item(
                "ITEM", "POSNR", docitem);
        searchhelp(model.reference(
                "MATERIAL", "MATCD", getItem("materialcode")),
                "AEGIS_MATERIAL");
        model.item(
                "QUANTITY", "QNTTY", quantity);
        searchhelp(model.reference(
                "QUANTITY_UNIT", "QTUNI", unitid), "SH_MUNIT");
        model.item(
                "PRICE", "PRICE", amount);
        searchhelp(model.reference(
                "CURRENCY", "CRNCY", currency), "SH_CURR");
        
        /*
         * documento complexo
         */
        cmodel = cmodelInstance("AEGIS_SD_DOCUMENT");
        cmodel.header("sd_head");
        cmodel.item("items", "sd_items");
    }

}
