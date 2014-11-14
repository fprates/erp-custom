package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.packagetool.common.SearchHelpData;

public class MaterialInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        ComplexModelInstall cmodel;
        DataElement materialcd, materialtx;
        SearchHelpData shd;
        
        materialcd = elementchar("AEGIS_MATRL_CODE", 18, DataType.UPPERCASE);
        materialtx = elementchar("AEGIS_MATRL_TEXT", 35, DataType.UPPERCASE);
        
        /*
         * cabeçalho
         */
        model = tag("materialhd", modelInstance(
                "AEGIS_MATERIAL_HEAD", "AEGISMATHD"));
        searchhelp(tag("materialcode", model.key(
                "CODE", "MATCD", materialcd)), "AEGIS_MATERIAL");
        model.item(
                "TEXT", "MATTX", materialtx);
        
        shd = searchHelpInstance("AEGIS_MATERIAL", "AEGIS_MATERIAL_HEAD");
        shd.setExport("CODE");
        shd.add("CODE");
        shd.add("TEXT");
        
        cmodel = cmodelInstance("AEGIS_MATERIAL");
        cmodel.header("materialhd");
    }

}
