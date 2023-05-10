package com.example.application.views.forms;

import com.example.application.data.entity.Client;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class ClientForm extends FormLayout{
    Binder<Client> binder = new BeanValidationBinder<>(Client.class);
    TextField login = new TextField("Login");
    TextField mail = new TextField("Email");

    private Client client;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ClientForm() {
        addClassName("category-form");
        binder.bindInstanceFields(this);

        add(
            login,
            mail,
            createButtonLayout()
        );
    }

    public void setClient(Client client) {
        this.client = client;
        binder.readBean(client);
        binder.setBean(client); // <1>
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, client)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          binder.writeBean(client);
          fireEvent(new SaveEvent(this, client));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
      }

      public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
        private Client client;
      
        protected ClientFormEvent(ClientForm source, Client client) { 
          super(source, false);
          this.client = client;
        }
      
        public Client getClient() {
          return client;
        }
      }
      
      public static class SaveEvent extends ClientFormEvent {
        SaveEvent(ClientForm source, Client client) {
          super(source, client);
        }
      }
      
      public static class DeleteEvent extends ClientFormEvent {
        DeleteEvent(ClientForm source, Client client) {
          super(source, client);
        }
      
      }
      
      public static class CloseEvent extends ClientFormEvent {
        CloseEvent(ClientForm source) {
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
