/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Conexion.Conexion;
import DAO.ConferenciaJpaController;
import DAO.DiaJpaController;
import DAO.PersonaJpaController;
import DAO.PonenciaJpaController;
import DAO.SesionJpaController;
import DAO.TipoPersonaJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Conferencia;
import DTO.Dia;
import DTO.Persona;
import DTO.Ponencia;
import DTO.Sesion;
import java.util.List;

/**
 *
 * @author berserker
 */
public class Negocio {

    public Negocio() {
    }
   
    public List<Conferencia> Conferencias() {
        Conexion c =Conexion.getConexion();
        ConferenciaJpaController producto = new ConferenciaJpaController(c.getBd());
        return producto.findConferenciaEntities();
    }
    
    public List<Sesion> sesiones() {
        Conexion c =Conexion.getConexion();
        SesionJpaController producto = new SesionJpaController(c.getBd());
        return producto.findSesionEntities();
    }
     public List<Ponencia> ponencia() {
        Conexion c =Conexion.getConexion();
        PonenciaJpaController producto = new PonenciaJpaController(c.getBd());
        return producto.findPonenciaEntities();
    }
      public List<Dia> Dias() {
        Conexion c =Conexion.getConexion();
        DiaJpaController producto = new DiaJpaController(c.getBd());
        return producto.findDiaEntities();
    }
      public void eliminarConferencia(Integer id) throws IllegalOrphanException, NonexistentEntityException{
           Conexion c =Conexion.getConexion(); 
           ConferenciaJpaController producto = new ConferenciaJpaController(c.getBd());
           producto.destroy(id);
      }
      
      public Integer ingreso (String contrasena , String email ){
         Integer x=0 ; 
         Conexion c =Conexion.getConexion();  
          PersonaJpaController producto = new PersonaJpaController(c.getBd());
        List<Persona> p =producto.findPersonaEntities();
        
          for (Persona persona : p) {
              System.out.println(persona.getContrasena()+" "+persona.getCorreo());
              System.out.println(contrasena+ " "+email);
              if(  persona.getCorreo().equalsIgnoreCase(email)){
                 
         if( persona.getContrasena().equalsIgnoreCase(contrasena)){
            
             
           x=persona.getTipo().getIdTipo();
           return x ;
         }}
             
         }
         return x ; 
      }
     
      
      public boolean registro (Integer id, String correo, String contrasena, String nombres, 
              String apellidos, String tipoDocumento, int telefono, String insti, int tip ){
           Conexion c =Conexion.getConexion();
            PersonaJpaController producto = new PersonaJpaController(c.getBd());
             TipoPersonaJpaController tipo = new TipoPersonaJpaController(c.getBd());
            Persona persona = new Persona(id,  correo,  contrasena, nombres, apellidos,  tipoDocumento,  telefono,  insti);
            persona.setTipo(tipo.findTipoPersona(tip));
            
            try {
            producto.create(persona);
                System.out.println("si se hizo ");
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }    
       
      }
      
    public static void main(String[] args) {
        Negocio con = new Negocio();
       
    }
    
   
}
