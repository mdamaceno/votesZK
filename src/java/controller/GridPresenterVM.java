/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridPresenterJpaController;
import java.util.List;
import model.GridPresenter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author mdamaceno
 */
public class GridPresenterVM extends BaseController
{
    private List<GridPresenter> listGridPresenter;
    
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        listGridPresenter = new GridPresenterJpaController(emf).findGridPresenterEntities();
    }

    public List<GridPresenter> getListGridPresenter()
    {
        return listGridPresenter;
    }

    public void setListGridPresenter(List<GridPresenter> listGridPresenter)
    {
        this.listGridPresenter = listGridPresenter;
    }
}
