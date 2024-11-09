package com.ben.mid_term.servlet;

import com.ben.mid_term.dao.LocationDao;
import com.ben.mid_term.model.UserLocation;
import com.ben.mid_term.model.LocationType;
import com.ben.mid_term.util.LocationPopulator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class LocationServlet extends HttpServlet {
    private LocationDao locationDao = new LocationDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // Get provinces
//        List<UserLocation> provinces = locationDao.getProvinces();
//        if (provinces != null && !provinces.isEmpty()) {  // Corrected condition
//            request.setAttribute("provinces", provinces);
//
//            // Handle selected province and display districts
//            String provinceId = request.getParameter("province");
//            if (provinceId != null && !provinceId.isEmpty()) {
//                UserLocation selectedProvince = locationDao.findLocationById(UUID.fromString(provinceId));
//                List<UserLocation> districts = locationDao.getChildLocations(selectedProvince.getLocationId());
//                request.setAttribute("districts", districts);
//                request.setAttribute("selectedProvince", provinceId);
//            }
//
//            // Handle selected district and display sectors
//            String districtId = request.getParameter("district");
//            if (districtId != null && !districtId.isEmpty()) {
//                UserLocation selectedDistrict = locationDao.findLocationById(UUID.fromString(districtId));
//                List<UserLocation> sectors = locationDao.getChildLocations(selectedDistrict.getLocationId());
//                request.setAttribute("sectors", sectors);
//                request.setAttribute("selectedDistrict", districtId);
//            }
//        } else {
//            // Handle empty province list (optional, to display a message)
//            request.setAttribute("message", "No provinces found.");
//        }
//        // Forward to JSP
//        request.getRequestDispatcher("location-populator.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle population request
        String action = request.getParameter("action");
        if ("populate".equals(action)) {
            LocationPopulator.populateLocations();
            request.setAttribute("message", "Locations populated successfully!");
        }

        // Redirect to GET to refresh the form
        response.sendRedirect("LocationServlet");
    }
}
