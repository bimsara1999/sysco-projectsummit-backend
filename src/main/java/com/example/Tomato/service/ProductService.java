package com.example.Tomato.service;

import com.example.Tomato.dto.Categorydto;
import com.example.Tomato.dto.Productdto;
import com.example.Tomato.exception.deleteprohibited.DeleteProhibited;
import com.example.Tomato.exception.notfound.NotFoundException;
import com.example.Tomato.exception.imageuploadfailed.ImageUploadFailed;
import com.example.Tomato.exception.savefailed.SavedFailed;
import com.example.Tomato.model.Category;
import com.example.Tomato.model.Product;
import com.example.Tomato.repo.CategoryRepo;
import com.example.Tomato.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.Tomato.constant.Constant.ITEM_DIRECTORY;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    public Page<Productdto> getAllProducts(int page, int size) {
        Page<Product> productsPage = productRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
        return modelMapper.map(productsPage , new TypeToken<Page<Productdto>>(){}.getType());
    }

    public Productdto addProduct(Productdto productDto) {

        Category categoty= categoryRepo.findById(productDto.getCategoryId()).orElseThrow(()->new NotFoundException(productDto.getCategoryId()));
        Product savedProduct;
        try{
            savedProduct = productRepo.save(modelMapper.map(productDto, Product.class));
        }
        catch (Exception e){
            throw new SavedFailed();
        }
        return modelMapper.map(savedProduct, Productdto.class);

    }

    public Productdto getProductById(String id) {
        Product product= productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return modelMapper.map(product, Productdto.class);
    }

    public String uploadPhoto(String id, MultipartFile file) {

        Productdto product = getProductById(id);
        String photoUrl = photoFunction.apply(id, file);
        product.setPhotoUrl(photoUrl);
        productRepo.save(modelMapper.map(product, Product.class));
        return photoUrl;
    }

    public Page<Productdto> getProductsByCategory(int page,int size,String id){

        Category categoty= categoryRepo.findById(id).orElseThrow(()->new NotFoundException(id));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> productsPage = productRepo.findProductsByCategory(id, pageRequest);
        return modelMapper.map(productsPage , new TypeToken<Page<Productdto>>(){}.getType());
    }

    public Page<Productdto> getProductsByName(int page,int size,String name){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> productsPage = productRepo.findProductsByName(name, pageRequest);
        return modelMapper.map(productsPage , new TypeToken<Page<Categorydto>>(){}.getType());
    }

    public Productdto deleteProductByID(String id){
        Product product= productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            productRepo.delete(product);
            String url = product.getPhotoUrl();
            int lastIndex = url.lastIndexOf('/');
            String filename = url.substring(lastIndex + 1);

            Path path = Paths.get(ITEM_DIRECTORY + filename);

            Files.delete(path);
            return modelMapper.map(product, Productdto.class);

        } catch (Exception e) {
            throw new DeleteProhibited();
        }
    }

    private final Function<String, String> fileExtenstion = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse("png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename=id+fileExtenstion.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(ITEM_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.isDirectory(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/crisp/product/photo/"+id+fileExtenstion
                            .apply(image.getOriginalFilename()))
                    .toUriString();

        } catch (Exception exception) {
            throw new ImageUploadFailed();
        }
    };
}
