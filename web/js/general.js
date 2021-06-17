/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function nuevoAjax()
{ 
    var xmlhttp=false;
    try	{
            // Creacion del objeto AJAX para navegadores no IE Ejemplo:Mozilla, Safari 
            xmlhttp=new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e){
            try	{
                    // Creacion del objet AJAX para IE
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }catch(E){
                    if (!xmlhttp && typeof XMLHttpRequest!='undefined') xmlhttp=new XMLHttpRequest();
            }
    }
    return xmlhttp; 
}

function verDetalles(dia){
   aleatorio=Math.random(); 
     
   ajax = nuevoAjax();   

    parametros = "dia="+dia+"&aleatorio="+aleatorio;  
    url = "programarCronograma.jsp";  
    ajax.open("POST",url,true);
    ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');   
    ajax.send(parametros);

    ajax.onreadystatechange=function()
    {
      if (ajax.readyState==4)
      {
        if (ajax.status == 200)
        {          	       	 
            document.getElementById("sesiones").innerHTML = ajax.responseText;
        }
        else
        {    
            document.getElementById("sesiones").innerHTML = ajax.responseText;
        }
      } 
      else
      {
        document.getElementById("sesiones").innerHTML = "<img src='imagenes/cargando.gif' width='100' height='100' />";
      }
    }; 
}

function EliminarConferencia (id){
   
}
