package vacancyAggregator.view;


import vacancyAggregator.Controller;
import vacancyAggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;

/**
 * Created by Evgeniy on 30.11.2016.
 */
public class HtmlView implements View
{
    Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replace(".", "/") + "/" + "vacancies.html";
    
    @Override
    public void update(List<Vacancy> vacancies)
    {
        try
        {
            updateFile(getUpdatedFileContent(vacancies));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies)
    {
        String result;
        Document doc = null;
        try
        {
            doc = getDocument();

            Elements elements = doc.getElementsByAttributeValue("class", "vacancy template");
            Element elementTemplate = elements.get(0).clone();

            elementTemplate.removeAttr("style");
            result = elementTemplate.toString().replaceAll("vacancy template", "vacancy");

            elementTemplate = Jsoup.parse(result, "", Parser.xmlParser());

            doc.getElementsByAttributeValue("class", "vacancy").remove();

            for (Vacancy vacancy : vacancies)
            {
                Element vacancyElement = elementTemplate.clone();
                vacancyElement.getElementsByClass("city").get(0).text(vacancy.getCity());
                vacancyElement.getElementsByClass("companyName").get(0).text(vacancy.getCompanyName());
                vacancyElement.getElementsByClass("salary").get(0).text(vacancy.getSalary());

                vacancyElement.getElementsByTag("a").get(0).text(vacancy.getTitle());
                vacancyElement.getElementsByTag("a").get(0).attr("href",vacancy.getUrl());

                doc.getElementsByAttributeValue("class", "vacancy template").get(0).before(vacancyElement.outerHtml());
            }
            result = doc.html();

        } catch (Exception e)
        {
            e.printStackTrace();
            result = "Some exception occurred";
        }

        return result;
    }

    private void updateFile(String string)
    {
        File file = new File(filePath);
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public Document getDocument() throws IOException
    {
        File input = new File(filePath);
        Document doc = Jsoup.parse(input, "UTF-8");

        return doc;
    }

}
Используй окно Expression Evaluation, думаю, оно тебе понадобится в следующем пункте.
 */