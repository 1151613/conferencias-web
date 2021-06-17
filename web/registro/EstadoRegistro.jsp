<%-- 
    Document   : EstadoRegistro
    Created on : 14/06/2021, 04:33:50 AM
    Author     : berserker
--%>

<%@page import="Negocio.Negocio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  
    String correo = request.getParameter("correo");
    String nombre = request.getParameter("nombre");
    String contrasena = request.getParameter("contrasena");
    String apellido = request.getParameter("apellido");
    String tipoDocumento = request.getParameter("tipoDocumento");
    String insti = request.getParameter("insti");
    Integer id = Integer.parseInt(request.getParameter("id"));
    int telefono = Integer.parseInt(request.getParameter("telefono"));
    int tipo = Integer.parseInt(request.getParameter("tipo"));
           
    
    Negocio n = new Negocio();
    
    
    if(n.registro(id,  correo,  contrasena, nombre, apellido,  tipoDocumento,  telefono,  insti,tipo)){
   if(tipo== 4 || tipo==3){ 
    response.sendRedirect("../indexUsuario.jsp");  }
   
   if(tipo==2){
   response.sendRedirect("../indexAdministrador.jsp"); }}


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
