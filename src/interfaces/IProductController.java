package interfaces;

import java.util.List;
import models.Product;
import exceptions.ObjectNotFoundException;

public interface IProductController {
    public int addProduct(Product product);
    public List<Product> getProducts();
    public Product getProductById(int id) throws ObjectNotFoundException;
    public List<Product> getProductsByName(String name);
    public List<Product> getProductsByPrice(double start, double end);
    public void updateProduct(int id, Product updatedProduct);
    public void deleteProduct(int id) throws ObjectNotFoundException;
}
