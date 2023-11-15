package com.fda.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ProductDto;
import com.fda.app.dto.ProductFilterWithPaginationDto;
import com.fda.app.dto.ProductResponseDto;
import com.fda.app.dto.ProductSearchFilterResponseDto;
import com.fda.app.dto.ProductSearchFilterWithPaginationDto;
import com.fda.app.dto.ProductUpdateDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Category;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.CategoryRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.ProductRepositoryCustom;
import com.fda.app.service.IProductService;
import com.fda.app.utility.Utility;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private FoodProductRepository foodProductRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepositoryCustom foodProductRepositoryCustom;
	@Value("${stripe.api.key}")
	private String stripeApiKey;

	@Override
	public void addProduct(@Valid ProductDto productDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (foodProductRepository.existsByproductName(productDto.getProductName())) {
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_NAME_ALREADY_REGISTERED)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if ((user.getRole() == 0 || user.getRole() == 2)) {
			Product product = null;
			Price price = null;
			try {
				Stripe.apiKey = stripeApiKey;
				ProductCreateParams paramsProduct = ProductCreateParams.builder().setActive(true)
						.setName(productDto.getProductName()).build();
				 product = Product.create(paramsProduct);
				PriceCreateParams paramsa = PriceCreateParams.builder().setCurrency("usd")
						.setUnitAmount(Long.parseLong(productDto.getPrice().toString())).setProduct(product.getId())
						.build();

				 price = Price.create(paramsa);
			} catch (StripeException e) {

			}
			FoodProduct foodProduct = customMapper.fdaDtoTofda(productDto);
			if(product!=null&&price!=null) {
			foodProduct.setStripeProductId(product.getId());
			foodProduct.setStripePriceId(price.getId());
			}
			foodProduct.setCreatedAt(new Date());
			Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
			if (category.isPresent()) {
				foodProduct.setCategoryName(category.get().getName());
			}
			Optional<Restaurant> restaurant = restaurantRepository.findById(productDto.getRestaurantId());
			if (restaurant.isPresent()) {
				foodProduct.setRestaurantName(restaurant.get().getRestaurantName());
			}
			save(foodProduct);
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_ADD_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(foodProduct);

		}
	}

	@Override
	public void save(FoodProduct product) {
		foodProductRepository.save(product);
	}

	@Override
	public void getAllProductListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<FoodProduct> productList = foodProductRepository.findAll();
		apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(productList);
	}

	@Override
	public void getProductById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<FoodProduct> product = foodProductRepository.findById(id);
		if (product.isPresent()) {
			ProductResponseDto foodProductResponseDto = new ProductResponseDto();
			foodProductResponseDto.setProductId(product.get().getId());
			foodProductResponseDto.setDescription(product.get().getDescription());
			foodProductResponseDto.setproductImage(product.get().getproductImage());
			foodProductResponseDto.setproductName(product.get().getproductName());
			foodProductResponseDto.setproductSize(product.get().getproductSize());
			foodProductResponseDto.setPrice(product.get().getPrice());
			foodProductResponseDto.setActive(product.get().getActive());
			Optional<Restaurant> restaurant = restaurantRepository.findById(product.get().getRestaurantId());
			if (restaurant.isPresent()) {
				foodProductResponseDto.setRestaurantAddress(restaurant.get().getRestaurantAddress());
				foodProductResponseDto.setRestaurantId(restaurant.get().getId());
				foodProductResponseDto.setRestaurantName(restaurant.get().getRestaurantName());
			}
			Optional<Category> category = categoryRepository.findById(product.get().getCategoryId());
			if (category.isPresent()) {
				foodProductResponseDto.setCategoryId(category.get().getId());
				foodProductResponseDto.setCategoryName(category.get().getName());
			}
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK)
					.withData(foodProductResponseDto);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void updateProduct(@Valid ProductUpdateDto productDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() == 0 || user.getRole() == 2) {
			Optional<FoodProduct> product = foodProductRepository.findById(id);
			if (product.isPresent()) {
				product.get().setproductSize(productDto.getProductSize());
				product.get().setproductName(productDto.getProductName());
				product.get().setPrice(productDto.getPrice());
				product.get().setDescription(productDto.getDescription());
				product.get().setUpdatedAt(new Date());
				product.get().setproductImage(productDto.getProductImage());
				save(product.get());
				apiResponseDtoBuilder.withMessage(Constants.PRODUCT_UPDATED).withStatus(HttpStatus.OK)
						.withData(product);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void isActiveProduct(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<FoodProduct> product = foodProductRepository.findById(id);
		if (product.isPresent()) {
			product.get().setActive(true);
			save(product.get());
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_ACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void isInactiveProduct(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<FoodProduct> product = foodProductRepository.findById(id);
		if (product.isPresent()) {
			product.get().setActive(false);
			save(product.get());
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_DEACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getProductListByFilterWithPagination(ProductFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto productList = foodProductRepositoryCustom
				.getproductListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESS).withData(productList);

	}

	@Override
	public void productSearchFilterWithPagination(
			ProductSearchFilterWithPaginationDto productSearchFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<ProductSearchFilterResponseDto> productList = foodProductRepositoryCustom
				.getproductSearchListByFilterWithPagination(productSearchFilterWithPaginationDto);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESS).withData(productList);

	}

	@Override
	public void getAllProductListByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<FoodProduct> foodProduct = foodProductRepository.findByRestaurantId(restaurantId);
		if (!foodProduct.isEmpty()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(foodProduct);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ID_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

}
