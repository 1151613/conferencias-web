<%-- 
    Document   : sesion
    Created on : 24/05/2021, 11:10:15 PM
    Author     : berserker
--%>

<%@page import="DTO.Ponencia"%>
<%@page import="java.util.List"%>
<%@page import="Negocio.Negocio"%>
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
        <title>Ponencias de la sesion</title>
    </head>

    
    <%     Integer id =Integer.parseInt( request.getParameter("sesion"));
        Negocio n = new Negocio();
        List<Ponencia> com = n.ponencia();     
  %>
    <body>




        <header>

            <div>
                <img src="../imagen/heard.png" alt="" class="img-nav"/></div>
            <nav>     
                <h3>  Administrar Ponencia</h3>
            </nav>

        </header>


            <div id="margen">
                           
        <table id="sesiones" >
            <thead>
                        <tr>
                            <td>Titulo</td>
                            <td>Descripcion</td>
                            <td>Tipo</td>
                            <td>Speaker</td>
                            <td>Titulo articulo</td>
                            <td>Autor articulo</td>
                            
                           
                        </tr>
            </thead>
                <tbody>
                   
                        <%
                           for (Ponencia c : com) {

                           if(c.getIdSesion().getIdSesion() ==id){
                        %>
                        <tr>
                       
                        <td><%= c.getTitulo() %></td>
                        <td><%= c.getDescripcion() %></td>
                        <td><%= c.getTipo() %></td>
                        <td><%= c.getSpeaker() %></td>
                        <td><%= c.getTituloArticulo() %></td>
                        <td><%= c.getAutorArticulo() %></td>
                       
                    </tr>

                    <%
                        
                        }}
                    %>
        </tbody>
</table>  
                    
   
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
                    
                    $('#sesiones').DataTable ({
                     dom: 'Bfrtip',
                        buttons: [
                            'copy', 'csv', 'excel', 'pdf', 'print'
                        ],
                    });
                });
            </script>

    </body>
</html>

