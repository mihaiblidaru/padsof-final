package gui.controllers;

import java.sql.SQLException;

import app.clases.MiVacaPiso;
import app.clases.users.Rol;

public class Controller {
	private final MiVacaPiso model;
	
	public Controller() throws SQLException {
		this.model = MiVacaPiso.getInstance();
	}
	
	public Rol login(String user, String password) throws SQLException {
		return model.login(user, password);
	}
	

}
