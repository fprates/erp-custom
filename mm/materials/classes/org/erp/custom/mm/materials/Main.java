package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void acceptprices() {
        context.priceshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void acceptpromotions() {
        context.promotionshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void acceptsubmats() {
        context.smaterialshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void addprices() {
        context.priceshelper.add();
    }

    /**
     * 
     * @param view
     */
    public final void addpromotions() {
        context.promotionshelper.add();
    }
    
    /**
     * 
     * @param view
     */
    public final void addsubmats() {
        context.smaterialshelper.add();
    }
    
    /**
     * 
     * @param view
     */
    public final void create() {
        Request.create(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void form() {
        Response.form(context);
    }
    
    @Override
    public final PageContext init(View view) {
        Documents documents = new Documents(this);
        
        context = new Context();
        context.materialmodel = documents.getModel("MATERIAL");
        context.pricesmodel = documents.getModel("PRECO_MATERIAL");
        context.promotionsmodel = documents.getModel("PROMOCAO_MATERIAL");
        context.submatmodel = documents.getModel("SUB_MATERIAL");
        context.autocode = new GlobalConfig(this).get("MATERIAL_AUTOCODE");
        
        return context;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.main(context);
    }
    
    public final void removeprices() {
        context.priceshelper.remove();
    }
    
    public final void removepromotions() {
        context.promotionshelper.remove();
    }
    
    public final void removesubmats() {
        context.smaterialshelper.remove();
    }
    
    public final void save() {
        Request.save(context);
    }
    
    public final void show() {
        context.mode = Context.SHOW;
        Request.load(context);
    }
    
    public final void update() {
        context.mode = Context.UPDATE;
        Request.load(context);
    }
    
    public final void validate() { }
}