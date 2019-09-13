package it.dstech.controllatori;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.dstech.databasici.Db;
import it.dstech.stato.Stati;

@Controller
public class VariController
{
	@GetMapping("/")
	public String main (Model model)
	{
		Db conn = new Db();
		List <Stati> stati = new ArrayList <Stati>();
		try
		{
			stati.addAll(conn.prendiStatiEuropei());
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("stati", stati);
		return "europestates";
	}
	
	
	
}
