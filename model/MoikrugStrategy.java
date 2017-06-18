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
 * Created by Evgeniy on 17.12.2016.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java+%s"; 

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> result = new ArrayList<>();

        try
        {
            int page = 1;
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

                Elements elements = document.getElementsByAttributeValue("class", "job ");
                Elements elementsMarked = document.getElementsByAttributeValue("class", "job marked");

                elements.addAll(elementsMarked);

                if (!elements.isEmpty())
                {
                    for (Element element : elements)
                    {
                        String title = element.getElementsByAttributeValue("class", "title").text();

                        String salary = element.getElementsByAttributeValue("class", "salary").text();
                        String city = element.getElementsByAttributeValue("class", "location").text();
                        String company = element.getElementsByAttributeValue("class", "company_name").select("a").text();
                        String siteName = "http://moikrug.ru";

                        Element urlDraft = element.getElementsByAttributeValue("class", "title").select("a").get(0);//.attr("href");
                        String url = urlDraft.absUrl("href");

                        String date = element.getElementsByAttributeValue("class", "date").text();


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
                else break;
            }
        }
        catch (Exception e) {
        }
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        Document doc = Jsoup.connect(String.format(URL_FORMAT, page, searchString))
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
        return doc;
    }
}
