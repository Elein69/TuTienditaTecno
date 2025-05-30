package com.packt.webstore.controller;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/market")
@Controller
public class ProductController {
@RequestMapping ("/products")
public String list (Model model) {
    model.addAttribute("products",productService.getAllProducts());
    return "products";
}

@Autowired
private ProductRepository productRepository;

@Autowired
private ProductService productService;

    @RequestMapping("/update/stock")
    public String updateStock(Model model) {
        productService.updateAllStock();
        return "redirect:/market/products";
    }
    
    @RequestMapping("/products/{category}")
    public String getProductBycategory(Model model,@PathVariable("category") String productCategory) {
        model.addAttribute("products",productService.getProductsByCategory(productCategory));
        return "products";
    }
    
    @RequestMapping("/products/filter/{params}")
    public String getProductsByFilter(@MatrixVariable(pathVar="params")
        Map<String,List<String>> filterParams,Model model){
        model.addAttribute("products", productService.getProductsByFilter(filterParams));
        return "products";
    }
    
    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String productId, Model model){
        model.addAttribute("product", productService.getProductById(productId));
        return "product";
    }
    
    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String getAddNewProductForm(Model model) {
        Product newProduct = new Product();
        model.addAttribute("newProduct", newProduct);
        return "addProduct";
    }
 
    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String processAddNewProductForm(@ModelAttribute("newProduct") 
    @Valid Product newProduct, BindingResult result, HttpServletRequest request) {
        
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attemting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        
        MultipartFile productImage = newProduct.getProductImage();
        String rootDirectory =  request.getSession().getServletContext().getRealPath("/");
        if (productImage!=null && !productImage.isEmpty()) {
            try{
                productImage.transferTo(new File(rootDirectory+"resources\\images"+newProduct.getProductId() + ".png"));
            } catch (Exception e){
                throw new RuntimeException ("Product Image saving failed", e);
                
            }
            
        }
                
        if (result.hasErrors()) { return "addProduct";}
        
        
        productService.addProduct(newProduct);
        return "redirect:/market/products";
    }
    @InitBinder
    /*se colocan las caracteriscas del 
    objeto que va a ser vinculadas con el formulario*/
    public void initialiseBinder(WebDataBinder binder) {
            binder.setAllowedFields("productId",
                "name",
                "unitPrice",
                "description",
                "manufacturer",
                "category",
                "unitsInStock",
                "condition",
                "productImage",
                "languge");
    }

}
