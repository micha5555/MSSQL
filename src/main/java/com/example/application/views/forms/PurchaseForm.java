package com.example.application.views.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.application.data.entity.Client;
import com.example.application.data.entity.Product;
import com.example.application.data.entity.Purchase;
import com.example.application.service.ShopService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;

public class PurchaseForm extends FormLayout{
    Binder<Purchase> binder = new BeanValidationBinder<>(Purchase.class);
    Grid<Product> grid = new Grid<>();
    ComboBox<Client> client = new ComboBox<>("Client");
    
    List<Product> products;
    private Purchase purchase;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public PurchaseForm(List<Product> products, List<Client> clients) {
        this.products = products;
        addClassName("contact-form");
        binder.bindInstanceFields(this); 
        client.setItems(clients);

        grid.setItems(products);
        // for(Product p : products) {
        //   System.out.println(p.getId());
        // }
        grid.setSelectionMode(SelectionMode.MULTI);

        configureGrid();
        add(
            client,
            grid,
            createButtonLayout()
        );
        
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.addColumn(Product::getName).setHeader("Products");
    }

    public void setPurchase(Purchase purchase) {
      // if(purchase != null) {
      //   System.out.println(purchase.getOrderedProducts());

      // }
        grid.deselectAll();
        this.purchase = purchase;
        binder.readBean(purchase);
        binder.setBean(purchase); // <1>
        if(purchase != null) {
            if(purchase.getOrderedProducts() != null) {
                for(Product p : purchase.getOrderedProducts()) {
                    grid.select(p);
                }
            }
        }
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, purchase)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          purchase.setOrderedProducts(new ArrayList<>(grid.getSelectedItems()));
          binder.writeBean(purchase);
          fireEvent(new SaveEvent(this, purchase));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
    }
    
    public static abstract class PurchaseFormEvent extends ComponentEvent<PurchaseForm> {
        private Purchase purchase;
      
        protected PurchaseFormEvent(PurchaseForm source, Purchase purchase) { 
          super(source, false);
          this.purchase = purchase;
        }
      
        public Purchase getPurchase() {
          return purchase;
        }
    }
        public static class SaveEvent extends PurchaseFormEvent {
            SaveEvent(PurchaseForm source, Purchase purchase) {
              super(source, purchase);
            }
          }
          
          public static class DeleteEvent extends PurchaseFormEvent {
            DeleteEvent(PurchaseForm source, Purchase purchase) {
              super(source, purchase);
            }
          
          }
          
          public static class CloseEvent extends PurchaseFormEvent {
            CloseEvent(PurchaseForm source) {
              super(source, null);
            }
          }
          
          public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) { 
            return addListener(DeleteEvent.class, listener);
          }
          
          public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
            return addListener(SaveEvent.class, listener);
          }
        
          public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
            return addListener(CloseEvent.class, listener);
          }
      
}

