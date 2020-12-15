/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entity.Etat;
import Entity.Ticket;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbuffeteau
 */
public class FonctionsMetier implements IMetier
{
    @Override
    public User GetUnUser(String login, String mdp)
    {
        User U = null;
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select  idUser, nomUser, prenomUser, statutUser from users where loginUser='"+login+"' and pwdUser ='"+mdp+"';");
            rs=stm.executeQuery();
            rs.next();
            U=new User(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return U;           
    }

    @Override
    public ArrayList<Ticket> GetAllTickets()
    {
        ArrayList<Ticket> lesTicket=new ArrayList<>();
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select  idTicket, nomTicket, dateTicket, nomEtat from tickets, etats where idEtat=numEtat;");
            rs=stm.executeQuery();
            while(rs.next()){
             Ticket ti=new Ticket(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
             lesTicket.add(ti);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return lesTicket;  
        
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsByIdUser(int idUser)
    {
        
         ArrayList<Ticket> lesTicket=new ArrayList<>();
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select  idTicket, nomTicket, dateTicket, nomEtat from tickets, etats where idEtat=numEtat and numUser="+idUser+";");
            rs=stm.executeQuery();
            while(rs.next()){
             Ticket ti=new Ticket(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
             lesTicket.add(ti);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return lesTicket; 
    }

    @Override
    public void InsererTicket(int idTicket, String nomTicket, String dateTicket, int idUser, int idEtat) 
    {
        try {
            Connection cnx= ConnexionBDD.getCnx();
            PreparedStatement stm=cnx.prepareStatement("insert into tickets (idTicket, nomTicket, dateTicket, numUser, numEtat) values("+idTicket+",'"+nomTicket+"','"+dateTicket+"',"+idUser+","+idEtat+");");
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    @Override
    public void ModifierEtatTicket(int idTicket, int idEtat) 
    {
        try {
            Connection cnx= ConnexionBDD.getCnx();
            PreparedStatement stm=cnx.prepareStatement("update tickets set numEtat="+idEtat+" where idTicket="+idTicket+";");
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<User> GetAllUsers()
    {
        
         ArrayList<User> lesUsers=new ArrayList<>();
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select idUser, nomUser, prenomUser, statutUser from users;");
            rs=stm.executeQuery();
            while(rs.next()){
             User U=new User(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
             lesUsers.add(U);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return lesUsers;   
    }

    @Override
    public int GetLastIdTicket()
    {
        int LastTicket=0;
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select count(idTicket) from tickets ;");
            rs=stm.executeQuery();
            rs.next();
            LastTicket= rs.getInt(1)+1;
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return LastTicket;           
        
    }

    @Override
    public int GetIdEtat(String nomEtat)
    {
      int idEtat=0;
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select idEtat  from etats where nomEtat='"+nomEtat+"';");
            rs=stm.executeQuery();
            rs.next();
            idEtat=rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return idEtat;   
       
        
    }

    @Override
    public ArrayList<Etat> GetAllEtats()
    {
        
         ArrayList<Etat> lesEtat=new ArrayList<>();
        try {
            Connection cnx= ConnexionBDD.getCnx();
            ResultSet rs ;
            PreparedStatement stm=cnx.prepareStatement("select * from etats;");
            rs=stm.executeQuery();
            while(rs.next()){
             Etat Ets=new Etat(rs.getInt(1),rs.getString(2));
             lesEtat.add(Ets);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return lesEtat;   
    }
}
