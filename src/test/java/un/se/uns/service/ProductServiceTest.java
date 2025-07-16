package un.se.uns.service;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import un.se.uns.Entity.Product;
import un.se.uns.Repository.ProductRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    //@BeforeEach,@BeforeAll,@Test are all class level setup

    //The Before all will execute only once and mainly it used handle like mock db connection and its type should be in static
    //and this for class level setup and will be execute before all the test cases
    @BeforeAll
    public static void init() {

        System.out.println("Before All the Test cases");
    }
    //@BeforeEach runs before every test method, and is used to set up test data, mocks, or environment needed for that test
    @BeforeEach
    public void initForEachTestCases() {
        System.out.println("Before Each Test cases");
    }

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

    //Here private method in service can be tested using java reflections
    @Test
    void validatePrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // these are the way to access private method in class using java reflections
        //here validateProduct is method name in product service class
        //in getDeclaredMethod it has two parameter one is mehtod name and second one argument type
        // which used in that particular method

        Method validateMethod = ProductService.class.getDeclaredMethod("validateProduct", String.class);

        validateMethod.setAccessible(true);
        Boolean productName = (Boolean) validateMethod.invoke(productService, "Headphone");

        assertTrue(productName);

    }

    @Test
    void validateNegativeCasePrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // these are the way to access private method in class using java reflections
        //here validateProduct is method name in product service class
        //in getDeclaredMethod it has two parameter one is mehtod name and second one argument type
        // which used in that particular method

        Method validateMethod = ProductService.class.getDeclaredMethod("validateProduct", String.class);

        validateMethod.setAccessible(true);
        Boolean productName = (Boolean) validateMethod.invoke(productService, "");

        assertFalse(productName);

    }

    // Here, doNothing() is used when the method return type is void,
    // and we want to test whether the method was executed as expected.
    // Common use cases include delete and update operations where no return value is expected.
    // If the method returns a value, then use when(...).thenReturn(...) instead.

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteByProductId(1);
        productService.deleteProduct(1);
        verify(productRepository,times(1)).deleteByProductId(1);
    }

    //@AfterAll @AfterEach cleanup level
    @AfterEach
    public void cleanUp() {

        System.out.println("It is used for resetting the values after each test cases performed");
    }
    @AfterAll
    public static void Destory() {

        System.out.println("It will execute only once ,After all the Test cases, for example we destroy Db Connection ");

    }

}