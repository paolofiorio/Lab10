package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	
    	Author a = boxPrimo.getValue() ;
    	
    	if(a==null) {
    		txtResult.appendText("Errore: selezionare un autore\n");
    		return ;
    	}
    	
    	List<Author> coautori = model.trovaCoAutori(a) ;
    	
    	// i non-coautori sono tutti tranne i coautori e se stessi
    	List<Author> noncoautori = new ArrayList<>(model.getAutori()) ;
    	noncoautori.removeAll(coautori) ;
    	noncoautori.remove(a) ;
    	
    	// stampa coautori
    	txtResult.appendText("Coautori di "+a.toString()+":\n");
    	for(Author a1: coautori) {
    		txtResult.appendText(a1.toString()+"\n");
    	}
    	
    	// riempi ed abilita la seconda tendina
    	boxSecondo.getItems().clear();
    	boxSecondo.getItems().addAll(noncoautori) ;
    	boxSecondo.setDisable(false);
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

        boxSecondo.setDisable(true);
    }
    public void setModel(Model model) {
    	this.model=model;
    	
    	boxPrimo.getItems().addAll(this.model.getAutori()) ;
    	boxSecondo.getItems().clear() ;
    }
}
