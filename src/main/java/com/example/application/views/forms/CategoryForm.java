package com.example.application.views.forms;

import com.example.application.data.entity.Category;
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

public class CategoryForm extends FormLayout{
    Binder<Category> binder = new BeanValidationBinder<>(Category.class);
    TextField name = new TextField("Category name");

    private Category category;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public CategoryForm() {
        addClassName("category-form");
        binder.bindInstanceFields(this);

        add(
            name,
            createButtonLayout()
        );
    }

    public void setCategory(Category category) {
        this.category = category;
        binder.readBean(category);
        binder.setBean(category); // <1>
      }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, category)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
          binder.writeBean(category);
          fireEvent(new SaveEvent(this, category));
        } catch (ValidationException e) {
          e.printStackTrace();
        }
      }

      public static abstract class CategoryFormEvent extends ComponentEvent<CategoryForm> {
        private Category category;
      
        protected CategoryFormEvent(CategoryForm source, Category category) { 
          super(source, false);
          this.category = category;
        }
      
        public Category getCategory() {
          return category;
        }
      }
      
      public static class SaveEvent extends CategoryFormEvent {
        SaveEvent(CategoryForm source, Category category) {
          super(source, category);
        }
      }
      
      public static class DeleteEvent extends CategoryFormEvent {
        DeleteEvent(CategoryForm source, Category category) {
          super(source, category);
        }
      
      }
      
      public static class CloseEvent extends CategoryFormEvent {
        CloseEvent(CategoryForm source) {
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
