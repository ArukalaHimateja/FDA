package com.fda.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fda.app.config.GeolocationException;
import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.model.GLocation;
import com.fda.app.model.Restaurant;
import com.fda.app.repository.LocationRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.service.IGoogleLocationService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@Service
public class GoogleLocationServiceImpl implements IGoogleLocationService {

	@Value("${google.Api.Key}")
	private String googleApiKey;

	private GeoApiContext context;

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private LocationRepository locationRepository;

	@PostConstruct
	private void createGeoApiContext() {
		context = new GeoApiContext.Builder().apiKey(googleApiKey).build();
	}

	@Override
	public void searchGLoctionByRestaurantId(Long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		GeocodingResult[] results;
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if (restaurant.isPresent()) {
			try {
				results = GeocodingApi.geocode(context, restaurant.get().getRestaurantAddress()).await();
				GLocation location = parseLocationJson(results);
				location.setRestaurantId(restaurantId);
				location.setCreatedAt(new Date());
				locationRepository.save(location);
			} catch (Exception e) {
				throw new GeolocationException(e);
			}
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_DELETE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(results);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	private GLocation parseLocationJson(GeocodingResult[] results) {
		GLocation gLocation = new GLocation();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(results);
			JsonNode jsonNode = objectMapper.readTree(json);
			gLocation.setAddress(jsonNode.get(0).path("formattedAddress").asText());
			gLocation.setLocationLat(
					Double.parseDouble(jsonNode.get(0).path("geometry").path("location").path("lat").asText()));
			gLocation.setLocationLng(
					Double.parseDouble(jsonNode.get(0).path("geometry").path("location").path("lng").asText()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return gLocation;
	}

	@Override
	public void searchGLoctionByNear(Double centerLat, Double centerLon, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		String url = "http://ip-api.com/json";
		RestTemplate restTemplate = new RestTemplate();
		String jsonResponse = restTemplate.getForObject(url, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);
			List<GLocation> allLocations = locationRepository.findAll();
			List<GLocation> nearbyLocations = new ArrayList<>();
			for (GLocation location : allLocations) {
//				double distance = calculateDistance(Double.parseDouble(jsonNode.path("lat").asText()),
//						Double.parseDouble(jsonNode.path("lon").asText()), location.getLocationLat(),
//						location.getLocationLng());
				double distance = calculateDistance(centerLat, centerLon, location.getLocationLat(),
						location.getLocationLng());
				if (distance <= 10.0) { // Within a 10km radius
					nearbyLocations.add(location);
				}
			}

			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_DELETE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(nearbyLocations);
		} catch (Exception e) {
			throw new GeolocationException(e);
		}
	}

	public double calculateDistance(double lat1, Double lon1, Double lat2, Double lon2) {
		// Radius of the Earth in kilometers
		double earthRadius = 6371.0; // Change this to 3958.8 if you want the distance in miles
		// Convert degrees to radians
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);
		// Haversine formula
		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = earthRadius * c;
		return distance;

	}

//	@Override
//	public void searchGLoctionByNear(Double centerLat, Double centerLon, ApiResponseDtoBuilder apiResponseDtoBuilder) {
//		String PLACES_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
//
//		RestTemplate restTemplate = new RestTemplate();
//		String url = PLACES_API_BASE_URL + "?location=27.6136274,75.1632937&radius=100" + // 10 meters
//				"&type=restaurant" + // Search for restaurants
//				"&key=" + googleApiKey;
//		String response = restTemplate.getForObject(url, String.class);
//		System.out.println();
//	}
}
