package com.example.Tomato.service;

import com.example.Tomato.dto.Categorydto;
import com.example.Tomato.exception.deleteprohibited.DeleteProhibited;
import com.example.Tomato.exception.imageuploadfailed.ImageUploadFailed;
import com.example.Tomato.exception.notfound.NotFoundException;
import com.example.Tomato.model.Category;
import com.example.Tomato.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.Tomato.constant.Constant.CATEGORY_DIRECTORY;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<Categorydto> getAllCategories(){
            List<Category> categoryList = categoryRepo.findAll();
            return modelMapper.map(categoryList , new TypeToken<List<Categorydto>>(){}.getType());
    }


    public Categorydto addCategory(Categorydto categoryDto) {
        Category savedCategory = categoryRepo.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(savedCategory, Categorydto.class);
    }


    public Categorydto getCategoryById(String id) {
        Category category= categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return modelMapper.map(category, Categorydto.class);
    }

    public Categorydto deleteCategoryByID(String id){
        Category category= categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            categoryRepo.delete(category);
            String url = category.getPhotoUrl();
            int lastIndex = url.lastIndexOf('/');
            String filename = url.substring(lastIndex + 1);

            Path path = Paths.get(CATEGORY_DIRECTORY + filename);

            Files.delete(path);
            return modelMapper.map(category, Categorydto.class);

        } catch (Exception e) {
            throw new DeleteProhibited();
        }
    }

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Saving picture for user ID: {}", id);
        System.out.println(id);
        Categorydto category = getCategoryById(id);
        String photoUrl = photoFunction.apply(id, file);
        category.setPhotoUrl(photoUrl);
        categoryRepo.save(modelMapper.map(category, Category.class));
        return photoUrl;
    }


    private final Function<String, String> fileExtenstion = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse("png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename=id+fileExtenstion.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(CATEGORY_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.isDirectory(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/public/category/photo/"+id+fileExtenstion
                            .apply(image.getOriginalFilename()))
                    .toUriString();

        } catch (Exception exception) {
            throw new ImageUploadFailed();
        }
    };
}
