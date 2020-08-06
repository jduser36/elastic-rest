package com.ohnet.elasticex.data_load;

import com.ohnet.elasticex.entity.Car;

import java.util.List;

public interface CarService {

	List<String> BRANDS = List.of("Toyota", "Honda", "Ford", "BMW", "Mitsubishi", "Mercedes", "Audi", "VW");

	List<String> COLORS = List.of("Red", "Black", "White", "Blue", "Silver", "Grey", "Lightgrey", "Mint");

	List<String> TYPES = List.of("Sedan", "SUV", "MPV", "Hatchback", "Convertible");

	List<String> ADDITIONAL_FEATURES = List.of("GPS", "Alarm", "Sunroof", "Media player", "Leather seats", "Navigation");

	List<String> FUELS = List.of("Petrol", "Electric", "Hybrid");

	List<String> TIRE_MANUFACTURERS = List.of("Goodyear", "Bridgestone", "Dunlop", "Fredestein");

	Car generateCar();

}
