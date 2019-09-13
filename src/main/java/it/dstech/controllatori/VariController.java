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
	
	@PostMapping("/stato")
	public String stati (@RequestParam(name="i")String continente , Model model) throws ClassNotFoundException, SQLException
	{
		List <Stati> stati = new ArrayList<Stati>();
		stati.addAll(conn.prendiStati(continente));
		model.addAttribute("stati", stati);
		return "stato";
	}
	

	
	
	
	
	
}
