package com.main.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Category;
import com.main.exceptions.ResourceNotFoundException;
import com.main.payloads.CategoryDto;
import com.main.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	public CategoryDto createCategory(CategoryDto categorydto) {
		Category cat = this.modelmapper.map(categorydto, Category.class);
		Category added = this.repo.save(cat);
		return this.modelmapper.map(added, CategoryDto.class);
	}
	
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.repo.save(cat);
		return this.modelmapper.map(updatedcat, CategoryDto.class);
	}
	
	public void deleteCategory(Integer categoryId) {
		Category cat = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		this.repo.delete(cat);
		
	}
	
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		return this.modelmapper.map(cat, CategoryDto.class);
	}
	
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.repo.findAll();
		List<CategoryDto> dtos = categories.stream().map((cat) -> this.modelmapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return dtos;
	}

}
