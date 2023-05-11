package com.example.application.views.list;

import com.example.application.data.entity.Purchase;
import com.example.application.service.ShopService;
import com.example.application.views.forms.PurchaseForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Purchases")
@Route(value = "/purchases", layout = MainLayout.class)
public class PurchaseView extends VerticalLayout{
    
    Grid<Purchase> grid = new Grid<>(Purchase.class);
    TextField filterText = new TextField();
    PurchaseForm purchaseForm;
    private ShopService service;

    public PurchaseView(ShopService service) {
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
        purchaseForm.setPurchase(null);
        purchaseForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("client", "orderedProducts", "dateOfOrder");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editPurchase(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, purchaseForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, purchaseForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        purchaseForm = new PurchaseForm(service.findAllProducts(null), service.findAllClients());
        purchaseForm.setWidth("25em");

        purchaseForm.addSaveListener(this::savePurchase);
        purchaseForm.addDeleteListener(this::deletePurchase);
        purchaseForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add purchase");
        addCategoryButton.addClickListener(e -> addPurchase());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addPurchase() {
        grid.asSingleSelect().clear();
        editPurchase(new Purchase());
    }

    private Object editPurchase(Purchase purchase) {
        if(purchase == null) {
            closeEditor();
        } else {
            purchaseForm.setPurchase(purchase);
            purchaseForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void savePurchase(PurchaseForm.SaveEvent event) {
        service.savePurchase(event.getPurchase());
        updateList();
        closeEditor();
    }

    private void deletePurchase(PurchaseForm.DeleteEvent event) {
        service.deletePurchase(event.getPurchase());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllPurchases());
    }

}
