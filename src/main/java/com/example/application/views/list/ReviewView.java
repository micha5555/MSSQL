package com.example.application.views.list;

import com.example.application.data.entity.Review;
import com.example.application.service.ShopService;
import com.example.application.views.forms.ReviewForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviews")
@Route(value = "/reviews", layout = MainLayout.class)
public class ReviewView extends VerticalLayout{
    Grid<Review> grid = new Grid<>(Review.class);
    TextField filterText = new TextField();
    ReviewForm reviewForm;
    private ShopService service;

    public ReviewView(ShopService service) {
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
        reviewForm.setReview(null);
        reviewForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("product", "rate", "reviewBody", "dateOfReview");
        // grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editReview(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, reviewForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, reviewForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        reviewForm = new ReviewForm(service.findAllProducts(null));
        reviewForm.setWidth("25em");

        reviewForm.addSaveListener(this::saveReview);
        reviewForm.addDeleteListener(this::deleteReview);
        reviewForm.addCloseListener(e -> closeEditor());
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addReviewButton = new Button("Add review");
        addReviewButton.addClickListener(e -> addReview());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addReviewButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReview() {
        grid.asSingleSelect().clear();
        editReview(new Review());
    }

    private Object editReview(Review review) {
        if(review == null) {
            closeEditor();
        } else {
            reviewForm.setReview(review);
            reviewForm.setVisible(true);
            addClassName("editing");
        }
        return null;
    }

    private void saveReview(ReviewForm.SaveEvent event) {
        service.saveReview(event.getReview());
        updateList();
        closeEditor();
    }

    private void deleteReview(ReviewForm.DeleteEvent event) {
        service.deleteReview(event.getReview());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllReviews());
    }
}
