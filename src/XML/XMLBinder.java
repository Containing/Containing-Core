package XML;

import Main.Container;
import Helpers.Vector3f;
import com.ximpleware.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class XMLBinder {
    
    public static ArrayList<Container> GetContainerList(String fileName) throws Exception{
        
        ArrayList<Container> returnList = new ArrayList<>();
        VTDGen vg = new VTDGen();
        
        // <editor-fold defaultstate="collapsed" desc="AutoPilot = new AutoPilot()">
        AutoPilot record = new AutoPilot();
        
        AutoPilot aankomst_datum_d = new AutoPilot();
        AutoPilot aankomst_datum_m = new AutoPilot();
        AutoPilot aankomst_datum_j = new AutoPilot();
        AutoPilot aankomst_tijd_van = new AutoPilot();
        AutoPilot aankomst_tijd_tot = new AutoPilot();
        AutoPilot aankomst_soort_vervoer = new AutoPilot();
        AutoPilot aankomst_bedrijf = new AutoPilot();
        AutoPilot aankomst_positie_x = new AutoPilot();
        AutoPilot aankomst_positie_y = new AutoPilot();
        AutoPilot aankomst_positie_z = new AutoPilot();
        
        AutoPilot eigenaar_naam = new AutoPilot();
        AutoPilot eigenaar_containernr = new AutoPilot();
        
        AutoPilot vertrek_datum_d = new AutoPilot();
        AutoPilot vertrek_datum_m = new AutoPilot();
        AutoPilot vertrek_datum_j = new AutoPilot();
        AutoPilot vertrek_tijd_van = new AutoPilot();
        AutoPilot vertrek_tijd_tot = new AutoPilot();
        AutoPilot vertrek_soort_vervoer = new AutoPilot();
        AutoPilot vertrek_bedrijf = new AutoPilot();
        
        AutoPilot afmetingen_l = new AutoPilot();
        AutoPilot afmetingen_b = new AutoPilot();
        AutoPilot afmetingen_h = new AutoPilot();
        
        AutoPilot gewicht_leeg = new AutoPilot();
        AutoPilot gewicht_inhoud = new AutoPilot();
        
        AutoPilot inhoud_naam = new AutoPilot();
        AutoPilot inhoud_soort = new AutoPilot();
        AutoPilot inhoud_gevaar = new AutoPilot();
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=".selectXPath("location");">
        record.selectXPath("/recordset/record");
        
        aankomst_datum_d.selectXPath("aankomst/datum/d"); 
        aankomst_datum_m.selectXPath("aankomst/datum/m"); 
        aankomst_datum_j.selectXPath("aankomst/datum/j");
        aankomst_tijd_van.selectXPath("aankomst/tijd/van");
        aankomst_tijd_tot.selectXPath("aankomst/tijd/tot");
        aankomst_soort_vervoer.selectXPath("aankomst/soort_vervoer");
        aankomst_bedrijf.selectXPath("aankomst/bedrijf");
        aankomst_positie_x.selectXPath("aankomst/positie/x");
        aankomst_positie_y.selectXPath("aankomst/positie/y");
        aankomst_positie_z.selectXPath("aankomst/positie/z");
        
        eigenaar_naam.selectXPath("eigenaar/naam");
        eigenaar_containernr.selectXPath("eigenaar/containernr");
        
        vertrek_datum_d.selectXPath("vertrek/datum/d"); 
        vertrek_datum_m.selectXPath("vertrek/datum/m"); 
        vertrek_datum_j.selectXPath("vertrek/datum/j");
        vertrek_tijd_van.selectXPath("vertrek/tijd/van");
        vertrek_tijd_tot.selectXPath("vertrek/tijd/tot");
        vertrek_soort_vervoer.selectXPath("vertrek/soort_vervoer");
        vertrek_bedrijf.selectXPath("vertrek/bedrijf");
                
        afmetingen_l.selectXPath("afmetingen/l");
        afmetingen_b.selectXPath("afmetingen/b");
        afmetingen_h.selectXPath("afmetingen/h");
        
        gewicht_leeg.selectXPath("gewicht/leeg");
        gewicht_inhoud.selectXPath("gewicht/inhoud");
        
        inhoud_naam.selectXPath("inhoud/naam");
        inhoud_soort.selectXPath("inhoud/soort");
        inhoud_gevaar.selectXPath("inhoud/gevaar");
        // </editor-fold>
        
        if (vg.parseFile(fileName,false)){
            VTDNav vn = vg.getNav();
            
            // <editor-fold defaultstate="collapsed" desc=".bind(vn);">
            record.bind(vn);
            
            aankomst_datum_d.bind(vn);
            aankomst_datum_m.bind(vn);
            aankomst_datum_j.bind(vn);
            aankomst_tijd_van.bind(vn);
            aankomst_tijd_tot.bind(vn);
            aankomst_soort_vervoer.bind(vn);
            aankomst_bedrijf.bind(vn);
            aankomst_positie_x.bind(vn);
            aankomst_positie_y.bind(vn);
            aankomst_positie_z.bind(vn);

            eigenaar_naam.bind(vn);
            eigenaar_containernr.bind(vn);

            vertrek_datum_d.bind(vn);
            vertrek_datum_m.bind(vn);
            vertrek_datum_j.bind(vn);
            vertrek_tijd_van.bind(vn);
            vertrek_tijd_tot.bind(vn);
            vertrek_soort_vervoer.bind(vn);
            vertrek_bedrijf.bind(vn);

            afmetingen_l.bind(vn);
            afmetingen_b.bind(vn);
            afmetingen_h.bind(vn);

            gewicht_leeg.bind(vn);
            gewicht_inhoud.bind(vn);

            inhoud_naam.bind(vn);
            inhoud_soort.bind(vn);
            inhoud_gevaar.bind(vn);
            // </editor-fold>
            
            int counter = 0;
            while(record.evalXPath()!=-1){
                Container container = new Container("id" + counter++);

                // <editor-fold defaultstate="collapsed" desc="Container.set()">
                container.setArrival(
                        container.df.parse(
                                aankomst_datum_d.evalXPathToString() + " " +
                                aankomst_datum_m.evalXPathToString() + " " +
                                aankomst_datum_j.evalXPathToString() + " " +
                                aankomst_tijd_van.evalXPathToString().split("\\.")[0] + ":" +
                                aankomst_tijd_van.evalXPathToString().split("\\.")[1]
                        ), 
                        container.df.parse(
                                aankomst_datum_d.evalXPathToString() + " " +
                                aankomst_datum_m.evalXPathToString() + " " +
                                aankomst_datum_j.evalXPathToString() + " " +
                                aankomst_tijd_tot.evalXPathToString().split("\\.")[0] + ":" +
                                aankomst_tijd_tot.evalXPathToString().split("\\.")[1]
                        ), 
                        Container.TransportType.valueOf(aankomst_soort_vervoer.evalXPathToString()), 
                        aankomst_bedrijf.evalXPathToString(), 
                        new Vector3f(   
                                Float.parseFloat(aankomst_positie_x.evalXPathToString()), 
                                Float.parseFloat(aankomst_positie_y.evalXPathToString()), 
                                Float.parseFloat(aankomst_positie_z.evalXPathToString())
                        )
                );
                
                container.setOwnerInformation(
                        eigenaar_naam.evalXPathToString(), 
                        Integer.parseInt(eigenaar_containernr.evalXPathToString())
                );
                
                container.setDeparture(
                        container.df.parse(
                                vertrek_datum_d.evalXPathToString() + " " +
                                vertrek_datum_m.evalXPathToString() + " " +
                                vertrek_datum_j.evalXPathToString() + " " +
                                vertrek_tijd_van.evalXPathToString().split("\\.")[0] + ":" +
                                vertrek_tijd_van.evalXPathToString().split("\\.")[1]
                        ), 
                        container.df.parse(
                                vertrek_datum_d.evalXPathToString() + " " +
                                vertrek_datum_m.evalXPathToString() + " " +
                                vertrek_datum_j.evalXPathToString() + " " +
                                vertrek_tijd_tot.evalXPathToString().split("\\.")[0] + ":" +
                                vertrek_tijd_tot.evalXPathToString().split("\\.")[1]
                        ), 
                        Container.TransportType.valueOf(vertrek_soort_vervoer.evalXPathToString()), 
                        vertrek_bedrijf.evalXPathToString()
                );
                
                container.setDimension(
                        new Vector3f(
                                Float.parseFloat(afmetingen_b.evalXPathToString().replace("'", "")), 
                                Float.parseFloat(afmetingen_l.evalXPathToString().replace("'", "")), 
                                Float.parseFloat(afmetingen_h.evalXPathToString().replace("'", ""))
                        )
                );
                
                container.setWeightInformation(
                        Integer.parseInt(gewicht_leeg.evalXPathToString()), 
                        Integer.parseInt(gewicht_inhoud.evalXPathToString())
                );
                
                container.setContentInformation(
                        inhoud_naam.evalXPathToString(), 
                        inhoud_soort.evalXPathToString(), 
                        inhoud_gevaar.evalXPathToString()
                );
                // </editor-fold>
                
                returnList.add(container);
            }
            record.resetXPath();
            System.out.println("Proccesed: " + returnList.size());
        }
        return returnList.size() > 0 ? returnList : null;
    }
}
