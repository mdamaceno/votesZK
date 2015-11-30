/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridJpaController;
import dao.GridPresenterJpaController;
import dao.PresenterJpaController;
import java.util.Date;
import java.util.List;
import java.util.Set;
import model.Grid;
import model.GridPresenter;
import model.Presenter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author mdamaceno
 */
public class GridVM extends BaseController
{

    private List<Grid> listGrids;
    private List<Presenter> listPresenters;
    private Presenter presenterSelected;
    private Set selectedItem;

    private String name, description;
    private Date schedule;
    private String gridId;
    private Grid grid;
    private List<Presenter> item;

    private Grid gridSelected = null;

    @Init
    public void init()
    {
        redirectIfNotLogged();
        
        listGrids = new GridJpaController(emf).findGridEntities();
        listPresenters = new PresenterJpaController(emf).findPresenterEntities();
    }

    @Command
    public void saveGrid()
    {
        try {
            if (gridId == null) {
                grid = new Grid();
                grid.setName(name);

                grid.setSchedule(schedule);
                grid.setDescription(description);

                new GridJpaController(emf).create(grid);
                int lastGrid = new GridJpaController(emf).findByLastId();

                if (selectedItem.size() > 0) {
                    GridPresenter gridPresenter = new GridPresenter();
                    gridPresenter.setGridId(new GridJpaController(emf).findGrid(lastGrid));
                    selectedItem.stream().map((p) -> {
                        gridPresenter.setPresenterId((Presenter) p);
                        return p;
                    }).forEach((_item) -> {
                        new GridPresenterJpaController(emf).create(gridPresenter);
                    });
                }
                messageOk("Programação registrada com sucesso!");
            } else {

            }
        } catch (Exception ex) {
            System.out.println("=============> " + ex.getMessage());
        }
    }

    @Command
    public void destroyGrid()
    {
        if (gridSelected != null) {
            Messagebox.show("Deseja excluir este programação?",
                    "Deseja excluir este programação?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event t) -> {
                        if (t.getName().equals("onYes")) {
                            List<GridPresenter> gridPresenter = new GridPresenterJpaController(emf).findAllByGridId(gridSelected.getId());

                            if (gridPresenter.size() > 0) {
                                for (GridPresenter gp : gridPresenter) {
                                    new GridPresenterJpaController(emf).destroy(gp.getId());
                                }
                            }

                            new GridJpaController(emf).destroy(gridSelected.getId());

                            messageOk("Programação excluída com sucesso!");
                        }
                    });
        }
    }

    @Command
    public void goToGridCreate()
    {
        Executions.sendRedirect("create.zul");
    }

    public List<Grid> getListGrids()
    {
        return listGrids;
    }

    public void setListGrids(List<Grid> listGrids)
    {
        this.listGrids = listGrids;
    }

    public List<Presenter> getListPresenters()
    {
        return listPresenters;
    }

    public void setListPresenters(List<Presenter> listPresenters)
    {
        this.listPresenters = listPresenters;
    }

    @NotifyChange
    public Presenter getPresenterSelected()
    {
        return presenterSelected;
    }

    public void setPresenterSelected(Presenter presenterSelected)
    {
        this.presenterSelected = presenterSelected;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getSchedule()
    {
        return schedule;
    }

    public void setSchedule(Date schedule)
    {
        this.schedule = schedule;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Grid getGrid()
    {
        return grid;
    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    public String getGridId()
    {
        return gridId;
    }

    public void setGridId(String gridId)
    {
        this.gridId = gridId;
    }

    public List<Presenter> getItem()
    {
        return item;
    }

    public void setItem(List<Presenter> item)
    {
        this.item = item;
    }

    public Set getSelectedItem()
    {
        return selectedItem;
    }

    public void setSelectedItem(Set selectedItem)
    {
        this.selectedItem = selectedItem;
    }

    public Grid getGridSelected()
    {
        return gridSelected;
    }

    public void setGridSelected(Grid gridSelected)
    {
        this.gridSelected = gridSelected;
    }


}
