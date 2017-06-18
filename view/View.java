package vacancyAggregator.view;

import vacancyAggregator.Controller;
import vacancyAggregator.vo.Vacancy;

import java.util.List;

/**
 * Created by Evgeniy on 30.11.2016.
 */
public interface View
{
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
