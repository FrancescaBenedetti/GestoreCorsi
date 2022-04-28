package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDAO {
	
	public List<Studente> getStudentiByCorso(String codins){
		String sql ="SELECT s.matricola, s.cognome, s.nome, s.CDS FROM studente s, iscrizione i WHERE s.matricola=i.matricola AND i.codins='?'";
		List<Studente> result = new ArrayList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, codins);
			ResultSet res = pst.executeQuery();
			
			while(res.next()) {
				result.add(new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"), res.getString("CDS")));
			}
			
			res.close();
			conn.close();
			pst.close();
			
			return result;
			
		}catch(SQLException e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Divisione> getDivisioneStudenti (String codins){
		
		String sql = "SELECT s.CDS, COUNT (*) as n FROM studente s, iscrizione i WHERE s.matricola = i.matricola AND i.codins='?' GROUP BY s.CDS";
		List<Divisione> result = new ArrayList<Divisione>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, codins);
			ResultSet res = pst.executeQuery();
			
			while(res.next()) {
				result.add(new Divisione (res.getString("CDS"), res.getInt("n")));
			}
			
			res.close();
			conn.close();
			pst.close();
			return result;
			
		}catch(SQLException e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}
		
	}

}
