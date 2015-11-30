/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.User;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author mdamaceno
 */
public class BaseController
{
    
    private String doc;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ZK653App6PU");

    public String select = "Selecione";
    
    public void redirectIfNotLogged() {
        if (Sessions.getCurrent().getAttribute("user")==null) {
            Executions.sendRedirect("/login/create.zul");
        }
    }
    
    public void redirectIfNotAdmin() {
        User user = (User) Sessions.getCurrent().getAttribute("user");
        if (user.getAdmin() != false) {
            Executions.sendRedirect("/index.zul");
        }
    }

    public String getSelect()
    {
        return select;
    }

    public void setSelect(String select)
    {
        this.select = select;
    }

    public void messageOk(String msg)
    {
        Messagebox.show(msg,
                "", Messagebox.OK, Messagebox.INFORMATION, (Event t) -> {
                    if (t.getName().equals("onOK")) {
                        Executions.sendRedirect("index.zul");
                    }
                });
    }

    public String getDoc()
    {
        return doc;
    }

    public void setDoc(String doc)
    {
        this.doc = doc;
    }
}
