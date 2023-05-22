package com.example.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.application.common.Common;
import com.example.application.data.entity.Category;
import com.example.application.data.entity.Client;
import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Purchase;
import com.example.application.data.entity.Product;
import com.example.application.data.entity.Review;
import com.example.application.data.repository.CategoryRepository;
import com.example.application.data.repository.ClientRepository;
import com.example.application.data.repository.PaymentRepository;
import com.example.application.data.repository.ProductRepository;
import com.example.application.data.repository.PurchaseRepository;
import com.example.application.data.repository.ReviewRepository;

@Service
public class ShopService {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public ShopService(CategoryRepository categoryRepository,
                        ProductRepository productRepository,
                        ReviewRepository reviewRepository,
                        ClientRepository clientRepository,
                        PurchaseRepository purchaseRepository,
                        PaymentRepository paymentRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.clientRepository = clientRepository;
        this.purchaseRepository = purchaseRepository;
        this.paymentRepository = paymentRepository;
        createFakeData();
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public List<Category> findAllCategories(String stringFilter) {
        // if(stringFilter == null || stringFilter.isEmpty()) {
            return categoryRepository.findAll();
        // } else {
            // return categoryRepository.search(stringFilter);
        // }
    }

    public List<Product> findAllProducts(String stringFilter) {
        // if(stringFilter == null || stringFilter.isEmpty()) {
            return productRepository.findAll();
        // } else {
            // return productRepository.search(stringFilter);
        // }
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public void saveCategory(Category category) {
        System.out.println("category id: " + category.getId());
        if (category == null) { 
            System.err.println("Category is null. Are you sure you have connected your form to the application?");
            return;
        }
        categoryRepository.save(category);
    }

    public void deleteReview(Review review) {
       reviewRepository.delete(review);
    }

    public void saveReview(Review review) {
        if (review == null) { 
            System.err.println("Review is null. Are you sure you have connected your form to the application?");
            return;
        }
        reviewRepository.save(review);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public void saveProduct(Product product) {
        if (product == null) { 
            System.err.println("Product is null. Are you sure you have connected your form to the application?");
            return;
        }
        productRepository.save(product);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public void saveClient(Client client) {
        if (client == null) { 
            System.err.println("Client is null. Are you sure you have connected your form to the application?");
            return;
        }
        clientRepository.save(client);
    }

    public void deletePurchase(Purchase purchase) {
        purchaseRepository.delete(purchase);
    }

    public void savePurchase(Purchase purchase) {
        if (purchase == null) { 
            System.err.println("Purchase is null. Are you sure you have connected your form to the application?");
            return;
        }
        purchaseRepository.save(purchase);
    }

    public void deletePayment(Payment payment) {
        paymentRepository.delete(payment);
    }

    public void savePayment(Payment payment) {
        if (payment == null) { 
            System.err.println("Payment is null. Are you sure you have connected your form to the application?");
            return;
        }
        paymentRepository.save(payment);
    }

    private void createFakeData() {
        // List<Category> categoryList = new ArrayList<>();
        // // categoryList.add(new Category("RTV"));
        // // categoryList.add(new Category("AGD"));
        // // categoryList.add(new Category("Spożywcze"));
        // // categoryList.add(new Category("Chemiczne"));
        // // this.categoryRepository.saveAll(categoryList);

        // List<Category> categories = findAllCategories(null);
        // Product p1 = new Product();
        // Product p2 = new Product();
        // Product p3 = new Product();

        // // p1.setId(20l);
        // p1.setName("produkt 1");
        // p1.setDescription("sdafsdf");
        // p1.setCategory(categories.get(0));
        // p1.setPrice(111);
        // p1.setQuantity(222);

        // p2.setName("Wiertarka");
        // p2.setDescription("Dobrze wierci");
        // p2.setCategory(categories.get(2));
        // p2.setPrice(1);
        // p2.setQuantity(2);

        // p3.setName("telewizor");
        // p3.setDescription("Kolorowy lcd");
        // ArrayList<Category> a3 = new ArrayList<>();
        // p3.setCategory(categories.get(3));
        // p3.setPrice(2000);
        // p3.setQuantity(5);

        // this.productRepository.save(p1);
        // this.productRepository.save(p2);
        // this.productRepository.save(p3);

        // Set<Product> products = new HashSet<>();
        // for(Product p : findAllProducts(null)) {
        //     products.add(p);
        // }
        // Map<Integer,Product> castedProducts = new HashMap<>();
        // int counter = 0;
        // for(Product p : products) {
        //     castedProducts.put(counter++, p);
        // }

        // List<Review> reviews = new ArrayList<>();
        // reviews.add(new Review(5, "Super, fajne, ogólmnie polecam", castedProducts.get(0)));
        // reviews.add(new Review(1, "Nie polecam, w paczce był chrabąszcz", castedProducts.get(2)));
        // reviews.add(new Review(5, "W paczce wcale nie było chrabąszcza", castedProducts.get(2)));
        // reviews.add(new Review(3, "Takie sobie", castedProducts.get(1)));
        // reviews.add(new Review(1, "Złodzieje", castedProducts.get(0)));
        
        // reviewRepository.saveAll(reviews);

        // List<Client> cl = new ArrayList<>();
        // cl.add(new Client("login123", "mail@gmail.com"));
        // cl.add(new Client("zielonyadam122", "zielony@gmail.com"));
        // cl.add(new Client("allegrowicz222", "allegronskiallegron@gmail.com"));
        
        // clientRepository.saveAll(cl);

        // List<Client> clients = findAllClients();

        // List<Product> prInList = new ArrayList<>(products);
        // List<Product> pr1 = new ArrayList<>(){{add(prInList.get(1));}};
        // List<Product> pr2 = new ArrayList<>(){{add(prInList.get(1)); add(prInList.get(2));}};
        // Purchase o1 = new Purchase(products, clients.get(0));
        // Purchase o2 = new Purchase(new HashSet<Product>(pr1), clients.get(1));
        // Purchase o3 = new Purchase(new HashSet<Product>(pr2), clients.get(1));

        // this.purchaseRepository.save(o1);
        // this.purchaseRepository.save(o2);
        // this.purchaseRepository.save(o3);
        


        // List<Purchase> purchases = findAllPurchases();
        // List<Payment> payments = new ArrayList<>();
        // payments.add(new Payment(Common.getPaymentMethods().get(1), purchases.get(0)));
        // payments.add(new Payment(Common.getPaymentMethods().get(0), purchases.get(1)));
        // payments.add(new Payment(Common.getPaymentMethods().get(2), purchases.get(2)));

        // this.paymentRepository.saveAll(payments);
        // private String name;
        // private String description;
        // private int quantity;
        // private double price;
        // private Category category;

        // System.out.println(purchaseRepository.search("zielonyadam122"));
    }
}
