package com.tourmanagement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.business.TourServiceImpl;
import com.tourmanagement.business.exception.EntityNotFoundException;
import com.tourmanagement.model.Tour;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@WebServlet("/tours")
public class TourServlet extends HttpServlet {

    private transient TourServiceImpl tourServiceImp;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.objectMapper.configure(
                com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        this.tourServiceImp = new TourServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Tour tour = tourServiceImp.findTourById(id);
                objectMapper.writeValue(resp.getWriter(), tour);
            } else {
                Collection<Tour> allTours = tourServiceImp.findAllTour();
                objectMapper.writeValue(resp.getWriter(), allTours);
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Ervenytelen ID formatum.");
        } catch (EntityNotFoundException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Tour newtour = objectMapper.readValue(req.getInputStream(), Tour.class);
            Tour created = tourServiceImp.createTour(newtour);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), created);
        } catch (IllegalArgumentException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Tour tour = tourServiceImp.findTourById(id);
                tourServiceImp.deleteTour(id);
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), tour);
            } else {
                sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID megadasa kotelezo a torleshez!");
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Ervenytelen ID formatum.");
        } catch (EntityNotFoundException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Tour tourUpdate = objectMapper.readValue(req.getInputStream(), Tour.class);
                tourUpdate.setId(id);
                tourServiceImp.updateTour(tourUpdate);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID megadasa kotelezo a modositashoz!");
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Ervenytelen ID formatum.");
        } catch (EntityNotFoundException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), Map.of("error", message));
    }

}
