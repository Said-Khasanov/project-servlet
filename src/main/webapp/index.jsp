<%@ page import="com.tictactoe.model.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic-Tac-Toe</title>
    <link href="static/main.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Tic-Tac-Toe</h1>
    </div>

    <table>
        <tr>
            <td onclick="window.location='/logic?click=0'">${sessionScope.data.get(0).getSign()}</td>
            <td onclick="window.location='/logic?click=1'">${sessionScope.data.get(1).getSign()}</td>
            <td onclick="window.location='/logic?click=2'">${sessionScope.data.get(2).getSign()}</td>
        </tr>
        <tr>
            <td onclick="window.location='/logic?click=3'">${sessionScope.data.get(3).getSign()}</td>
            <td onclick="window.location='/logic?click=4'">${sessionScope.data.get(4).getSign()}</td>
            <td onclick="window.location='/logic?click=5'">${sessionScope.data.get(5).getSign()}</td>
        </tr>
        <tr>
            <td onclick="window.location='/logic?click=6'">${sessionScope.data.get(6).getSign()}</td>
            <td onclick="window.location='/logic?click=7'">${sessionScope.data.get(7).getSign()}</td>
            <td onclick="window.location='/logic?click=8'">${sessionScope.data.get(8).getSign()}</td>
        </tr>
    </table>

    <div class="winner">
        <hr>
        <c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
        <c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>

        <c:choose>
            <c:when test="${sessionScope.winner == CROSSES}">
                <h1>CROSSES WIN!</h1>
                <button id="restart-button" onclick="restart()">Start again</button>
            </c:when>
            <c:when test="${sessionScope.winner == NOUGHTS}">
                <h1>NOUGHTS WIN!</h1>
                <button id="restart-button" onclick="restart()">Start again</button>
            </c:when>
            <c:when test="${sessionScope.draw}">
                <h1>IT'S A DRAW</h1>
                <br>
                <button id="restart-button" onclick="restart()">Start again</button>
            </c:when>
        </c:choose>
    </div>
</div>


<script src="static/jquery-3.6.0.min.js"></script>
<script>
    function restart() {
        $.ajax({
            url: '/restart',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                location.reload();
            }
        });
    }
</script>

</body>
</html>