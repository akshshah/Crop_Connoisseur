package com.example.cropconnoisseur.Utils;

import com.example.cropconnoisseur.Model.CropDisease;

import java.util.HashMap;
import java.util.Map;

public class AddCropDisease {

    private static Map<String, CropDisease> diseaseMap;

    public AddCropDisease() {
    }
    
    public static Map<String,CropDisease> getDiseaseMap(){
        if(diseaseMap == null){
            diseaseMap = new HashMap<>();
            initialize();
        }
        return diseaseMap;
    }

    private static void initialize() {

        diseaseMap.put("Tomato bacterial spot",new CropDisease("Tomato","Tomato bacterial spot",true,"Xanthomonas vesicatoria, Xanthomonas euvesicatoria, Xanthomonas gardneri, and Xanthomonas perforans.","A plant with bacterial spot cannot be cured. Remove symptomatic plants from the field or greenhouse to prevent the spread of bacteria to healthy plants. Burn, bury or hot compost the affected plants and DO NOT eat symptomatic fruit."));

        diseaseMap.put("Tomato early blight", new CropDisease("Tomato","Tomato early blight",true,"Alternaria tomatophila and Alternaria solani.","Fungicides,Organic Treatmentsand Cultural Controls. Rotate Your Crops. Purge Nightshades and Volunteer Tomato Plants. Keep Your Plants Dry. Stake Your Plants. Remove Infected Plants."));

        diseaseMap.put("Tomato late blight", new CropDisease("Tomato","Tomato late blight",true,"oomycete Phytophthora infestans","Use fungicide sprays based on mandipropamid, chlorothalonil, fluazinam, mancozeb to combat late blight. Fungicides are generally needed only if the disease appears during a time of year when rain is likely or overhead irrigation is practiced."));

        diseaseMap.put("Tomato mosaic virus", new CropDisease("Tomato","Tomato mosaic virus",true,"The disease is spread into the plant via small wounds caused by mechanical injury, insect chewing, and grafting. Leftover plant debris is the most common contagion.","Fungicides will NOT treat this viral disease,Plant resistant varieties when available or purchase transplants from a reputable source and Do NOT save seed from infected crops."));

        diseaseMap.put("Apple scab",new CropDisease("Apple","Apple scab",true,"fungus Venturia inaequalis","The best fungicides available for scab control at this time of the early season are the broad-spectrum protectants: Captan and the EBDCs."));

        diseaseMap.put("Apple Black_rot",new CropDisease("Apple","Apple Black_rot",true,"fungus Diplodia seriata (syn Botryosphaeria obtusa)","Black rot can infect dead wood and freshly pruned wounds. Trim your trees when they are dormant, disinfect tools between cuts, and burn the branches and leaves. Spray fungicides"));

        diseaseMap.put("Apple Cedar_apple_rust", new CropDisease("Apple","Apple Cedar_apple_rust",true,"fungal pathogen Gymnosporangium juniperi-virginianae","Focus on purging infected leaves and fruit from around your tree. Spraying apple trees with copper can be done to treat cedar apple rust and prevent other fungal infections"));

        diseaseMap.put("Cercospora/Gray leaf spot", new CropDisease("Corn","Cercospora/Gray leaf spot",true,"Poor airflow, low sunlight, overcrowding, improper soil nutrient and irrigation management, and poor soil drainage can all contribute to the propagation of the disease.","There are some fungicides available to help manage Cercospora leaf spot. Products containing chlorothalonil, myclobutanil or thiophanate-methyl are most effective when applied prior to or at the first sign of leaf spots."));

        diseaseMap.put("Common rust", new CropDisease("Corn","Common rust",true,"fungus Puccinia sorghi","Apply a foliar fungicide early in the season if rust is bound to spread rapidly due to the weather conditions. Numerous fungicides are available for rust control."));

        diseaseMap.put("Northern Leaf Blight",new CropDisease("Corn","Northern Leaf Blight", true,"fungus Setosphaeria turcica. Symptoms usually appear first on the lower leaves.","Northern corn leaf blight can be managed through the use of resistant hybrids. Additionally, timely planting can be useful for avoiding conditions that favor the disease. The tan lesions of northern corn leaf blight are slender and oblong tapering at the ends ranging in size between 1 to 6 inches."));

        diseaseMap.put("Potato Early Blight",new CropDisease("Potato","Potato Early Blight",true,"fungal pathogen Alternaria solani.","Treatment of early blight includes prevention by planting potato varieties that are resistant to the disease; late maturing are more resistant than early maturing varieties. Avoid overhead irrigation and allow for sufficient aeration between plants to allow the foliage to dry as quickly as possible."));

        diseaseMap.put("Potato Late Blight",new CropDisease("Potato","Potato Late Blight",true,"fungus Phytophthora infestans.","The severe late blight can be effectively managed with prophylactic spray of mancozeb at 0.25% followed by cymoxanil+mancozeb or dimethomorph+mancozeb at 0.3% at the onset of disease and one more spray of mancozeb at 0.25% seven days after application of systemic fungicides."));

        diseaseMap.put("Brinjal bacterial spot",new CropDisease("Brinjal/Eggplant","Brinjal bacterial spot",true,"Cercospora melongenae is a fungal plant pathogen that causes leaf spot on eggplant.","Crop roatatoin,fertilizer,bio-fungicides(QST 73)."));

        diseaseMap.put("Brinjal early blight",new CropDisease("Brinjal/Eggplant","Brinjal early blight",true,"Alternaria solani","Fungicides based on or combinations of azoxystrobin, pyraclostrobin, difenoconazole, boscalid, chlorothalonil, fenamidone, maneb, mancozeb, trifloxystrobin, and ziram can be used. Rotation of different chemical compounds is recommended."));

        diseaseMap.put("Brinjal late blight",new CropDisease("Brinjal/Eggplant","Brinjal late blight",true,"fungus Phytophthora infestans","Water in the early morning hours,Plant resistant cultivars when available and Remove volunteers from the garden prior"));

        diseaseMap.put("Brinjal mosaic virus",new CropDisease("Brinjal/Eggplant","Brinjal mosaic virus",true,"xanthocarpum","Do NOT save seed from infected crops,Plant resistant varieties when available or purchase transplants from a reputable source and Fungicides will NOT treat this viral disease."));

        diseaseMap.put("Capsicum Bacterial_spot",new CropDisease("Capsicum","Capsicum Bacterial_spot",true,"It overwinters in infected debris and weeds such as nightshade. The bacterium is spread by splashing water and mechanical means such as pickers and/or picking equipment, particularly when plants are wet.","Select resistant varieties,Purchase disease-free seed and transplants and Treat seeds by soaking them for 2 minutes in a 10% chlorine bleach solution (1 part bleach; 9 parts water)."));

        diseaseMap.put("Strawberry Leaf scorch",new CropDisease("Strawberry","Strawberry Leaf scorch",true,"These lesions often have definite reddish-purple to rusty-brown borders that surround a necrotic area. Lesion size and appearance often are influenced by the host variety and the ambient temperature. The leaf spots sometimes cause severe problems, often depending on the variety planted.","Since this fungal pathogen overwinters on the fallen leaves of infected plants, proper garden sanitation is key. This includes the removal of infected garden debris from the strawberry patch, as well as the frequent establishment of new strawberry transplants."));


    }


}
