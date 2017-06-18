package vacancyAggregator.model;


import vacancyAggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy on 27.11.2016.
 */
public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> result = new ArrayList<>();

        try
        {
            int page = 0;
            while (true)
            {
                Document document = null;
                try
                {
                    document = getDocument(searchString, page++);
                }
                catch (IOException e1) {
                }

                if (document == null) break;

                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if (!elements.isEmpty())
                {
                    for (Element element : elements)
                    {
                        String title = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();

                        Element elementSalary = element.select("[data-qa=vacancy-serp__vacancy-compensation]").first();
                        String salary = "";
                        if (elementSalary != null) {
                            salary = elementSalary.text();
                        }

                        String city = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").select("span.searchresult__address").text();

                        String company = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").select("a[href]").text();

                        String siteName = "http://hh.ua";

                        String url = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").first().attr("abs:href");

                        Vacancy vacancy = new Vacancy();

                        vacancy.setTitle(title);
                        vacancy.setSalary(salary);
                        vacancy.setCity(city);
                        vacancy.setCompanyName(company);
                        vacancy.setSiteName(siteName);
                        vacancy.setUrl(url);

                        result.add(vacancy);

                    }
                }
            }
        }
        catch (Exception e) {
        }
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        Document doc = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
        return doc;
    }
}
2.5. Если закончились страницы с вакансиями, то выйти из цикла.
 */