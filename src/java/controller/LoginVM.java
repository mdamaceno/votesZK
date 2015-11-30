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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author mdamaceno
 */
public class LoginVM
{

    private String doc;
    private User user;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ZK653App6PU");

    @Init
    @NotifyChange({"doc"})
    public void init()
    {
        if (Sessions.getCurrent().getAttribute("user") != null) {
            Messagebox.show("Você já está logado!");
            Executions.sendRedirect("/index.zul");
        }
    }

    @Command
    public void makeLogin()
    {
        user = new UserJpaController(emf).findByDoc(doc);
        if (user == null) {
            Messagebox.show("Usuário não encontrado!");
            doc = "";
        } else {
            Sessions.getCurrent().setAttribute("user", user);
            Executions.getCurrent().sendRedirect("/index.zul");
        }
    }

    public String getDoc()
    {
        return doc;
    }

    public void setDoc(String doc)
    {
        this.doc = doc;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
