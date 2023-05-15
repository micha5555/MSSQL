package com.example.application.views.list;

import com.example.application.data.entity.Product;
import com.example.application.service.ShopService;
import com.example.application.views.forms.ProductForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Products")
@Route(value = "", layout = MainLayout.class)
public class ProductView extends VerticalLayout{
    Grid<Product> grid = new Grid<>(Product.class);
    TextField filterText = new TextField();
    ProductForm productForm;
    private ShopService service;

    public ProductView(ShopService service) {
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

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "category", "description", "price", "quantity");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editProduct(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, productForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, productForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        productForm = new ProductForm(service.findAllCategories(null));
        productForm.setWidth("25em");

        productForm.addSaveListener(this::saveProduct);
        productForm.addDeleteListener(this::deleteProduct);
        productForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCategoryButton = new Button("Add product");
        addCategoryButton.addClickListener(e -> addProduct());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        // grid.setItems(service.findAllProducts(filterText.getValue()));
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private Object editProduct(Product product) {
        if(product == null) {
            closeEditor();
        } else {
            productForm.setProduct(product);
            productForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void saveProduct(ProductForm.SaveEvent event) {
        // service.saveProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void deleteProduct(ProductForm.DeleteEvent event) {
        // service.deleteProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        productForm.setProduct(null);
        productForm.setVisible(false);
        removeClassName("editing");
    }
}
