/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}
