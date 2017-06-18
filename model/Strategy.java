package vacancyAggregator.model;

import vacancyAggregator.vo.Vacancy;

import java.util.List;

/**
 * Created by Evgeniy on 27.11.2016.
 */
public interface Strategy
{
    public List<Vacancy> getVacancies(String searchString);
}
