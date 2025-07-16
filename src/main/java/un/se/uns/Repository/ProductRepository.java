package un.se.uns.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import un.se.uns.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.productId =?1")
    void deleteByProductId(int productId);
}
