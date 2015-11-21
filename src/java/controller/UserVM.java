/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.User;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 *
 * @author mdamaceno
 */
public class UserVM extends BaseController
{
    private List<User> listUsers;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        listUsers = new UserJpaController(emf).findUserEntities();
    }

    public List<User> getListUsers()
    {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers)
    {
        this.listUsers = listUsers;
    }
    
    
}
