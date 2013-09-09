package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
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
    public final void acceptprices(View view) {
        context.priceshelper.refresh(view);
        context.priceshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void acceptpromotions(View view) {
        context.promotionshelper.refresh(view);
        context.promotionshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void acceptsubmats(View view) {
        context.smaterialshelper.refresh(view);
        context.smaterialshelper.accept();
    }
    
    /**
     * 
     * @param view
     */
    public final void addprices(View view) {
        context.priceshelper.refresh(view);
        context.priceshelper.add();
    }

    /**
     * 
     * @param view
     */
    public final void addpromotions(View view) {
        context.promotionshelper.refresh(view);
        context.promotionshelper.add();
    }
    
    /**
     * 
     * @param view
     */
    public final void addsubmats(View view) {
        context.smaterialshelper.refresh(view);
        context.smaterialshelper.add();
    }
    
    /**
     * 
     * @param view
     */
    public final void create(View view) {
        Request.create(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void form(View view) {
        Response.form(view, context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    public final void init(View view) {
        Documents documents;
        
        if (!view.getPageName().equals("main"))
            return;
        
        documents = new Documents(this);
        context = new Context();
        context.materialmodel = documents.getModel("MATERIAL");
        context.pricesmodel = documents.getModel("PRECO_MATERIAL");
        context.promotionsmodel = documents.getModel("PROMOCAO_MATERIAL");
        context.submatmodel = documents.getModel("SUB_MATERIAL");
        context.function = this;
        context.autocode = new GlobalConfig(this).get("MATERIAL_AUTOCODE");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Response.main(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void removeprices(View view) {
        context.priceshelper.refresh(view);
        context.priceshelper.remove();
    }
    
    /**
     * 
     * @param view
     */
    public final void removepromotions(View view) {
        context.promotionshelper.refresh(view);
        context.promotionshelper.remove();
    }
    
    /**
     * 
     * @param view
     */
    public final void removesubmats(View view) {
        context.smaterialshelper.refresh(view);
        context.smaterialshelper.remove();
    }
    
    /**
     * 
     * @param view
     */
    public final void save(View view) {
        Request.save(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void show(View view) {
        Request.show(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void update(View view) {
        Request.update(view, context);
    }
    
    public final void validate(View view) { }
}