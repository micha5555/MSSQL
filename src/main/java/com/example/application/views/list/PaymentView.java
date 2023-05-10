package com.example.application.views.list;

import com.example.application.data.entity.Payment;
import com.example.application.service.ShopService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Payments")
@Route(value = "/payments", layout = MainLayout.class)
public class PaymentView extends VerticalLayout{
     
    Grid<Payment> grid = new Grid<>(Payment.class);
    TextField filterText = new TextField();
    private ShopService service;

    public PaymentView(ShopService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        // configureForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("purchase", "paymentMethod", "dateOfPayment");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        // content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    // private void configureForm() {
    //     form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
    //     form.setWidth("25em");

    //     form.addSaveListener(this::saveContact);
    //     form.addDeleteListener(this::deleteContact);
    //     form.addCloseListener(e -> closeEditor());
    // }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add product");
        // addCategoryButton.addClickListener(e -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.findAllPayments());
    }

}
