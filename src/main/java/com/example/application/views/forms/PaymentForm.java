package com.example.application.views.forms;

import java.util.List;

import com.example.application.common.Common;
import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Purchase;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class PaymentForm extends FormLayout{
    Binder<Payment> binder = new BeanValidationBinder<>(Payment.class);
    ComboBox<Purchase> purchase = new ComboBox<>("Purchase");
    ComboBox<String> paymentMethods = new ComboBox<>("Payment method");

    private Payment payment;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public PaymentForm(List<Purchase> purchases) {
        addClassName("contact-form");
        binder.bindInstanceFields(this); 

        purchase.setItems(purchases);
        paymentMethods.setItems(Common.getPaymentMethods());

        add(
            purchase,
            paymentMethods,
            createButtonLayout()
        );
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        binder.readBean(payment);
        binder.setBean(payment); // <1>
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, payment)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          binder.writeBean(payment);
          fireEvent(new SaveEvent(this, payment));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
    }
    
    public static abstract class PaymentFormEvent extends ComponentEvent<PaymentForm> {
        private Payment payment;
      
        protected PaymentFormEvent(PaymentForm source, Payment payment) { 
          super(source, false);
          this.payment = payment;
        }
      
        public Payment getPayment() {
          return payment;
        }
    }
        public static class SaveEvent extends PaymentFormEvent {
            SaveEvent(PaymentForm source, Payment payment) {
              super(source, payment);
            }
          }
          
          public static class DeleteEvent extends PaymentFormEvent {
            DeleteEvent(PaymentForm source, Payment payment) {
              super(source, payment);
            }
          
          }
          
          public static class CloseEvent extends PaymentFormEvent {
            CloseEvent(PaymentForm source) {
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

