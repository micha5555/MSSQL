package com.example.application.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Common {
    public static final List<String> PAYMENT_METHODS = new ArrayList<>(Arrays.asList(
        "In cash", "Credit card", "Voucher"));
    
        public static List<String> getPaymentMethods() {
            return PAYMENT_METHODS;
        }
}
