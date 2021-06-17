<%-- 
    Document   : inicioSesion
    Created on : 13/06/2021, 07:07:03 PM
    Author     : berserker
--%>

<%@page import="Negocio.Negocio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%  
    String email = request.getParameter("email_input");
    String contrasena = request.getParameter("name_input");
    
    Negocio n = new Negocio();
    Integer x=n.ingreso(contrasena, email);
   if(x== 4 || x==3){ 
    response.sendRedirect("../indexUsuario.jsp");  }
   
   if(x==2){
   response.sendRedirect("../indexAdministrador.jsp"); }


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        
        
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <header>
           
            <div>
                <img src="../imagen/heard.png" alt="" class="img-nav"/></div>
        </header>
         <div id="margen">
             <h1>Error Reintetalo  </h1>
        
         <input id="form_button" type="submit" value="Volver" onClick="history.go(-1);" />
        </div>
    </body>
</html>
