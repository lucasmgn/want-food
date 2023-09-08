package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.PhotoProduct;
import com.wantfood.aplication.domain.model.Product;
import com.wantfood.aplication.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries{
	
    @Query("from Produto where restaurant.id = :restaurant and id = :product")
    Optional<Product> findById(@Param("restaurant") Long restaurantId,
                               @Param("product") Long productId);
    
    //Buscando todos os restaurants
    List<Product> findByRestaurant(Restaurant restaurant);
    
    //Buscando todos os restaurant que tem a propriedade active como true
    @Query("from Produto p where p.active = true and restaurant = :restaurant")
    List<Product> findActiveByRestaurant(Restaurant restaurant);

    //retornando photoProduto
    @Query("select f from PhotoProduct f join f.product p "
    		+ "where p.restaurant.id = :restaurantId and f.product.id = :productId")
    Optional<PhotoProduct> findPhotoProductById(Long restaurantId, Long productId);
}
