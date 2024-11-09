/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.util;
import com.ben.mid_term.model.LocationType;
import com.ben.mid_term.model.UserLocation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.FileReader;

/**
 *
 * @author benji
 */

public class LocationPopulator {
    public static void populateLocations() {
        try (FileReader reader = new FileReader("C:\\Users\\benji\\Documents\\NetBeansProjects\\mid_testing_24305\\src\\main\\java\\com\\ben\\mid_term\\util\\rwandaLocations.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray data = jsonObject.getAsJsonArray("data");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            try {
                // Process provinces
                for (JsonElement provinceElement : data) {
                    JsonObject provinceObject = provinceElement.getAsJsonObject();
                    for (String provinceName : provinceObject.keySet()) {
                        // Create province
                        UserLocation province = createLocation(session, provinceName, null, LocationType.PROVINCE);
                        
                        // Process districts
                        JsonArray districts = provinceObject.getAsJsonArray(provinceName);
                        for (JsonElement districtElement : districts) {
                            JsonObject districtObject = districtElement.getAsJsonObject();
                            for (String districtName : districtObject.keySet()) {
                                // Create district
                                UserLocation district = createLocation(session, districtName, province, LocationType.DISTRICT);
                                
                                // Process sectors
                                JsonArray sectors = districtObject.getAsJsonArray(districtName);
                                for (JsonElement sectorElement : sectors) {
                                    JsonObject sectorObject = sectorElement.getAsJsonObject();
                                    for (String sectorName : sectorObject.keySet()) {
                                        // Create sector
                                        UserLocation sector = createLocation(session, sectorName, district, LocationType.SECTOR);
                                        
                                        // Process cells
                                        JsonArray cells = sectorObject.getAsJsonArray(sectorName);
                                        for (JsonElement cellElement : cells) {
                                            JsonObject cellObject = cellElement.getAsJsonObject();
                                            for (String cellName : cellObject.keySet()) {
                                                // Create cell
                                                UserLocation cell = createLocation(session, cellName, sector, LocationType.CELL);
                                                
                                                // Process villages
                                                JsonArray villages = cellObject.getAsJsonArray(cellName);
                                                for (JsonElement villageElement : villages) {
                                                    String villageName = villageElement.getAsString();
                                                    // Create village
                                                    createLocation(session, villageName, cell, LocationType.VILLAGE);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Flush and clear session periodically to manage memory
                        session.flush();
                        session.clear();
                    }
                }
                
                transaction.commit();
                System.out.println("Locations populated successfully!");
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static UserLocation createLocation(Session session, String name, UserLocation parent, LocationType type) {
        UserLocation location = new UserLocation();
        location.setLocationName(name);
        location.setLocationType(type);
        location.setParentLocation(parent);
        
        // Generate location code based on hierarchy
        String parentCode = (parent != null) ? parent.getLocationCode() : "";
        String code = generateLocationCode(parentCode, name, type);
        location.setLocationCode(code);
        
        session.persist(location);
        session.flush(); // Ensure the entity is persisted immediately
        return location;
    }
    
    private static String generateLocationCode(String parentCode, String name, LocationType type) {
        // Create a simple code based on the first 3 letters of the name (or full name if shorter)
        String nameCode = name.length() > 3 ? name.substring(0, 3).toUpperCase() : name.toUpperCase();
        
        if (parentCode.isEmpty()) {
            // For provinces (top level)
            return type.toString().charAt(0) + "-" + nameCode;
        } else {
            // For other levels, append to parent code
            return parentCode + "-" + nameCode;
        }
    }
}
