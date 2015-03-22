package com.victor.control;

import java.util.List;

import com.victor.entity.InvestmentFund;
import com.victor.skandia.model.SkandiaSimulationResult;
import com.victor.view.ViewPanel;

/**
 * @author Wiktor <br/>
 *         klasa zarzadzajaca panelem<br/>
 *
 *
 */

public abstract class ViewController<T extends ViewPanel<? extends ViewController<T>>> {

	/** panel ktorym zarzadza kontroler */
	protected T panel;

	/**
	 * {@link ViewController}
	 */
	public ViewController(T panel) {
		this.panel = panel;
	}

	
	/**
	 * @param investmentFund
	 */
	public void desactivateAlgorithm(InvestmentFund investmentFund) {
	}

}
