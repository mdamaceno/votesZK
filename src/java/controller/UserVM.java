/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserJpaController;
import java.util.List;
import model.User;
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
public class UserVM extends BaseController
{
    
    private List<User> listUsers;
    private String name, email, address, phone_number, doc;
    private boolean admin;
    private String userId;
    private User user;
    private String selectedAdmin;
    
    private User userSelected = null;
    
    @Init
    public void init()
    {
        userId = Executions.getCurrent().getParameter("userId");
        listUsers = new UserJpaController(emf).findUserEntities();
        
        if (userId != null) {
            user = new UserJpaController(emf).findUser(Integer.parseInt(userId));
            name = user.getName();
            address = user.getAddress();
            email = user.getEmail();
            phone_number = user.getPhoneNumber();
            doc = user.getDoc();
            selectedAdmin = String.valueOf(user.getAdmin());
        }
    }
    
    @Command
    public void goToUserCreate()
    {
        Executions.sendRedirect("create.zul");
    }
    
    @Command
    public void goToUserEdit()
    {
        Executions.sendRedirect("edit.zul?userId=" + userSelected.getId());
    }
    
    @Command
    public void saveUser()
    {
        try {
            if (userId == null) {
                user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setAddress(address);
                user.setPhoneNumber(phone_number);
                user.setDoc(doc);
                user.setAdmin(Boolean.parseBoolean(selectedAdmin));
                
                new UserJpaController(emf).create(user);
                
                messageOk("Usuário registrado com sucesso!");
            } else {
                user = new UserJpaController(emf).findUser(Integer.parseInt(userId));
                user.setName(name);
                user.setEmail(email);
                user.setAddress(address);
                user.setPhoneNumber(phone_number);
                user.setDoc(doc);
                user.setAdmin(Boolean.parseBoolean(selectedAdmin));
                
                new UserJpaController(emf).edit(user);
                
                messageOk("Usuário atualizado com sucesso!");
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage(), "", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Command
    public void destroyUser()
    {
        if (userSelected != null) {
            Messagebox.show("Deseja excluir este usuário?",
                    "Deseja excluir este usuário?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event t) -> {
                        if (t.getName().equals("onYes")) {
                            
                            new UserJpaController(emf).destroy(userSelected.getId());
                            
                            messageOk("Usuário excluído com sucesso!");
                        }
                    });
        }
    }
    
    public List<User> getListUsers()
    {
        return listUsers;
    }
    
    public void setListUsers(List<User> listUsers)
    {
        this.listUsers = listUsers;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getPhone_number()
    {
        return phone_number;
    }
    
    public void setPhone_number(String phone_number)
    {
        this.phone_number = phone_number;
    }
    
    public String getDoc()
    {
        return doc;
    }
    
    public void setDoc(String doc)
    {
        this.doc = doc;
    }
    
    @NotifyChange
    public boolean isAdmin()
    {
        return admin;
    }
    
    @NotifyChange
    public void setAdmin(boolean admin)
    {
        if (selectedAdmin.equals("true")) {
            this.admin = true;
        }
        this.admin = false;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @NotifyChange
    public String getSelectedAdmin()
    {
        return selectedAdmin;
    }
    
    @NotifyChange
    public void setSelectedAdmin(String selectedAdmin)
    {
        this.selectedAdmin = selectedAdmin;
    }
    
    public User getUserSelected()
    {
        return userSelected;
    }
    
    public void setUserSelected(User userSelected)
    {
        this.userSelected = userSelected;
    }
}
