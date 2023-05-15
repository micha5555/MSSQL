package com.example.application.views.forms;

import java.util.ArrayList;
import java.util.List;

import com.example.application.data.entity.Product;
import com.example.application.data.entity.Review;
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

public class ReviewForm extends FormLayout{
    Binder<Review> binder = new BeanValidationBinder<>(Review.class);
    TextField reviewBody = new TextField("Review body");
    ComboBox<Product> product_id = new ComboBox<>("Product");
    ComboBox<Integer> rate = new ComboBox<>("Rate"); 

    private Review review;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ReviewForm(List<Product> products) {
        addClassName("contact-form");
        binder.bindInstanceFields(this); 

        product_id.setItems(products);
        List<Integer> rates = new ArrayList<>(){{
            add(1); 
            add(2); 
            add(3); 
            add(4); 
            add(5);
        }};
        rate.setItems(rates);

        add(
            product_id,
            rate,
            reviewBody,
            createButtonLayout()
        );
    }

    public void setReview(Review review) {
        this.review = review;
        binder.readBean(review);
        binder.setBean(review); // <1>
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, review)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          binder.writeBean(review);
          fireEvent(new SaveEvent(this, review));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
    }
    
    public static abstract class ReviewFormEvent extends ComponentEvent<ReviewForm> {
        private Review review;
      
        protected ReviewFormEvent(ReviewForm source, Review review) { 
          super(source, false);
          this.review = review;
        }
      
        public Review getReview() {
          return review;
        }
    }
        public static class SaveEvent extends ReviewFormEvent {
            SaveEvent(ReviewForm source, Review review) {
              super(source, review);
            }
          }
          
          public static class DeleteEvent extends ReviewFormEvent {
            DeleteEvent(ReviewForm source, Review review) {
              super(source, review);
            }
          
          }
          
          public static class CloseEvent extends ReviewFormEvent {
            CloseEvent(ReviewForm source) {
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
