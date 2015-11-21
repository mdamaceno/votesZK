/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PresenterJpaController;
import java.util.List;
import model.Presenter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author mdamaceno
 */
public class PresenterVM extends BaseController
{
    private List<Presenter> listPresenters;
    
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        listPresenters = new PresenterJpaController(emf).findPresenterEntities();
    }

    public List<Presenter> getListPresenters()
    {
        return listPresenters;
    }

    public void setListPresenters(List<Presenter> listPresenters)
    {
        this.listPresenters = listPresenters;
    }
    
}
