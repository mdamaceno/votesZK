/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author mdamaceno
 */
public class BaseController
{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ZK653App6PU");

    public String select = "Selecione";

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
}
