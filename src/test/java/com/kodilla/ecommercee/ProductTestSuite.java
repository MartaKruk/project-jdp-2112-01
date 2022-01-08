package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class ProductTestSuite {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testProductSave() {

        //Given
        Group group = new Group("NAME");
        Product product1 = new Product("Name", "Descr", new BigDecimal(5.50), group);

        //When
        group.getProducts().add(product1);
        groupRepository.save(group);

        //Then
        long id = product1.getId();;

        Optional<Product> readProduct = productRepository.findById(id);
        assertTrue(readProduct.isPresent());


        //CleanUp
        groupRepository.deleteAll();

    }

    @Test
    public void testFindAllProducts(){
        //Given
        Group group = new Group("NAME");
        Product product1 = new Product("Name", "Descr", new BigDecimal(5.50), group);
        Product product2 = new Product("Name2", "Descr2", new BigDecimal(5.50), group);
        Product product3 = new Product("Name2", "Descr2", new BigDecimal(5.50), group);

        //When
        groupRepository.save(group);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        int productAmount = productRepository.findAll().size();

        //Then
        assertEquals(3, productAmount);

        //Cleanup
        groupRepository.deleteAll();

    }


    @Test
    public void testDeleteProduct_ShouldSaveGroup(){
        //Given
        Group group = new Group("NAME");
        Product product1 = new Product("Name", "Descr", new BigDecimal(5.50), group);
        Product product2 = new Product("Name2", "Descr2", new BigDecimal(5.50), group);

        //When
        groupRepository.save(group);
        productRepository.save(product1);
        productRepository.save(product2);
        long product1Id = product1.getId();

        //Then
        productRepository.deleteById(product1Id);
        int productAmount = productRepository.findAll().size();

        assertEquals(1, productAmount);
        assertTrue(!groupRepository.findAll().isEmpty());

        //CleanUp
        groupRepository.deleteAll();
    }



    @Test
    public void testUpdateProduct(){
        //Given
        Group group = new Group("NAME");
        Product product1 = new Product("Name", "Descr", new BigDecimal(5.50), group);
        Product product2 = new Product("Name2", "Descr2", new BigDecimal(5.50), group);
        Product product3 = new Product("Name3", "Descr3", new BigDecimal(5.50), group);

        //When
        groupRepository.save(group);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        long product1Id = product1.getId();
        Product productToUpdate = productRepository.findById(product1Id).get();
        productToUpdate.setUnitPrice(new BigDecimal(6.00));
        BigDecimal newPrice = productToUpdate.getUnitPrice();
        productRepository.save(productToUpdate);

        //Then
        assertThat(newPrice, Matchers.comparesEqualTo(productRepository.findById(product1.getId()).get().getUnitPrice()));

        //CleanUp
        groupRepository.deleteAll();
    }

}
