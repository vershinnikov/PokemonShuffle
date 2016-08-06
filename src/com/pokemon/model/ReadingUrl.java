package com.pokemon.model;

import com.pokemon.dao.Pokemon;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 06-Aug-16.
 */
public class ReadingUrl {

    public String urlPokemon = "http://www.serebii.net/shuffle/pokemon.shtml";

    public void readConvertApp(String offline) throws Exception {
        GenericXmlApplicationContext ctx=new GenericXmlApplicationContext();
        ctx.load("beans1.xml");
        ctx.refresh();
        PokemonManipulation poks =  ctx.getBean(PokemonManipulation.class);
        int ii = 0;
        String htmlStr="";
        if(offline.equals("offline"))htmlStr = readUrl();
        else htmlStr = readUrl(urlPokemon);
        List<List<String>> PokString = ConvertHtml(htmlStr);
        downloadsPics(PokString);
        // System.out.println(PokString.size());
        for (List<String> l : PokString) {
            int POK_num = Integer.parseInt(l.get(0).substring(1));
            Document d = Jsoup.parse((String) l.get(1));
            Element imgSrc = d.getElementsByTag("img").first();
            String POK_img = imgSrc.attr("src");
            POK_img = POK_img.substring(POK_img.indexOf("pokemon"));
            String POK_Name = l.get(2);
            int POK_Lolli = 0;
            if (POK_Name.indexOf("Lollipop") > 0) {
                POK_Lolli = Integer.parseInt(POK_Name.substring(POK_Name.lastIndexOf(">") + 1).trim());
                POK_Name = POK_Name.substring(0, POK_Name.indexOf("<"));
            }
            String POK_Nature = l.get(3);
            POK_Nature = POK_Nature.substring(POK_Nature.lastIndexOf("/") + 1);
            POK_Nature = POK_Nature.substring(0, POK_Nature.indexOf("."));
            String POK_Power = l.get(4);
            String POK_Abil = l.get(5);
            boolean POK_Mega = false;
            int POK_Candy = 0;
            List<String> POK_Abil_List = new ArrayList<>();
            if (POK_Abil.indexOf("Mega Power") > -1) {
                POK_Mega = true;
                String tt = POK_Abil.substring(POK_Abil.lastIndexOf("<"));
                POK_Abil = POK_Abil.substring(0, POK_Abil.lastIndexOf("<"));
                POK_Abil = Jsoup.parse(POK_Abil).text();
                POK_Abil = POK_Abil.substring(10).trim();
                tt = Jsoup.parse(tt).text().trim();
                POK_Candy = Integer.parseInt(tt);
            } else {
                if (POK_Abil.indexOf("skillswapperth.png") > -1) {
                    for (String s : POK_Abil.split("<br[^>]*>")) {
                        POK_Abil_List.add(s.replaceAll("<[^>]+>", " ").replaceAll("  ", ""));
                    }
                    POK_Abil = POK_Abil_List.get(0);
                } else {
                    POK_Abil = Jsoup.parse(POK_Abil).text();
                    POK_Abil_List.add(POK_Abil);
                }
            }
            String POK_LocStr = l.get(6).replaceAll("[\n\r]", " ").replaceAll("<[^>]+>", " ").replace("  ", " ").trim();
            // Convert to Location object List
            String POK_Capture = "";
            if (l.size() > 7)
                POK_Capture = l.get(7).replaceAll("<br[^>]*>", "\n");
//			if (!POK_Capture.equals(""))
//				System.out.println(POK_Capture);
            Pokemon p = new Pokemon(POK_num, POK_img, POK_Name, POK_Nature, POK_Candy, POK_Lolli, POK_LocStr,
                    POK_Abil_List, POK_Abil, POK_Capture, POK_Mega, POK_Power);
            try {
                poks.addPokemon(p);
            }catch (Exception e){
                System.out.println(p);
            }
        }
    }

    private String logL(List<String> l, int option) {
        // i=0 same
        // i=1 same without /n/r
        // i=2 <.*> -> ""
        StringBuffer sb = new StringBuffer();
        if (option == 0)
            for (String s : l)
                sb.append(s + "\n");
        if (option == 1)
            for (String s : l)
                sb.append(s.replaceAll("[\\n\\r]", "") + "\n");
        if (option == 2)
            for (String s : l)
                sb.append(s.replaceAll("[\\n\\r]", "").replaceAll("<[^>]+>", " ") + "\n");

        return sb.toString() + "\n-----------------------\n";
    }

    private void downloadsPics(List<List<String>> pokString) {
        for (List<String> l : pokString) {
            Document d = Jsoup.parse((String) l.get(1));
            Element imgSrc = d.getElementsByTag("img").first();
            // System.out.println(imgSrc.attr("src"));
            readPic(urlPokemon.substring(0, urlPokemon.indexOf("/shuffle")) + imgSrc.attr("src"), false);
            // break;
        }

    }

    private List ConvertHtml(String tbl) {
        List pokStrs = new ArrayList();
        Document doc = Jsoup.parse(tbl);
        Element table = doc.getElementsByClass("dextable").last();
        // System.out.println(table.toString().substring(0, 1000));
        for (Element tr : table.children().first().children()) {
            // System.out.println(pokStrs.size() +tr.tagName());
            List pokemon = new ArrayList();
            for (Element td : tr.children())
                pokemon.add(td.html());
            pokStrs.add(pokemon);

        }
        pokStrs.remove(0);
        return pokStrs;
    }

    String readUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer sb = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine).append("\n");
        in.close();
        writeToFile(sb.toString());
        List<List<String>> PokString = ConvertHtml(sb.toString());
        downloadsPics(PokString);
        return sb.toString();
    }

    String readUrl() throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("pokemons.html"));
        StringBuffer sb = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine).append("\n");
        in.close();
        return sb.toString();
    }

    boolean readPic(String url, boolean overwrite) {
        // System.out.println(url);
        String fName = "pokemon" + url.substring(url.lastIndexOf("/"));
        File f = new File(fName);
        File fdir=new File(fName.substring(0,fName.lastIndexOf("/")));
        if (!overwrite && f.exists())
            return false;
        if(!fdir.exists())fdir.mkdirs();
        URL URL = null;
        try {
            URL = new URL(url);
            InputStream in = URL.openStream();
            byte b[] = new byte[1024];
            int c;
            OutputStream out = new FileOutputStream(f);
            while ((c = in.read(b, 0, 1024)) != -1) {
                out.write(b, 0, c);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    void writeToFile(String s) {
        try {
            PrintWriter writer = new PrintWriter("pokemons.html");
            writer.print(s);
            writer.close();
        } catch (Exception e) {
            System.out.println("error in write");
        }
    }

}
