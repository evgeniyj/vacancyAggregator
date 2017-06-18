package vacancyAggregator;


import vacancyAggregator.model.HHStrategy;
import vacancyAggregator.model.Model;
import vacancyAggregator.model.MoikrugStrategy;
import vacancyAggregator.model.Provider;

import vacancyAggregator.view.HtmlView;
import vacancyAggregator.view.View;




/**
 * Created by Evgeniy on 27.11.2016.
 */
public class Aggregator
{
    public static void main(String[] args)
    {

        Provider provider = new Provider(new MoikrugStrategy());
        View view = new HtmlView();
        Model model = new Model(view, provider);
        Controller controller = new Controller(model);
        view.setController(controller);
        ((HtmlView)view).update(provider.getJavaVacancies("Dnepropetrovsk"));


    }
}