package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	public List<Corso> getCorsoByPeriodo (int periodo) {
		
		String sql = "SELECT * FROM corso WHERE pd = ?";
		
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			
			pst.setInt(1, periodo);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(corso);
			}
			
			conn.close();
			pst.close();
			rs.close();
			return result;
						
		} catch(SQLException e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Map<Corso,Integer> getIscritti (int periodo){
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd, count(*) as n FROM corso c, iscrizione i WHERE c.codins=i.codins AND c.pd=? GROUP BY c.codins, c.crediti, c.nome, c.pd";
		Map<Corso,Integer> result = new HashMap<Corso,Integer>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, periodo);
			ResultSet res = pst.executeQuery();
			
			while(res.next()) {
				result.put(new Corso (res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd")), res.getInt("n"));
			}
			
			res.close();
			conn.close();
			pst.close();
			return result;
			
		} catch (SQLException e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}
	}
	
	

}
