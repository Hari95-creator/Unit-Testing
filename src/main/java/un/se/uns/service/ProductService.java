package un.se.uns.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import un.se.uns.Entity.Product;
import un.se.uns.Repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class ProductService {

    public ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;


    }

    public HashMap<String, String> addProduct(String data) {

        JSONObject requestData = new JSONObject(data);

        HashMap<String, String> addingProcess = new HashMap<>();

        try {

            Product product = new Product();
            product.setProductGid(UUID.randomUUID());
            product.setProductName(requestData.optString("pName"));
            product.setCategory(requestData.optString("pCategory"));
            product.setPrice(requestData.optBigDecimal("pPrice", BigDecimal.valueOf(0.00)));
            product.setDescription(requestData.optString("pDesc"));

            Date currentDate = new Date();
            product.setCreatedAt(currentDate);
            product.setQuantityInStock(requestData.optInt("pStock"));

            addingProcess = addProuctSucessFully(product);

            return addingProcess;

        } catch (Exception e) {

            return addingProcess;

        }


    }

    public HashMap<String, String> addProuctSucessFully(Product product) {

        HashMap<String, String> addingData = new HashMap<>();

        try {

            productRepository.save(product);

            addingData.put("msg", "success");


        } catch (Exception e) {

            addingData.put("msg", "failed");
        }

        return addingData;
    }

    public void deleteProduct(int productId){

        productRepository.deleteByProductId(productId);
    }
}
