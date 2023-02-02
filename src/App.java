import controllers.ProductController;
import models.Product;

public class App {
    public static void main(String[] args) throws Exception {
        ProductController productController = new ProductController();
        // productController.getProducts().forEach(System.out::println);

        // Product product = new Product("Macbook Air M1 2020", "Product 1 description",
        // 500000.0);

        // productController.addProduct(product);

        // update product
        Product updatedMacProduct = productController.getProductById(1);
        updatedMacProduct.setName("Macbook Air M1 2020");
        updatedMacProduct.setPrice(600_000.50);

        productController.updateProduct(1, updatedMacProduct);

    }
}
