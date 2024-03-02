package com.tictactoe;

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
        Field field = extractField(session);

        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);

        if (currentSign != Sign.EMPTY) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(req, resp);
            return;
        }

        field.getField().put(index, Sign.CROSS);

        if (checkWin(resp, session, field)) {
            return;
        }

        int emptyFieldIndex = field.getEmptyFieldIndex();
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (checkWin(resp, session, field)) {
                return;
            }
        } else {
            session.setAttribute("draw", true);
            List<Sign> data = field.getFieldData();
            session.setAttribute("data", data);
            resp.sendRedirect("/index.jsp");
            return;
        }

        List<Sign> data = field.getFieldData();
        session.setAttribute("field", field);
        session.setAttribute("data", data);

        resp.sendRedirect("index.jsp");
    }

    private Field extractField(HttpSession session) {
        Object fieldAttribute = session.getAttribute("field");
        if (fieldAttribute.getClass() != Field.class) {
            session.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAttribute;
    }

    private int getSelectedIndex(HttpServletRequest req) {
        String stringIndex = req.getParameter("click");
        boolean isNumeric = stringIndex.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(stringIndex) : 0;
    }

    private boolean checkWin(HttpServletResponse resp, HttpSession session, Field field) throws IOException {
        Sign winner = field.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            session.setAttribute("winner", winner);
            List<Sign> data = field.getFieldData();
            session.setAttribute("data", data);
            resp.sendRedirect("/index.jsp");
            return true;
        }
        return false;
    }
}
