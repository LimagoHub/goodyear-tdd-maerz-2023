package de.goodyear.gui.presenter;


import de.goodyear.gui.Euro2DollarRechnerView;
import de.goodyear.model.Euro2DollarRechner;

public interface Euro2DollarPresenter {

	// Setterinjection fuer Dependecies
	Euro2DollarRechnerView getView();

	void setView(Euro2DollarRechnerView view);

	Euro2DollarRechner getModel();

	void setModel(Euro2DollarRechner model);
	// Injection Ende

	// Presenter Methoden
	void onRechnen();  // Rechnenevent aus der Maske

	void onBeenden(); // Beendenevent aus der Maske

	// Codebehind
	void onPopulateItems(); // Maske initialisieren
	
	void updateRechnenActionState(); // Nicht beachten

}