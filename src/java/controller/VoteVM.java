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
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;

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
    private String comment;
    private Grid selectedGrid;
    private String selectedScale = null;

    private Vote voteSelected = null;
    private String voteId;

    private final static String iconLocation = "/images/star_%s.png";

    @Init
    @NotifyChange({"voteId"})
    public void init()
    {
        voteId = Executions.getCurrent().getParameter("voteId");

        if (voteId != null) {
            vote = new VoteJpaController(emf).findVote(Integer.parseInt(voteId));
            selectedGrid = new GridJpaController(emf).findGrid(vote.getGridId().getId());
            selectedScale = String.valueOf(vote.getScale());
            comment = vote.getComment();
        }

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
    @NotifyChange({"comment"})
    public void goToVoteEdit()
    {
        comment = voteSelected.getComment();
        Executions.sendRedirect("edit.zul?voteId=" + voteSelected.getId());
    }

    @Command
    public void saveVote()
    {
        try {
            if (voteId == null) {
                vote = new Vote();
                vote.setComment(comment);
                vote.setScale(Integer.parseInt(selectedScale));
                vote.setUserId(new UserJpaController(emf).findUser(1));
                vote.setGridId(new GridJpaController(emf).findGrid(selectedGrid.getId()));

                new VoteJpaController(emf).create(vote);

                Messagebox.show("Voto registrado com sucesso!",
                        "", Messagebox.OK, Messagebox.INFORMATION, (Event t) -> {
                            if (t.getName().equals("onOK")) {
                                Executions.sendRedirect("index.zul");
                            }
                        });
            } else {
                vote = new VoteJpaController(emf).findVote(Integer.parseInt(voteId));
                vote.setComment(comment);
                vote.setScale(Integer.parseInt(selectedScale));
                vote.setUserId(new UserJpaController(emf).findUser(1));
                vote.setGridId(new GridJpaController(emf).findGrid(selectedGrid.getId()));

                new VoteJpaController(emf).edit(vote);

                Messagebox.show("Voto atualizado com sucesso!",
                        "", Messagebox.OK, Messagebox.INFORMATION, (Event t) -> {
                            if (t.getName().equals("onOK")) {
                                Executions.sendRedirect("index.zul");
                            }
                        });
            }
        } catch (Exception ex) {
            System.out.println("=============> " + ex.getMessage());
        }
    }

    @Command
    public void destroyVote()
    {
        if (voteSelected != null) {
            Messagebox.show("Deseja excluir este voto?",
                    "Deseja excluir este voto?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event t) -> {
                        if (t.getName().equals("onYes")) {
                            new VoteJpaController(emf).destroy(voteSelected.getId());
                            Executions.sendRedirect("index.zul");
                        }
                    });
        }
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

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Vote getVote()
    {
        return vote;
    }

    public void setVote(Vote vote)
    {
        this.vote = vote;
    }

    public Vote getVoteSelected()
    {
        return voteSelected;
    }

    public void setVoteSelected(Vote voteSelected)
    {
        this.voteSelected = voteSelected;
    }

    public String getVoteId()
    {
        return voteId;
    }

    public void setVoteId(String voteId)
    {
        this.voteId = voteId;
    }
}
