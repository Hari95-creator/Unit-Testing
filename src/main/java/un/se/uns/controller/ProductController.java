package un.se.uns.controller;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import un.se.uns.service.ProductService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/product")
public class ProductController {

    public ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }


    @RequestMapping(value="/add/product",method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody String data){

        HashMap<String,String> response=new HashMap<>();

        response=productService.addProduct(data);


        return ResponseEntity.ok(response);
    }
}
