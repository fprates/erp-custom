package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param view
     * @param identitymodel
     * @param addressmodel
     */
    public static final void identity(ViewData view,
            DocumentModel identitymodel, DocumentModel addressmodel) {
        String name;
        DataItem dataitem;
        DataForm partnerform;
        Table addresses;
        TabbedPaneItem tab;
        Container addresscnt, container = new Form(view, "main");
        TabbedPane tabs = new TabbedPane(container, "pane");
        byte modo = Common.getMode(view);
        ExtendedObject opartner = view.getParameter("partner");
        ExtendedObject[] oaddresses = view.getParameter("addresses");
        
        partnerform = new DataForm(tabs, "identity");
        partnerform.importModel(identitymodel);
        
        for (Element element : partnerform.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            dataitem = (DataItem)element;
            name = dataitem.getName();
            
            if (name.equals("CODIGO")) {
                partnerform.get("CODIGO").setEnabled(false);
                continue;
            }
            
            if (name.equals("NOME_RAZAO"))
                view.setFocus(name);
            
            if (name.equals("TIPO_PESSOA")) {
                dataitem.setComponentType(Const.LIST_BOX);
                dataitem.add("fis", "0");
                dataitem.add("jur", "1");
            }
            
            dataitem.setEnabled((modo == Common.SHOW)? false : true);
            
        }
        
        tab = new TabbedPaneItem(tabs, "identitytab");
        tab.setContainer(partnerform);
        
        addresscnt = new StandardContainer(tabs, "addresscnt");
        addresses = new Table(addresscnt, "addresses");
        addresses.importModel(addressmodel);
        addresses.getColumn("ADDRESS_ID").setVisible(false);
        addresses.getColumn("PARTNER_ID").setVisible(false);
        addresses.setMark(true);
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        tab.setContainer(addresscnt);
        
        switch (modo) {
        case Common.CREATE:
            Common.insertItem(addresses, null);
            
            new Button(container, "save");
            
            new Button(addresscnt, "addaddress");
            new Button(addresscnt, "removeaddress");
            
            break;
        
        case Common.SHOW:
            partnerform.setObject(opartner);

            if (oaddresses != null)
                for (ExtendedObject oaddress : oaddresses)
                    Common.insertItem(addresses, oaddress);
            
            break;
            
        case Common.UPDATE:
            partnerform.setObject(opartner);
            
            if (oaddresses != null)
                for (ExtendedObject oaddress : oaddresses)
                    Common.insertItem(addresses, oaddress);
            
            new Button(addresscnt, "addaddress");
            new Button(addresscnt, "removeaddress");
            
            new Button(container, "save");
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(Common.TITLE[modo]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        DataItem partner = new DataItem(form, Const.TEXT_FIELD, "partner");
        
        partner.setModelItem(new Documents(function).getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO"));
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("partner");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("partner-selection");
    }
}
