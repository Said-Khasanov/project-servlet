package com.tictactoe.controller;

import com.tictactoe.model.*;
import com.tictactoe.service.GameService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GameService gameService = extractGameService(session);

        int index = getSelectedIndex(req);
        gameService.doMoves(index);

        GameState gameState = gameService.getGameState();

        switch (gameState) {
            case CONTINUES -> {
                List<Sign> data = gameService.getGame().getFieldData();
                session.setAttribute("gameService", gameService);
                session.setAttribute("data", data);
                resp.sendRedirect("/index.jsp");
            }
            case HAS_WINNER -> {
                session.setAttribute("winner", gameService.getWinner());
                List<Sign> data = gameService.getGame().getFieldData();
                session.setAttribute("data", data);
                resp.sendRedirect("/index.jsp");
            }
            case SKIP_MOVES -> {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(req, resp);
            }
            case DRAW -> {
                session.setAttribute("draw", true);
                resp.sendRedirect("/index.jsp");
            }
        }
    }

    private GameService extractGameService(HttpSession session) {
        Object fieldAttribute = session.getAttribute("gameService");
        if (fieldAttribute.getClass() != GameService.class) {
            session.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (GameService) fieldAttribute;
    }

    private int getSelectedIndex(HttpServletRequest req) {
        String stringIndex = req.getParameter("click");
        boolean isNumeric = stringIndex.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(stringIndex) : 0;
    }
}
