package it.dstech.controllatori;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.dstech.databasici.Db;
import it.dstech.stato.Stati;

@Controller
public class VariController
{
	@Value("${message}")
	private String message;
	
	Db conn = new Db();
	@PostMapping("/")
	public String continenti (Model model)
	{
		List <String> continenti = new ArrayList<String>();
		try {
			continenti.addAll(conn.prendiContinenti());
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("continenti", continenti);
		model.addAttribute("message", message);
		return "continente";
	}
	
	
	
	
	
}
