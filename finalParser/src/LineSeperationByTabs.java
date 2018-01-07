//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by Moustafa Elhagaly on 22/12/2017
// */
//public class LineSeperationByTabs {
//    public static void main(String[] args) throws IOException {
//        String regex = "(.*?)(\\t{1,3})(.+)(\\s+)(\\((\\S{4,8}?)\\))(\\s+)(\\{(.+)\\})?(\\s+)?(\\((.*)\\))?(\\s+)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
//
//        String string = "#1, Buffy		Closet Monster (2015)  [Buffy 4]  <31>\n\n"
//                + "$, Claw			\"OnCreativity\" (2012)  [Himself]\n\n"
//                + "$, Homo			Nykytaiteen museo (1986)  [Himself]  <25>\n"
//                + "			Suuri illusioni (1985)  [Guests]  <22>\n\n"
//                + "$, Steve		E.R. Sluts (2003) (V)  <12>\n\n"
//                + "$hutter			Battle of the Sexes (2017)  (as $hutter Boy)  [Bobby Riggs Fan]  <10>\n"
//                + "			NVTION: The Star Nation Rapumentary (2017)  (as $hutter Boy)  [Himself]  <1>\n"
//                + "			Secret in Their Eyes (2015)  (uncredited)  [2002 Dodger Fan]\n"
//                + "			Steve Job (2015) [Opera House Patron]\n"
//                + "			Straight Outta Compton (2015)  (uncredited)  [Club Patron/Dopeman]\n\n"
//                + "$lim, Bee Moe		Fatherhood 101 (2013)  (as Brandon Moore)  [Himself - President, Passages]\n"
//                + "			For Thy Love (2009) (d) [Thug 1]\n"
//                + "			Night of the Jackals (2009) (V)  [Trooth]\n"
//                + "			\"Idle Talk\" (2013)  (as Brandon Moore)  [Himself]\n"
//                + "			\"Idle Times\" (2012) {(#1.1)}  (as Brandon Moore)  [Detective Ryan Turner]\n"
//                + "			\"Idle Times\" (2012) {(#1.2)}  (as Brandon Moore)  [Detective Ryan Turner]\n"
//                + "			\"Idle Times\" (2012) {(#1.3)}  (as Brandon Moore)  [Detective Ryan Turner]\n"
//                + "			\"Idle Times\" (2012) {(#1.4)}  (as Brandon Moore)  [Detective Ryan Turner]\n"
//                + "			\"Idle Times\" (2012) {(#1.5)}  (as Brandon Moore)  [Detective Ryan Turner]\n"
//                + "			\"Money Power & Respect: The Series\" (2010) {Bottom Dollar (#2.7)}  [Bee Moe Slim]\n"
//                + "			\"Money Power & Respect: The Series\" (2010) {Flip Side (#2.5)}  [Bee Moe Slim]\n"
//                + "			\"Red River\" (2015)  [Victor Charles]\n"
//                + "			\"Red River\" (2015) {Under Control (#1.10)}  [Victor Charles]\n\n"
//                + "$ly, Yung		Town Bizzness Pt 3 (2014) (V)  [Yung $ly]\n"
//                + "			\"From Tha Bottom 2 Tha Top\" (2016)  [Yung $ly]\n"
//                + "			\"From Tha Bottom 2 Tha Top\" (2016) {T-Pain (#1.2)}  [Yung $ly]\n\n"
//                + "$torm, Cuntry		From the Woods: The Discovery of LYB (????)  (as Country $torm)  [Himself]\n\n"
//                + "& Davi, Bruninho	Michel na Balada (2011) (V)  [Themselves]\n"
//                + "			Michel TelÛ: Sunset (2013) (V)  [Themselves]\n"
//                + "			\"M˙sica Boa Ao Vivo\" (2014)  [Themselves]\n"
//                + "			\"Programa da Sabrina\" (2014) {(2016-01-23)}  [Themselves]\n\n"
//                + "& Dollar Furado, Caio Corsalette	\"Som Brasil\" (2007) {ZezÈ di Camargo & Luciano (#5.7)}  [Themselves]\n\n"
//                + "& Fabiano, CÈsar Menotti	Nascemos para Cantar (2010) (TV)  [Themselves]\n"
//                + "			Show da Virada (2011) (TV)  [Themselves - Performers]\n"
//                + "			Teleton 2010 (2010) (TV)  [Themselves]\n"
//                + "			\"Altas Horas\" (2000) {(2013-06-29)}  [Themselves]\n"
//                + "			\"Altas Horas\" (2000) {(2013-12-14)}  [Themselves]\n"
//                + "			\"Eliana\" (2009) {(2012-10-21)}  [Themselves]\n"
//                + "			\"Esquenta!\" (2011) {(#5.1)}  [Themselves]\n"
//                + "			\"Legend·rios\" (2010) {(2017-05-05)}  [Themselves]\n"
//                + "			\"Tamanho FamÌlia\" (2016) {(#2.9)}  [Themselves]\n"
//                + "			\"Tudo … PossÌvel\" (2005) {(2008-04-13)}  [Themselves]\n"
//                + "			\"TV Xuxa\" (2005) {(2013-01-05)}  [Themselves]\n\n"
//                + "& Grand Piano Boogie Train, Jaap Dekker	\"Barend en Van Dorp\" (1990) {(2003-03-17)}  [Themselves - Musicians]  <8>\n\n"
//                + "& Ralph, Christian	\"Roberto Carlos Especial\" (1974) {(1986-12-30)}  [Himself]\n\n"
//                + "& The Oriental Groove, Yacine	\"Els matins a TV3\" (2004) {(#8.192)}  [Themselves]\n\n"
//                + "& VinÌcius, Jo„o Bosco	Show da Virada (2011) (TV)  [Themselves - Performers]\n"
//                + "			Teleton 2009 (2009) (TV)  [Themselves]\n"
//                + "			Teleton 2012 (2012) (TV)  [Themselves]\n"
//                + "			\"Eliana\" (2009) {(2012-10-28)}  [Themselves]\n"
//                + "			\"M˙sica Boa Ao Vivo\" (2014)  [Themselves]\n\n"
//                + "' Chris Focus' Adolphus, Chris	Virtues of Habit (????)  [Himself]\n\n"
//                + "' Danilo' Jurado Jr., Jori	Lapis, Ballpen at Diploma, a True to Life Journey (2014)  [Jaime (young)]  <9>\n\n"
//                + "''El Lobo'' Rubio, Humberto	24∞ 51' Latitud Norte (2015)  [Lobo]\n"
//                + "			Episodio de Una Espera (????)  [Lobo]\n\n"
//                + "''Hooper''Kelly, Simon	Solitary (2015/I)  (as Simon P.J. Kelly)  [Matthew O Brien]\n\n"
//                + "'77			\"Tria33\" (2015) {(#1.196)}  [Themselves]\n\n"
//                + "'77 Big Smoker Pig	\"Pop r‡pid\" (2011) {Here Comes Your Man (#2.10)}  [Themselves]  <20>\n\n"
//                + "'Angel' Garrett, Christopher	A Call in the Dark (2017)  [Head Vamp]\n"
//                + "			Zombie Reign: Revelation (2016)  [Mason McDermott]\n\n"
//                + "'Ariffin, Syaiful	Desire (2014/III)  [Actor Playing Eteocles from 'Antigone']\n\n"
//                + "'Aruhane		Shaping Bamboo (1979)  [Himself - 'au paina panpipe ensemble]\n\n"
//                + "'Atu'ake, Taipaleti	When the Man Went South (2014)  [Two Palms - Ua'i Paame]  <8>\n\n"
//                + "'Avacado' Wolfe, David	The Gift (2010/I)  [Himself]\n\n"
//                + "'Az-Is' Shearer, Zamien	\"Exit\" (2014)  [Power]\n"
//                + "			\"Exit\" (2014) {I'm Home (#1.1)}  [Power]\n\n"
//                + "'babeepower' Viera, Michael	Little Angel (Angelita) (2015)  [Chico]  <9>\n"
//                + "			Mixing Nia (1998)  [Rapper]\n"
//                + "			Rock Steady (2002)  [Stevie]\n"
//                + "			W8 (Weight) (2012)  [Man In Car]\n"
//                + "			\"In the Mix\" (1991) {Hip Hop: Then & Now}  (as Michael 'Power' Viera)  [Himself]\n"
//                + "			\"Swift Justice\" (1996) {Where Were You in '72? (#1.5)}  [Young Leo]\n"
//                + "			\"The Lyricist Lounge Show\" (2000)  [Various/lyricist]\n\n"
//                + "'Bear'Boyd, Steven	The Replacements (2000)  (uncredited)  [Defensive Tackle - Washington Sentinels]\n\n"
//                + "'bliss' Vilbon, Kirlew	All Out Dysfunktion! (2016)  [Bliss]\n"
//                + "			Gook (2017)  [Bliss]\n\n"
//                + "'Boogie' Brown, Steve	Bait Shop (2008) (V)  [Fishing Competitor]  <28>\n\n"
//                + "'Bootsy' Thomas, George	My Song for You (2010)  [Cooley's Customer/Celebration Guest]  <16>\n\n"
//                + "'Boretta' Torres, Pete	Hustle Beach (2017)  [Tony]\n\n"
//                + "'Buguelo' Neto, Alderico	Babado Novo: Ver-te Mar (2007) (V)  (as Buguelo)  [Himself - Drums]\n"
//                + "			Claudia Leitte: Ao Vivo em Copacabana (2008) (V)  [Himself - Claudia Leitte Band]  <7>\n"
//                + "			Claudia Leitte: Axemusic - Ao Vivo (2014) (V)  (as Buguelo)  [Himself - Claudia Leitte Band: Drums]  <11>\n"
//                + "			Claudia Leitte: Negalora - Õntimo (2012) (V)  (as Buguelo)  [Himself - Invited Musician: Bateria]  <25>\n"
//                + "			Roupa Ac˙stico 2 (2006) (V)  [Himself]\n"
//                + "			Uau! Babado Novo Ao Vivo em Salvador (2004) (V)  [Himself - Drums]\n"
//                + "			\"Caldeir„o do Huck\" (2000) {(2013-06-29)}  (as Buguelo)  [Himself - Musician]\n\n"
//                + "'Builder Bill' Ammons, Bill	Welcome to Slab City (2012)  [Himself]\n\n"
//                + "'Byron' Sese, Joseph	Mariano Mison... NBI (1997)  [Putik's Son]  <31>\n\n"
//                + "'Calica' Ferrer, Carlos	La huella del doctor Ernesto Guevara (2013)  [Himself]\n\n"
//                + "'Cartucho' Pena, Ramon	Natas es Satan (1977)  [Nigth Club Owner]\n\n"
//                + "'Chago' Felix, Santiago	Shadow Girl (2016)  [Himself, blind street vendor]  <10>\n\n"
//                + "'Cherry, Sahel		The Emancipation of Anemone (????)  [Chorus]\n\n"
//                + "'Chincheta', Eloy	°Ja me maaten...! (2000)  [Gitano 1]  <20>\n";
//
//        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//
//        String currentLine;
//        String prevName = "";
//        BufferedWriter fw = new BufferedWriter(new FileWriter("actors.csv"));
//        BufferedReader br = new BufferedReader(new StringReader(string));
//
//        int lineNumber = 0;
//        int linesToSkip = 0;
//        while ((currentLine = br.readLine()) != null)
//        {
//            if (++lineNumber <= linesToSkip) continue;
//
//            final Matcher matcher = pattern.matcher(currentLine);
//            List<String> rows = new ArrayList<>();
//
//            if (matcher.find()) {
//                if (Objects.equals("", matcher.group(1))) {
//                    rows.add(prevName);
//                    rows.add("\t" + matcher.group(3));
//                } else {
//                    rows.add(matcher.group(1));
//                    rows.add("\t" + matcher.group(3));
//                    prevName = matcher.group(1);
//                }
//            }
//
//            StringBuilder lineString = new StringBuilder();
//            for (String row : rows) {
//                if (row != null) {
//                    row = row.trim();
//                }
////                System.out.println(lineString);
//                assert row != null;
//                lineString.append(row.trim()).append(";\t");
//            }
//            if (!lineString.toString().isEmpty()) {
//                fw.write(lineString.toString() + "\n");
//
//                System.out.println(lineString.toString().trim());
//            }
////            fw.write(lineString.toString().isEmpty() + "\n", 0, lineString.toString().length());
//
//        }
//        fw.close();
//    }
//}
