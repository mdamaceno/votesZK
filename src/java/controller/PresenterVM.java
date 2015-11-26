/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GridPresenterJpaController;
import dao.PresenterJpaController;
import java.util.List;
import model.GridPresenter;
import model.Presenter;
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
public class PresenterVM extends BaseController
{

    private Presenter presenter;
    private List<Presenter> listPresenters;
    private String name;
    private Presenter presenterSelected = null;
    private String presenterId;

    @Init
    public void init()
    {
        presenterId = Executions.getCurrent().getParameter("presenterId");

        if (presenterId != null) {
            presenter = new PresenterJpaController(emf).findPresenter(Integer.parseInt(presenterId));
            name = presenter.getName();
        }

        listPresenters = new PresenterJpaController(emf).findPresenterEntities();
    }

    @Command
    public void goToPresenterCreate()
    {
        Executions.sendRedirect("create.zul");
    }

    @Command
    public void goToPresenterEdit()
    {
        Executions.sendRedirect("edit.zul?presenterId=" + presenterSelected.getId());
    }

    @Command
    public void savePresenter()
    {
        try {
            if (presenterId == null) {
                presenter = new Presenter();
                presenter.setName(name);

                new PresenterJpaController(emf).create(presenter);

                messageOk("Apresentador atualizado com sucesso!");
            } else {
                presenter = new PresenterJpaController(emf).findPresenter(Integer.parseInt(presenterId));
                presenter.setName(name);

                new PresenterJpaController(emf).edit(presenter);

                messageOk("Apresentador registrado com sucesso!");
            }
        } catch (Exception ex) {
            System.out.println("=============> " + ex.getMessage());
        }
    }

    @Command
    public void destroyPresenter()
    {
        if (presenterSelected != null) {
            Messagebox.show("Deseja excluir este apresentador?",
                    "Deseja excluir este apresentador?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event t) -> {
                        if (t.getName().equals("onYes")) {
                            List<GridPresenter> gridPresenter = new GridPresenterJpaController(emf).findAllByPresenterId(presenterSelected.getId());

                            if (gridPresenter.size() > 0) {
                                for (GridPresenter gp : gridPresenter) {
                                    new GridPresenterJpaController(emf).destroy(gp.getId());
                                }
                            }

                            new PresenterJpaController(emf).destroy(presenterSelected.getId());

                            messageOk("Apresentador excluído com sucesso!");
                        }
                    });
        }
    }

    public List<Presenter> getListPresenters()
    {
        return listPresenters;
    }

    public void setListPresenters(List<Presenter> listPresenters)
    {
        this.listPresenters = listPresenters;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Presenter getPresenter()
    {
        return presenter;
    }

    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }

    public Presenter getPresenterSelected()
    {
        return presenterSelected;
    }

    public void setPresenterSelected(Presenter presenterSelected)
    {
        this.presenterSelected = presenterSelected;
    }

    public String getPresenterId()
    {
        return presenterId;
    }

    public void setPresenterId(String presenterId)
    {
        this.presenterId = presenterId;
    }
}
