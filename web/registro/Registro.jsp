<%-- 
    Document   : Registro
    Created on : 13/06/2021, 06:30:32 PM
    Author     : berserker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <title>Registro</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <header>

            <div>
                <img src="../imagen/heard.png" alt="" class="img-nav"/></div>
            <nav>    

               <h1>Registro de Usuario </h1>
            </nav>

        </header>

        <div id="margen">

           
            
           
            <form action="EstadoRegistro.jsp" method="post" id="contact_form">
                
                <div class="email2">
                    <label for="email"></label>
                    <input type="email" placeholder="Email" name="correo" id="email_input" required>
                </div>
                <div class="password">
                    <label for="name"></label>
                    <input type="password" placeholder="Contraseña " name="contrasena" id="name_input" required>
                </div>
                <div class="email2">
                    <label for="email"></label>
                    <input type="text" placeholder="Nombres" name="nombre" id="email_input" required>
                </div>
                <div class="password">
                    <label for="name"></label>
                    <input type="text" placeholder="Apellidos " name="apellido" id="name_input" required>
                </div>
                <div class="email2">
                    <label for="email"></label>
                    <input type="text" placeholder="Tipo de documento" name="tipoDocumento" id="email_input" required>
                </div>
                <div class="password">
                    <label for="name"></label>
                    <input type="text" placeholder="Institucion " name="insti" id="name_input" required>
                </div>
                <div class="email2">
                    <label for="email"></label>
                    <input type="number" placeholder="Numero de documento" name="id" id="email_input" required>
                </div>
                <div class="password">
                    <label for="name"></label>
                    <input type="number" placeholder="Telefono " name="telefono" id="name_input" required>
                </div>
                 <div class="subject">
                    <label for="subject"></label>
                    <select placeholder="Ponencias disponibles " name="tipo" id="subject_input" required>
                        <option disabled hidden selected>Tipo de usuario</option>
                        <option value=2 >Administrador</option>
                        <option value=3 >Ponente</option>
                        <option value=4 >Asistente</option>
                    </select>
                </div>
          
           
                <div class="submitt">
                    <input type="submit" value="Registrar" id="form_button" />
                    <input id="form_button" type="submit" value="Volver" onClick="history.go(-1);" />
                </div>
                
            </form><!-- // End form -->




        </div>

    </body>
</html>
