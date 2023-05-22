package com.example.application.views.list;

import com.example.application.data.entity.Client;
import com.example.application.service.ShopService;
import com.example.application.views.forms.ClientForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Clients")
@Route(value = "/clients", layout = MainLayout.class)
public class ClientView extends VerticalLayout{
    
    Grid<Client> grid = new Grid<>(Client.class);
    TextField filterText = new TextField();
    ClientForm clientForm;
    private ShopService service;

    public ClientView(ShopService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        clientForm.setClient(null);
        clientForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("login", "mail", "dateOfJoin");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editClient(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, clientForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, clientForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        clientForm = new ClientForm();
        clientForm.setWidth("25em");

        clientForm.addSaveListener(this::saveClient);
        clientForm.addDeleteListener(this::deleteClient);
        clientForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add client");
        addCategoryButton.addClickListener(e -> addClient());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addClient() {
        grid.asSingleSelect().clear();
        editClient(new Client());
    }

    private Object editClient(Client client) {
        if(client == null) {
            closeEditor();
        } else {
            clientForm.setClient(client);
            clientForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void saveClient(ClientForm.SaveEvent event) {
        service.saveClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void deleteClient(ClientForm.DeleteEvent event) {
        service.deleteClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllClients());
    }

}
