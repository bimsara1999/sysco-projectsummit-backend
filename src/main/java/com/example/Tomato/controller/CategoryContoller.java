package com.example.Tomato.controller;

import com.example.Tomato.dto.Categorydto;
import com.example.Tomato.exception.deleteprohibited.DeleteProhibited;
import com.example.Tomato.exception.imagenotfound.ImageNotFound;
import com.example.Tomato.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.Tomato.constant.Constant.CATEGORY_DIRECTORY;
import static org.springframework.util.MimeTypeUtils.IMAGE_GIF_VALUE;
import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(value="/api/v1/public/category")
@CrossOrigin("http://localhost:3000")
public class CategoryContoller {

    @Autowired
    private CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<List<Categorydto>> getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Categorydto> addCategory(@Valid @RequestBody Categorydto category) {
        Categorydto addedCategory = categoryService.addCategory(category);
        return ResponseEntity.created(URI.create("")).body(addedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorydto> getCategory(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Categorydto> deleteCategory(@PathVariable(value = "id") String id) {
        ResponseEntity<Categorydto> category = null;
        try{
            category= ResponseEntity.ok().body(categoryService.deleteCategoryByID(id));
        }catch (Exception e)
        {
            throw  new DeleteProhibited();
        }
        return category;
    }


    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam MultipartFile file){

        return ResponseEntity.ok().body(categoryService.uploadPhoto(id,file));
    }

    @GetMapping(value="/photo/{filename}",produces = {IMAGE_PNG_VALUE, IMAGE_GIF_VALUE})
    public byte[] getphoto(@PathVariable("filename") String filename) throws IOException {
        try{

            return Files.readAllBytes(Paths.get(CATEGORY_DIRECTORY + filename));
        } catch (Exception exception) {
            throw new ImageNotFound();
        }
    }
}
