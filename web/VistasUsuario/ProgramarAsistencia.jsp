<%-- 
    Document   : ProgramarAsistencia
    Created on : 18/04/2021, 12:51:42 PM
    Author     : berserker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title> Progamar Asistencia</title>
    </head>
    <body>
        <header>

            <div>
                <img src="../imagen/heard.png" alt="" class="img-nav"/></div>
            <nav>    
                <a href="../indexUsuario.jsp" class="nav-enlace">Calendario</a>
                <a href="ProgramarAsistencia.jsp" class="nav-enlace">Programar asistencia  </a>
                <a href="Ponencias.jsp" class="nav-enlace">Ponencias </a>
                <a href="Ponentes.jsp" class="nav-enlace">Ponentes</a> 
            </nav>

        </header>

        <div id="margen">

            <h1>Registro a Ponecias </h1>


            <form action="#" method="post" id="contact_form">
                <div class="name">
                    <label for="name"></label>
                    <input type="text" placeholder="Nombre completo" name="name" id="name_input" required>
                </div>
                <div class="email">
                    <label for="email"></label>
                    <input type="email" placeholder="Email" name="email" id="email_input" required>
                </div>
                <div class="telephone">
                    <label for="name"></label>
                    <input type="text" placeholder="Numero celular " name="telephone" id="telephone_input" required>
                </div>
                <div class="subject">
                    <label for="subject"></label>
                    <select placeholder="Ponencias disponibles " name="subject" id="subject_input" required>
                        <option disabled hidden selected>Ponencias disponibles</option>
                        <option>WEB</option>
                        <option>BACKEND</option>
                        <option>FRONEND</option>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Inscribirme" id="form_button" />
                </div>
            </form><!-- // End form -->




        </div>
    </body>
</html>
