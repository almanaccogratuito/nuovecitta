package it.dstech.databasici;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.dstech.stato.Citta;
import it.dstech.stato.Stati;

public class Db
{
	public static Connection connessionedb() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root" , "dstech");
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
	
	public void inserisciNuovaCitta (String nomeCitta ,String distretto, String codiceStato) throws ClassNotFoundException, SQLException
	{
		Random random = new Random();
		int max = 0;
		int min = 0;
		String queryMax = "SELECT population FROM world.city where CountryCode = (?) order by (population) desc limit 1;";
		String queryMin = "SELECT population FROM world.city where CountryCode = (?) order by (population) asc limit 1;";
		String queryInserimento = "INSERT INTO `world`.`city` (`Name`, `CountryCode`, `District`, `Population`) VALUES (?, ?, ?, ?);" ;
		PreparedStatement prepMax = connessionedb().prepareStatement(queryMax);
		PreparedStatement prepMin = connessionedb().prepareStatement(queryMin);
		prepMax.setString(1, codiceStato);
		prepMin.setString(1, codiceStato);
		ResultSet result = prepMax.executeQuery();
		while (result.next())
		{
			max=result.getInt(1);
		}
		result = prepMin.executeQuery();
		while (result.next())
		{
			min=result.getInt(1);
		}
		int diff = max-min;
		int randomizzazione = random.nextInt(diff) + min;
		PreparedStatement prepIns = connessionedb().prepareStatement(queryInserimento);
		prepIns.setString(1, nomeCitta);
		prepIns.setString(2, codiceStato);
		prepIns.setString(3, distretto);
		prepIns.setInt(4, randomizzazione);
		prepIns.executeUpdate();
	}
}
