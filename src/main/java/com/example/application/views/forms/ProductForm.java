package com.example.application.views.forms;

import java.util.List;

import com.example.application.data.entity.Category;
import com.example.application.data.entity.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ProductForm extends FormLayout{
    Binder<Product> binder = new BeanValidationBinder<>(Product.class);
    TextField name = new TextField("Product name");
    TextField description = new TextField("Description");
    TextField quantity = new TextField("Quantity of product");
    TextField price = new TextField("Product price");
    ComboBox<Category> category = new ComboBox<>("Category");

    private Product product;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ProductForm(List<Category> categories) {
        addClassName("contact-form");
        binder.bindInstanceFields(this); 

        category.setItems(categories);

        add(
            name,
            description,
            quantity,
            price,
            category,
            createButtonLayout()
        );
    }

    public void setProduct(Product product) {
        this.product = product;
        binder.readBean(product);
        binder.setBean(product); // <1>
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, product)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          binder.writeBean(product);
          fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
    }
    
    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {
        private Product product;
      
        protected ProductFormEvent(ProductForm source, Product product) { 
          super(source, false);
          this.product = product;
        }
      
        public Product getProduct() {
          return product;
        }
    }
        public static class SaveEvent extends ProductFormEvent {
            SaveEvent(ProductForm source, Product product) {
              super(source, product);
            }
          }
          
          public static class DeleteEvent extends ProductFormEvent {
            DeleteEvent(ProductForm source, Product product) {
              super(source, product);
            }
          
          }
          
          public static class CloseEvent extends ProductFormEvent {
            CloseEvent(ProductForm source) {
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
