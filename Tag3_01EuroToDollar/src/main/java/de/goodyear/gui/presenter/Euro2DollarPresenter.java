package de.goodyear.gui.presenter;


import de.goodyear.gui.Euro2DollarRechnerView;
import de.goodyear.model.Euro2DollarRechner;

public interface Euro2DollarPresenter {

	Euro2DollarRechnerView getView();

	void setView(Euro2DollarRechnerView view);

	Euro2DollarRechner getModel();

	void setModel(Euro2DollarRechner model);

	void onRechnen();

	void onBeenden();

	void onPopulateItems();
	
	void updateRechnenActionState(); // Nicht beachten

}