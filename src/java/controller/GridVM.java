/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridJpaController;
import java.util.List;
import model.Grid;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author mdamaceno
 */
public class GridVM extends BaseController
{
    private List<Grid> listGrids;
    
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        listGrids = new GridJpaController(emf).findGridEntities();
    }

    public List<Grid> getListGrids()
    {
        return listGrids;
    }

    public void setListGrids(List<Grid> listGrids)
    {
        this.listGrids = listGrids;
    }
}
