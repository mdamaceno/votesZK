/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridJpaController;
import dao.UserJpaController;
import dao.VoteJpaController;
import java.util.List;
import model.Grid;
import model.Vote;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;

/**
 *
 * @author mdamaceno
 */
public class VoteVM extends BaseController
{

    private List<Vote> listVotes;
    private List<Grid> listGrids;
    private String rating[];

    private Vote vote;
    private Grid selectedGrid;
    private String selectedScale = null;

    private final static String iconLocation = "/images/star_%s.png";

    public VoteVM()
    {
        this.rating = new String[]{"Péssimo", "Ruim", "Regular", "Bom", "Excelente"};
        this.listGrids = new GridJpaController(emf).findGridEntities();
        this.listVotes = new VoteJpaController(emf).findVoteEntities();
    }

    @Command
    public void goToVoteCreate()
    {
        Executions.sendRedirect("create.zul");
    }

    @Command
    public void saveVote()
    {
        vote = new Vote();
        vote.setComment("jfbsdfj");
        vote.setScale(Integer.parseInt(selectedScale));
        vote.setUserId(new UserJpaController(emf).findUser(1));
        vote.setGridId(new GridJpaController(emf).findGrid(selectedGrid.getId()));
        //new VoteJpaController(emf).create(vote);

        /* Inserir trycatch */
        
            Messagebox.show(vote.getGridId().getName());

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

    public String getIconImage(String icon)
    {
        switch (icon) {
            case "Péssimo":
                icon = "a";
                break;
            case "Ruim":
                icon = "b";
                break;
            case "Regular":
                icon = "c";
                break;
            case "Bom":
                icon = "d";
                break;
            case "Excelente":
                icon = "e";
                break;
        }
        return String.format(iconLocation, icon);
    }

    public Grid getSelectedGrid()
    {
        return selectedGrid;
    }

    public void setSelectedGrid(Grid selectedGrid)
    {
        this.selectedGrid = selectedGrid;
    }

    public String getSelectedScale()
    {
        return selectedScale;
    }

    public void setSelectedScale(String selectedScale)
    {
        switch (selectedScale) {
            case "Péssimo":
                this.selectedScale = "1";
                break;
            case "Ruim":
                this.selectedScale = "2";
                break;
            case "Regular":
                this.selectedScale = "3";
                break;
            case "Bom":
                this.selectedScale = "4";
                break;
            case "Excelente":
                this.selectedScale = "5";
                break;
        }
    }

    public Vote getVote()
    {
        return vote;
    }

    public void setVote(Vote vote)
    {
        this.vote = vote;
    }

}
