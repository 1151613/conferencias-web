<%-- 
    Document   : DiasConferencia
    Created on : 24/05/2021, 06:44:47 PM
    Author     : berserker
--%>

<%@page import="Negocio.Negocio"%>
<%@page import="DTO.Dia"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/style.css" rel="stylesheet">

        <link href="../css/dataTable/jquery.dataTables.min.css" rel="stylesheet">
        <link href="../css/dataTable/dataTables.bootstrap4.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Bootstrap core CSS -->
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <link href="../css/dataTable/buttons.dataTables.min.css" rel="stylesheet">
        <!-- JQuery -->
        <script type="text/javascript" src="../js/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/general.js"></script>
        <title>Dias de conferencia</title>
    </head>

  <%
        Negocio n = new Negocio();
        List<Dia> com = n.Dias(); 
  int x = Integer.parseInt(request.getParameter("conferencia"));%>


    <body>




        <header>

            <div>
                <img src="../imagen/heard.png" alt="" class="img-nav"/></div>
            <nav>     
                <h3>  Dias de conferencia</h3>
            </nav>

        </header>


            <div id="margen">
                           
                          <%
                           for (Dia c : com) {

                           if(c.getIdConferencia().getIdConferencia()== x){
                        %>
                           <form name="formRegistro" id="formRegistro"  action="programarCronograma.jsp" method="post">

                        <input type="hidden" id="dia" name="dia" required value="<%= c.getIdDia()%>" > 
                        <input type="hidden" id="conferencia" name="conferencia" required value="<%= x %>" > 
                            <button  id="form_button"  type="submit" > <%= c.getDia().toLocaleString() %> </button>
                      
                           </form>
                
                            <%
                        }}
                    %>  
                        
                    
                     <input id="form_button" type="submit" value="Volver" onClick="history.go(-1);" />
            </div>


            <script src="../js/dataTable/jquery.dataTables.min.js"></script>
            <script src="../js/dataTable/dataTables.buttons.min.js"></script>
            <script src="../js/dataTable/buttons.flash.min.js"></script>
            <script src="../js/dataTable/jszip.min.js"></script>
            <script src="../js/dataTable/pdfmake.min.js"></script>
            <script src="../js/dataTable/vfs_fonts.js"></script>
            <script src="../js/dataTable/buttons.html5.min.js"></script>
            <script src="../js/dataTable/buttons.print.min.js"></script>
            <script type="text/javascript">
                $(document).ready(function () {
                    
                    $('#sesiones').DataTable ();
                });
            </script>

    </body>
</html>
