package de.goodyear.application;


import de.goodyear.gui.Euro2DollarRechnerView;
import de.goodyear.gui.IEuro2DollarRechnerView;
import de.goodyear.gui.presenter.Euro2DollarPresenter;
import de.goodyear.gui.presenter.IEuro2DollarPresenter;
import de.goodyear.model.Euro2DollarRechnerImpl;
import de.goodyear.model.IEuro2DollarRechner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IEuro2DollarRechner model = new Euro2DollarRechnerImpl();
		
		IEuro2DollarPresenter presenter = new Euro2DollarPresenter();
		
		IEuro2DollarRechnerView view = new Euro2DollarRechnerView();
		
		
		presenter.setView(view);
		presenter.setModel(model);
		
		view.setPresenter(presenter);
		
		presenter.onPopulateItems();
		
		view.show();

	}

}
