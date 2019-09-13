package it.dstech.databasici;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.dstech.stato.Citta;
import it.dstech.stato.Stati;

public class Db
{
	public static Connection connessionedb() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root" , "root");
		return conn;
	}
	public List <Stati> prendiStatiEuropei () throws ClassNotFoundException, SQLException
	{
		List <Stati> stati = new ArrayList <Stati>();
		String query = "SELECT Code, name FROM world.country where continent='Europe';";
		PreparedStatement prep = connessionedb().prepareStatement(query);
		ResultSet result = prep.executeQuery();
		while (result.next())
		{
			Stati stato = new Stati();
			stato.setCodice(result.getString(1));
			stato.setNome(result.getString(2));
			stati.add(stato);
		}
		return stati;
	}
	
	public List <Citta> prendiCitta (String codiceStato) throws ClassNotFoundException, SQLException
	{
		List <Citta> citta = new ArrayList <Citta>();
		String query = "select name, population from city where CountryCode = (?);";
		PreparedStatement prep = connessionedb().prepareStatement(query);
		prep.setString(1, codiceStato);
		ResultSet result = prep.executeQuery();
		while (result.next())
		{
			Citta city = new Citta();
			city.setNome(result.getString(1));
			city.setPopolazione(result.getInt(2));
			citta.add(city);
		}
		return citta;
	}
	
	public List <Stati> prendiStati (String continente) throws ClassNotFoundException, SQLException
	{
		List <Stati> stati = new ArrayList <Stati>();
		String query = "SELECT name, code FROM world.country where continent = (?);";
		PreparedStatement prep = connessionedb().prepareStatement(query);
		prep.setString(1, continente);
		ResultSet result = prep.executeQuery();
		while (result.next())
		{
			Stati stato = new Stati();
			stato.setNome((result.getString(1)));
			stato.setCodice((result.getString(2)));
			stati.add(stato);
		}
		return stati;
	}
	
	public List <String> prendiContinenti () throws ClassNotFoundException, SQLException
	{
		List <String> continenti = new ArrayList <String>();
		String query = "SELECT distinct(continent) FROM world.country;";
		PreparedStatement prep = connessionedb().prepareStatement(query);
		ResultSet result = prep.executeQuery();
		while (result.next())
		{
			continenti.add(result.getString(1));
		}
		return continenti;
	}
	
	
}
