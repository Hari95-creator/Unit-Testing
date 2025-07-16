package un.se.uns.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import un.se.uns.service.ProductService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;


    //@BeforeEach,@BeforeAll,@Test are all class level setup

    //The Before all will execute only once and mainly it used handle like mock db connection and its type should be in static
    //and this for class level setup and will be execute before all the test cases
    @BeforeAll
    public static void init() {

        System.out.println("Before All the Test cases");
    }

    @AfterAll
    public static void Destory() {

        System.out.println("It will execute only once ,After all the Test cases, for example we destroy Db Connection ");

    }

    //@BeforeEach runs before every test method, and is used to set up test data, mocks, or environment needed for that test
    @BeforeEach
    public void initForEachTestCases() {
        System.out.println("Before Each Test cases");
    }

    @Test
    void addShouldHappenSuccessfullyInProductController() {

        String data = """
                            {
                                   "pName": "Bluetooth Headphones",
                                   "pCategory": "Audio",
                                   "pPrice": 1499.50,
                                   "pDesc": "Over-ear headphones with noise cancellation and mic",
                                   "pStock": 75
                                 }
                """;

        HashMap<String, String> mockResp = new HashMap<>();
        mockResp.put("msg", "success");

        //“If this mock productService receives a call to addProduct(data), return mockResp instead of executing real logic.”
        //👉 No call happens yet — you're just registering the "rule" on the mock.
        when(productService.addProduct(data)).thenReturn(mockResp);


        //When the controller calls the service, it actually calls the mocked service —
        // and Mockito returns the stubbed response that you defined earlier.
        ResponseEntity<Map<String, String>> response = productController.addProduct(data);

        //here verify is used to ensure that the method was called in product service with expected input
        verify(productService).addProduct(data);

        JSONObject respObj = new JSONObject(response.getBody());

        assertEquals("success", respObj.getString("msg"));


    }

    //@AfterAll @AfterEach cleanup level
    @AfterEach
    public void cleanUp() {

        System.out.println("It is used for resetting the values after each test cases performed");
    }


}