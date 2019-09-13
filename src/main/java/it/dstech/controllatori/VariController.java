package it.dstech.controllatori;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.dstech.databasici.Db;
import it.dstech.stato.Citta;
import it.dstech.stato.Stati;

@Controller
public class VariController
{
	@Value("${welcome.message}")
	private String message;
	
	Db conn = new Db();
	@GetMapping("/")
	public String continenti (Model model) throws ClassNotFoundException, SQLException
	{
		List <String> continenti = new ArrayList<String>();
		continenti.addAll(conn.prendiContinenti());		
		model.addAttribute("continenti", continenti);
		model.addAttribute("message", message);
		return "continente";
	}
	
	@GetMapping("/stato")
	public String statoSimple(Model model) throws ClassNotFoundException, SQLException
	{
		model.addAttribute("message", message);
		return "stato";
	}
	
	@PostMapping("/stato")

	public String stati (@RequestParam(name="i")String continente , Model model) throws ClassNotFoundException, SQLException
	{
		List <Stati> stati = new ArrayList<Stati>();
		stati.addAll(conn.prendiStati(continente));
		model.addAttribute("message", message);
		model.addAttribute("stati", stati);
		return "stato";
	}
	
	@PostMapping("citta")
	public String citta (@RequestParam(name="stato")String codiceStato , Model model) throws ClassNotFoundException, SQLException
	{
		List<Citta> city = new ArrayList <Citta>();
		//questo metodo ti fa tornare la lista di tutte le citta
		city.addAll(conn.prendiCitta(codiceStato));
		model.addAttribute(codiceStato);
		model.addAttribute("message", message);
		model.addAttribute("citta", city);
		return "ultimaP";
	}
	
	@PostMapping("nuovaCitta")
	public String nuovacitta (@RequestParam(name="")String nomeCitta , @RequestParam(name="")String distretto, @RequestParam(name="")String codiceStato , Model model ) throws ClassNotFoundException, SQLException
	{
		conn.inserisciNuovaCitta(nomeCitta , distretto, codiceStato);
		message = "citta' aggiunta correttamente, bentornati Marco e Simone , volete aggiungere una nuova citta' ? divertitevi ! :)" ;
		model.addAttribute("message" , message);
		
		List <String> continenti = new ArrayList<String>();
		continenti.addAll(conn.prendiContinenti());		
		model.addAttribute("continenti", continenti);
		return "continente";
	}
	

	

	
	
	
	
	
}
