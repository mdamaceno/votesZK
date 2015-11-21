/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridJpaController;
import dao.VoteJpaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Grid;
import model.Vote;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author mdamaceno
 */
public class VoteVM extends BaseController
{

    private List<Vote> listVotes;
    private List<Grid> listGrids;
    private String rating[] = new String[]{"1","2","3","4","5"};
    
    private final static String iconLocation = "/images/star_%s.png";

    @Init
    public void init()
    {
        listVotes = new VoteJpaController(emf).findVoteEntities();
        listGrids = new GridJpaController(emf).findGridEntities();
    }

    @Command
    public void goToVoteCreate()
    {
        Executions.sendRedirect("create.zul");
    }

    public List<Vote> getListVotes()
    {
        return listVotes;
    }

    public void setListVotes(List<Vote> listVotes)
    {
        this.listVotes = listVotes;
    }

    public List<Grid> getListGrids()
    {
        return listGrids;
    }

    public void setListGrids(List<Grid> listGrids)
    {
        this.listGrids = listGrids;
    }

    public String[] getRating()
    {
        return rating;
    }

    public void setRating(String[] rating)
    {
        this.rating = rating;
    }
    
    public String getIconImage(String icon) {
        return String.format(iconLocation, icon);
    }

    private Object[] appendValue(Object[] obj, Object newObj)
    {

        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

}
