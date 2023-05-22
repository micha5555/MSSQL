package com.example.application.views.list;

import com.example.application.data.entity.Payment;
import com.example.application.service.ShopService;
import com.example.application.views.forms.PaymentForm;
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
    PaymentForm paymentForm;
    private ShopService service;

    public PaymentView(ShopService service) {
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
        paymentForm.setPayment(null);
        paymentForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        // grid.addColumn(Payment::getPurchase).setHeader("Purchasee");
        grid.setColumns("purchase", "paymentMethod", "dateOfPayment");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editPayment(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, paymentForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, paymentForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        paymentForm = new PaymentForm(service.findAllPurchases());
        paymentForm.setWidth("25em");

        paymentForm.addSaveListener(this::savePayment);
        paymentForm.addDeleteListener(this::deletePayment);
        paymentForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add payment");
        addCategoryButton.addClickListener(e -> addPayment());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addPayment() {
        grid.asSingleSelect().clear();
        editPayment(new Payment());
    }

    private Object editPayment(Payment payment) {
        if(payment == null) {
            closeEditor();
        } else {
            paymentForm.setPayment(payment);
            paymentForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void savePayment(PaymentForm.SaveEvent event) {
        service.savePayment(event.getPayment());
        updateList();
        closeEditor();
    }

    private void deletePayment(PaymentForm.DeleteEvent event) {
        service.deletePayment(event.getPayment());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllPayments());
    }

}
