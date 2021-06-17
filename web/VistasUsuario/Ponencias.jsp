<%-- 
    Document   : Ponencias
    Created on : 18/04/2021, 12:52:27 PM
    Author     : berserker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Ponencias</title>
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
            
            <h1> 
             Lista de Ponencias
            </h1>
            
            <div id="pricing-table" class="clear">
                <% String nombre ="Nombre";  
                    String ponente ="Ponentes";    
                    String temas ="Temas";    
                
                for (int i = 0; i <3 ; i++) {%>
                        
                
              
    <div class="plan" id="plann">
          
        <h3 >fecha <%=i%></h3>      
        <ul >
            <form name="formRegistro" id="formRegistro"  action="DatosPonencia.jsp" method="post">
                <li name="nombre" ><b >Nombre </b> <span name="nombre"><%=nombre+i%></span> </li>
                
            <li><b > <%=ponente %>  </b>........ ......... ....... .......</li>
            <li><b ><%=temas%></b> ........ ......... ....... .......</li>
             <input type="submit" value=" Informacion " id="form_button"   >   
             </form>
        </ul> 
    </div>    <% } %>
                
                 
            
            </div>
        </div>
        
        
        
    </body>
</html>
