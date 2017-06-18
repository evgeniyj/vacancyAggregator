package vacancyAggregator;

import vacancyAggregator.model.Model;
import vacancyAggregator.model.Provider;
import vacancyAggregator.vo.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Evgeniy on 27.11.2016.
 */
public class Controller
{
    private Model model;

    public Controller(Model model)
    {
        if (model == null) {
            throw  new IllegalArgumentException();
        }
        this.model = model;
    }

    public void onCitySelect(String cityName)
    {
        model.selectCity(cityName);
    }
}