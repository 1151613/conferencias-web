<%-- 
    Document   : indexUsuario
    Created on : 13/06/2021, 05:10:11 PM
    Author     : berserker
--%>


<%@page import="java.util.List"%>
<%@page import="Negocio.Negocio"%>
<%@page import="DTO.Conferencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet">

        <link href="css/dataTable/jquery.dataTables.min.css" rel="stylesheet">
        <link href="css/dataTable/dataTables.bootstrap4.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/dataTable/buttons.dataTables.min.css" rel="stylesheet">
        <!-- JQuery -->
        <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/general.js"></script>
        <title>conferencias</title>
    </head>

    <%
        Negocio n = new Negocio();
        List<Conferencia> com = n.Conferencias(); 
       
    %>

    <body>




         <header>
           
            <div>
                <img src="imagen/heard.png" alt="" class="img-nav"/></div>
            <nav>    
                <a href="indexUsuario.jsp" class="nav-enlace">Calendario</a>
                <a href="VistasUsuario/ProgramarAsistencia.jsp" class="nav-enlace">Programar asistencia  </a>
                <a href="VistasUsuario/Ponencias.jsp" class="nav-enlace">Ponencias </a>
                <a href="VistasUsuario/Ponentes.jsp" class="nav-enlace">Ponentes</a> 
            </nav>
        
        </header>

            <div id="margen">

                <table id="tblProductos">
                    <thead>
                        <tr>
                            <td>Nombre Conferencia</td>
                            <td>URL</td>
                            <td>Fecha Inicio</td>
                            <td>Fecha Fin</td>
                            <td>programar</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Conferencia c : com) {


                        %>
                        <tr>
                    <form name="formRegistro" id="formRegistro"  action="VistasUsuario/Programa.jsp" method="post">

                        <td><%= c.getNombre()%></td>    
                        <td><%= c.getUrl()%></td>  
                        <td><%= c.getFechaInicio().toLocaleString()%></td>    
                        <td><%= c.getFechaFin().toLocaleString() %></td>

                        <td> <input type="hidden" id="conferencia" name="conferencia" required value="<%= c.getIdConferencia()%>" >
                          
                            <button  id="form_button"  type="submit" > Informaccion </button>
                        </td>
                    </form>
                    </tr>

                    <%
                        }
                    %>
                    </tbody>
                </table>

            </div>


            <script src="js/dataTable/jquery.dataTables.min.js"></script>
            <script src="js/dataTable/dataTables.buttons.min.js"></script>
            <script src="js/dataTable/buttons.flash.min.js"></script>
            <script src="js/dataTable/jszip.min.js"></script>
            <script src="js/dataTable/pdfmake.min.js"></script>
            <script src="js/dataTable/vfs_fonts.js"></script>
            <script src="js/dataTable/buttons.html5.min.js"></script>
            <script src="js/dataTable/buttons.print.min.js"></script>
            <script type="text/javascript">
                $(document).ready(function () {
                    $('#tblProductos').DataTable({
                        dom: 'Bfrtip',
                        buttons: [
                            'copy', 'csv', 'excel', 'pdf', 'print'
                        ],
                    });
                });
            </script>

    </body>
</html>


