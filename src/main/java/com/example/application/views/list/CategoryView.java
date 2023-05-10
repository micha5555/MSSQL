package com.example.application.views.list;

import com.example.application.data.entity.Category;
import com.example.application.service.ShopService;
import com.example.application.views.forms.CategoryForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Categories")
@Route(value = "/categories", layout = MainLayout.class)
public class CategoryView extends VerticalLayout {
    Grid<Category> grid = new Grid<>(Category.class);
    TextField filterText = new TextField();
    CategoryForm categoryForm;
    private ShopService service;

    public CategoryView(ShopService service) {
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
        categoryForm.setCategory(null);
        categoryForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCategory(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, categoryForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, categoryForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        categoryForm = new CategoryForm();
        categoryForm.setWidth("25em");

        categoryForm.addSaveListener(this::saveCategory);
        categoryForm.addDeleteListener(this::deleteCategory);
        categoryForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add category");
        addCategoryButton.addClickListener(e -> addCategory());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCategory() {
        grid.asSingleSelect().clear();
        editCategory(new Category());
    }

    private Object editCategory(Category category) {
        if(category == null) {
            closeEditor();
        } else {
            categoryForm.setCategory(category);
            categoryForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void saveCategory(CategoryForm.SaveEvent event) {
        service.saveCategory(event.getCategory());
        updateList();
        closeEditor();
    }

    private void deleteCategory(CategoryForm.DeleteEvent event) {
        service.deleteCategory(event.getCategory());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllCategories(filterText.getValue()));
    }

}
