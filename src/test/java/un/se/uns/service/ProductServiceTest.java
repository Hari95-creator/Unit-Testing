package un.se.uns.service;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import un.se.uns.Entity.Product;
import un.se.uns.Repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void addProductShouldAddProductSuccessfully() {

        String data = """
                            {
                                   "pName": "Bluetooth Headphones",
                                   "pCategory": "Audio",
                                   "pPrice": 1499.50,
                                   "pDesc": "Over-ear headphones with noise cancellation and mic",
                                   "pStock": 75
                                 }
                """;


        JSONObject productData = new JSONObject(data);

        Product product = new Product();
        product.setProductGid(UUID.randomUUID());
        product.setProductName(productData.optString("pName"));
        product.setCategory(productData.optString("pCategory"));
        product.setPrice(productData.optBigDecimal("pPrice", BigDecimal.valueOf(0.00)));
        product.setDescription(productData.optString("pDesc"));

        Date currentDate = new Date();
        product.setCreatedAt(currentDate);
        product.setQuantityInStock(productData.optInt("pStock"));

        when(productRepository.save(product)).thenReturn(product);

        HashMap<String, String> response = productService.addProuctSucessFully(product);

        assertNotNull(response.get("msg"));
        assertEquals("success", response.get("msg"));
        assertTrue("success".equals(response.get("msg")));
        System.out.println("My First Test");
    }

}