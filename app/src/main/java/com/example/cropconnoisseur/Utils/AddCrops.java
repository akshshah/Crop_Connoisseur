package com.example.cropconnoisseur.Utils;

import com.example.cropconnoisseur.Model.Crop;
import com.example.cropconnoisseur.R;

import java.util.ArrayList;

public class AddCrops {

    private static ArrayList<Crop> allCrops;
    private static ArrayList<Crop> modelCrops;

    public AddCrops(){
    }

    public static ArrayList<Crop> getAllCrops(){
        if(allCrops == null){
            allCrops = new ArrayList<>();
            initializeAllCrops();
        }
        return allCrops;
    }

    public static ArrayList<Crop> getModelCrops(){
        if(modelCrops == null){
            modelCrops = new ArrayList<>();
            initializeModelCrops();
        }
        return modelCrops;
    }

    private static void initializeModelCrops() {

        //Tomato
        {
            modelCrops.add(new Crop("Tomato",R.drawable.tomato,null,null,null,null,null,null));
        }
        //Apple
        {
            modelCrops.add(new Crop("Apple",R.drawable.apple,null,null,null,null,null,null));
        }
        //Corn
        {
            modelCrops.add(new Crop("Maize/Corn",R.drawable.maize,null,null,null,null,null,null));
        }
        //Grapes
        {
            modelCrops.add(new Crop("Grapes",R.drawable.grapes,null,null,null,null,null,null));
        }
        //Orange
//        {
//            modelCrops.add(new Crop("Orange",R.drawable.oranges,null,null,null,null,null,null));
//        }
        //Bell Pepper
        {
            modelCrops.add(new Crop("Capsicum",R.drawable.bellpepper,null,null,null,null,null,null));
        }
        //Potato
        {
            modelCrops.add(new Crop("Potato",R.drawable.potato,null,null,null,null,null,null));
        }
        //Strawberry
        {
            modelCrops.add(new Crop("Strawberry",R.drawable.strawberry,null,null,null,null,null,null));
        }
        //Eggplant
        {
            modelCrops.add(new Crop("Brinjal/Eggplant",R.drawable.eggplant,null,null,null,null,null,null));
        }
        //Wheat
        {
            modelCrops.add(new Crop("Wheat",R.drawable.wheat,null,null,null,null,null,null));
        }
        //Cotton
        {
            modelCrops.add(new Crop("Cotton",R.drawable.cotton,null,null,null,null,null,null));
        }
    }

    private static void initializeAllCrops() {

        String introduction;
        String climate;
        String soil;
        String fertilizer;
        String irrigation;
        String disease;

        //Wheat
        {
            introduction = "Wheat (Triticum spp.) occupies the prime position among the food crops in the world. In India, it is the second important food crop being next to rice and contributes to the total foodgrain production of the country to the extent of about 25%. Wheat has played a very vital role in stabilizing the foodgrain production in the country over the past few years.<br>" +
                    "<b>Types of wheat</b><br>1. Emmer Wheat<br>2. Macroni Wheat<br>3. Common Bread Wheat<br>4. Indian Dwarf Wheat";

            climate = "The ideal temperature requirement varies from plant type and stages of growth. The dwarf varieties require the following temperature for their growth and development:<br>" +
                    "<b>Growth stages --> Temperature requirement</b><br>1. Germination --> 20 to 25<sup>o</sup> C mean daily temperature<br>2. Tillering --> 16 to 20<sup>o</sup>C mean daily temperature<br>3. Accelerated growth --> 20 to 23<sup>o</sup>C mean daily temperature<br>4. Proper grain filling --> 23 to 25<sup>o</sup>C mean daily temperature <br><br> Wheat plants are sensitive to very cold or frost injury at any stage of growth particularly at reproductive growth if temperature is below 15<sup>o</sup>C.";

            soil = "The wheat crop requires a well-pulverized but compact seed bed for good and uniform germination. Three or four ploughings in the summer, repeated harrowing in the rainy season, followed by three or four cultivations and planking immediately before sowing produce a good, firm seed bed for the dry crop on alluvial soils.";

            fertilizer = "<p>It is desirable that 2 to 3 tonnes of farmyard manure per hectare or some other organic matter is applied 5 or 6 weeks before sowing.</p><p>Total quantity of Phosphorus and potash and half the quantity of nitrogen should be applied at the time of sowing. Remaining quantity of Nitrogen should be applied at the time of crown root initiation.For the late sown irrigated wheat crop, the NPK fertilizer dose recommended is:</p>" +
                    "N --> 60-80 kg/ha <br> P<sub>2</sub>O<sub>5</sub> --> 30-40 kg/ha<br>K<sub>2</sub>O --> 20-25 kg/ha.";

            irrigation = "The high yielding wheat varieties should be given five to six irrigations at their critical growth stages viz. Crown root initiation, tillering, jointing, flowering, milk and dough which come at 21-25 days after sowing (DAS), 45-60 DAS, 60-70 DAS, 90-95 DAS, 100-105 DAS and 120-125 DAS respectively. Off these irrigation at CRI stage is most important.";

            disease = "<b>Name of disease  --> Name of chemicals </b> <br> 1. Smut --> Seed dressing with Vitavax/Benlate/G 696 <br> 2. Rust --> Zinc/Manganese <br> 3. Bunts --> Seed dressing with Agrosan GN/Vitavax <br> 4. Molya --> Nemagon DECP 60% EC";
            allCrops.add(new Crop("Wheat", R.drawable.wheat,introduction, climate, soil, fertilizer,irrigation, disease));
        }

        //Bajra
        {
            introduction = "Bajra popularly known as Pearl millet, cattail millet or bulrush belongs to the family Graminea. The crop is cultivated for grain as well as for fodder in the arid region of Africa and Asia and as a pasture in U.S.A. It is originated in India or Africa. It is grown all over India except Assam and part of northeast India.";
            climate = "The crop has a wide adaptability as it may grow under different day lengths, temperature and moisture stress. Most of the varieties developed in India are photosensitive which helps in growing the crop during monsoon, rabi and arid season. It requires low annual rainfall ranging between 40-50 cm and dry weather. The crop may tolerate drought but cannot withstand high rainfall of 90 cm or above.";
            soil = "Light soils of low inherent fertility good drainage, mild salinity are best type for this crop. Crop does not tolerate soil acidity. The crop needs very fine tilt because the seeds are too small. 2-3 harrowings and a ploughing is followed so that a fine tilth may be obtained to facilitate the sowing and proper distribution of seed at appropriate depth.";
            fertilizer = "Fertilizers are applied in split doses, half of nitrogen, full phosphorus and potash should be basal placed at the time of sowing . The organic manures must be applied 20 days before the sowing of the seeds for full decomposition. One fourth dose of nitrogen should be applied about 30 days and 60 days after sowing. ";
            irrigation = "Bajra is grown rainfed and crop being drought resistant hardly needs any irrigation, however it is observed that the yield may be significantly increased by irrigating the crop at critical growth stages like maximum tillering, flowering and grain filling stage. Therefore light irrigations and efficient drainage is very essential for bajra production.";
            disease = "<b>Downy mildew</b> --> for controlling this disease seed treatment with fungicide like Dithane Z-78 or M-45 @ 2.0kg/ha in 800-1000 lit. of water. <br> <b>Smut</b> --> Treatment with Ceresan or Thirum @ 1-2 g/kg seeds is effective. <br><b>Ergot</b> --> Seed treatment with 20% common salt solution followed by washing with fresh water and then treating with Ceresan or Thirum @ 1-2 g/kg seeds is effective";

            allCrops.add(new Crop("Bajra",R.drawable.bajra,introduction,climate,soil,fertilizer,irrigation,disease));
        }

        //Maize
        {
            introduction = "Maize (Zea mays L.) is one of the most versatile emerging crop shaving wider adaptability under varied agro-climatic conditions. In addition to staple food for human being and quality feed for animals, maize serves as a basic raw material as an ingredient to thousands of industrial products that includes starch, oil, protein, alcoholic beverages, food sweeteners, pharmaceutical, cosmetic, film, textile, gum, package and paper industries etc.";
            climate = " Maize does well on a wide range of climatic conditions, and it is grown in the tropical as well as temperate regions, from sea-levels up to altitudes of 2500m. It is however susceptible to frost at all stages of its growth.";
            soil = "Maize can be grown successfully in variety of soils ranging from loamy sand to clay loam. However, soils with good organic matter content having high water holding capacity with neutral pH are considered good for higher productivity. Being a sensitive crop to moisture stress particularly excess soil moisture and salinity stresses; it is desirable to avoid low lying fields having poor drainage and also the field having higher salinity. Therefore, the fields having provision of proper drainage should be selected for cultivation of maize.";
            fertilizer = "For higher economic yield of maize, application of 10 t FYM ha-1, 10-15 days prior to sowing supplemented with 150-180 kg N, 70-80 kg P<sub>2</sub>O<sub>5</sub>, 70-80 kg K<sub>2</sub>O and 25 kg ZnSO<sub>4</sub> ha-1 is recommended. Full doses of P, K and Zn should be applied as basal preferably drilling of fertilizers in bands along the seed using seed-cum-fertilizer drills.";
            irrigation = "The irrigation water management depends on season as about 80 % of maize is cultivated during monsoon season particularly under rainfed conditions. However, in areas with assured irrigation facilities are available, depending upon the rains and moisture holding capacity of the soil, irrigation should be applied as and when required by the crop and first irrigation should be applied very carefully wherein water should not overflow on the ridges/beds.";
            disease = "<b>Leaf Blight - </b> Manifestation of oval to round, yellowish-purple spots on leaves. The affected leaves dry up and appear as if burnt. In severe cases, the plants may become stunted, resulting in poorly-formed ears." +
                    "<br><b>Control : </b> The crop can be sprayed with Dithane M-45 or Indofil @ 35-40 gms or Blue Copper @55 -60 gms in 18 litres water, 2 -3 sprays at 15 days interval, will effectively control the disease.";

            allCrops.add(new Crop("Maize/Corn",R.drawable.maize,introduction,climate,soil,fertilizer,irrigation,disease));
        }

        //Jowar
        {
            introduction = "Sorghum (Sorghum vulgare Pers.), popularly known as jowar, is the most important food and fodder crop of dryland agriculture. The annual area under it ranges between 17 and 18 million hectares and the annual production between 8 and 10 million tonnes.Jowar is mainly concentrated in the peninsular and central India. Maharashtra, Karnataka, Andhra Pradesh, Madhya Pradesh, Gujarat, Rajasthan, Uttar Pradesh (the Bundelkhand region) and Tamil Nadu are the major jowar – growing states. ";
            climate = "Sorghum plants are very hardy and can withstand high temperature and drought, however, it is grown in arid regions of U.P, Rajasthan and humid regions of Bengal and Bihar. It may be successfully grown under atmospheric temperature ranging between 15<sup>o</sup>C to 40<sup>o</sup>C and annual rainfall ranging from 400 to 1000 mm.";
            soil = "Sorghum is grown on a variety of soil types but the clayey loam soil rich in humus is found to be the most ideal soil. It may tolerate mild acidity to mild salinity under pH 5.5 to 8.0. A good sorghum soil must have an efficient drainage facilities though, it may withstand water logging more than maize.";
            fertilizer = "Sorghum is an exhaustive crop and it depletes soil fertility very fast, if proper care is not taken.An optimum dose may be found out from the following details: an optimum dose of nitrogen for rainfed high yielding and local varieties of irrigated crop should be 60-80kg/ha while for irrigated high yielding varieties it should be between 120-150 kg/ha. " +
                    "Under low rainfall or in rainfed areas top-dressing of nitrogen is not required. On an average a dose of 40-60 kg P<sub>2</sub>O<sub>5</sub>/ha is found to be good. Placement at 4-6 cm depth has given better results. However, under normal conditions it is mostly basal placed. Potash at the rate of 40kg/ha applied at the time of field preparation gives good result.";
            irrigation = "Sorghum is a fairly drought resistant crop and it does very well in areas receiving 50 cm well distributed rainfall but it cannot withstand waterlogging at any stage of crop growth. The most critical growth stages for irrigation are knee-height stage, flowering and grain filling stages at which the crop should be ensured for proper moisture conditions so that the crop does not suffer from moisture stress.";
            disease = "<b> Diseases --> Control Measures </b> <br> 1. Grain smut --> Seed treatment with organo-mercurial compound viz. Ceresan, Agrosan GN, etc. <br> 2. Downy midew --> a)  Avoid waterlogging <br> b) Removal and burning/burying of diseased plants.<br> c) Application of Dithane Z-78 @ 0.2 % spray solution reduces secondary infaction.";

            allCrops.add(new Crop("Jowar/Sorghum",R.drawable.jowar,introduction,climate,soil,fertilizer,irrigation,disease));
        }

        //Tomato
        {
            introduction = "Tomato is the world’s largest vegetable crop and known as protective food both because of its special nutritive value and also because of its wide spread production. The estimated area and production of tomato for India are about 3,50,000 hectares and 53,00,000 tons respectively. In Maharashtra area under tomato cultivation is 43,600 hectare.";
            climate = "Tomato is a warm season crop, it requires warm and cool climate. The plants cannot withstand frost and high humidity. Also light intensity affects pigmentation, fruit colour, fruit set. The plant is highly affected by adverse climatic conditions. It requires different climatic range for seed germination, seedling growth, flower and fruit set, and fruit quality. " +
                    "Temperature below 10<sup>o</sup>C and above 38<sup>o</sup>C adversely affects plant tissues thereby slow down physiological activities. It thrives well in temperature 10<sup>o</sup>C to 30<sup>o</sup>C with optimum range of temperature is 21<sup>o</sup>-24<sup>o</sup>C. The mean temperature below 16<sup>o</sup>C and above 27<sup>o</sup>C are not desirable.";
            soil = "Tomatoes do very well on most mineral soils, but they prefer deep, well drained sandy loams. Upper layer of soil should be porous with little sand and good clay in the subsoil. Soil depth 15 to 20cm proves to be good for healthy crop. Deep tillage can allow for adequate root penetration in heavy clay type soils, which allows for production in these soil types." +
                    "<br> Tomato is a moderately tolerant crop to a wide pH range. A pH of 5.5- 6.8 is preferred. Though tomato plants will do well in more acidic soils with adequate nutrient supply and availability. Tomato is moderately tolerant to acid an soil that is pH of 5.5. The soils with proper water holding capacity, aeration, free from salts are selected for cultivation." ;
            fertilizer = "As the fruit production and quality depends upon nutrient availability and fertilizer application so balance fertilizer are applied as per requirement. The nitrogen in adequate quantity increases fruit quality, fruit size, color and taste. It also helps in increasing desirable acidic flavor. Adequate amount of potassium is also required for growth, yield and quality." +
                    "<br>Normally tomato crop requires 120kg Nitrogen (N), 50kg Phosphorus (P<sub>2</sub>O<sub>5</sub>), and 50kg Potash (K<sub>2</sub>O). Nitrogen should be given in split doses. Half nitrogen and full P<sub>2</sub>O<sub>5</sub> is given at the time of transplanting and remaining nitrogen is given after 30 days and 60 days of transplanting.";
            irrigation = "Tomatoes have been observed to withdraw water from depths up to 13 feet in a well structured soil. Tomato plants require adequate moisture throughout their growth period. First irrigation is required soon after seedlings are transplanted. Frequent water is necessary in root zone when plants are small." +
                    " In summer irrigation at intervals of 3-4 days and 10-15 days water is necessary to maintain wet soil. Erratic moisture conditions can cause radial and concentric cracking on fruit.";
            disease = "<b>Diseases --> Control Measures</b><br> 1. Bacterial fruit spot --> Streptocycline (100PPm) or copper fungicides <br> 2. Early blight --> Mancozeb @ 0.2% <br> 3. Late blight --> Mancozeb @ 0.2% <br> 4. Septoria leaf spot --> Mancozeb @2 g/kg, 0.2% Dithane Z-78 <br> 5. Wilt --> 0.1% carbendazim or benomyl <br> 6. Powdery mildew --> Dinocap at 0.1% or Wet sulphur at 0.2%";

            allCrops.add(new Crop("Tomato",R.drawable.tomato,introduction,climate,soil,fertilizer,irrigation,disease));
        }

        //Potato
        {
            introduction = "The Potato, a native of South America occupies a largest area any single vegetable in the world. It has a special value as food. The consumption of potato per head is very low in India as compared to that of Western Countries.";
            climate = "The short day conditions are highly favourable for economic yield of potato crops. To secure good for potato a day length of 12-13 hours is needed and night temperature 20<sup>o</sup> C increase tuberization.";
            soil = "Potato can be grown well in fertile and well-drained soil. Potato grows well in soil having pH 4.8-5.4. Heavy soil should be avoided. ";
            fertilizer = "Farm Yard Manure(F.Y.M) 250-400 g/hectare,<br> 120-160 kg/hectare N,<br> 80-120 kg/hectare P,<br> 80-120 kg/hectare K.<br> To be incorporated into the soil 3 to 4 weeks before planting half dose of nitrogen full dose of P<sub>2</sub>O<sub>5</sub> and K<sub>2</sub>O as basal dressing at planting and remaining nitrogen dose is given at the time of earthing up.";
            irrigation = "Potato needs irrigation at frequent intervals, depending upon the soil and climatic conditions. Usually 6 irrigation is sufficient. Pre-sowing irrigation followed by 5-6 light irrigations.";
            disease = "<b>Diseases --> Control Measures</b><br> 1. Nematodes --> Fumigate soil with D-D <br> 2. Early Blight –-> Spray Dithane M-45 (0.2%) <br> 3. Late blight --> Spray Dithane M-45 or Dithane Z-78 or Difolatan or Zineb. <br> 4. Scab --> Disinfect tubers with Agallol-6 or Mercuric fungicides.";

            allCrops.add(new Crop("Potato",R.drawable.potato,introduction,climate,soil,fertilizer,irrigation,disease));
        }

        //Onion
        {
            introduction = "Areawise India ranks second while production wise it ranks third among the total onion production in the world. In the world total area under cultivation of onion is about 19,77,000 hectare which gives 2,79,18,000 mt. Major producing countries are North America, Japan, Spain, Netherland, Canada, etc. while in India it is grown in Gujarat, Punjab, Haryana. Area under cultivation in Maharashtra is about 54,600 hectare.";
            climate = " It is grown under a wide range of climatic conditions. However, it cannot stand too hot or too cold weather. It prefers moderate temperature in summer as well as in winter. Short days are very favorable for the formation of bulbs.";
            soil = "An ideal soil should have pH in between 6.5 to 8. The soil should be well aerated. Heavy soil should be avoided. Onion requires well drained loamy soils, rich in humus, with fairly good content of potash. The crop raised on sandy or loose soil does soils, the bulbs produced are deformed and during harvesting, many bulbs are broken and bruised and so they do not keep well in storage.";
            fertilizer = "Farm Yard Manure(F.Y.M) 200-250 g/hectare,<br> 45-65 kg/hectare N,<br> 40-60 kg/hectare P, <br> 60-100 kg/hectare K.";
            irrigation = "Irrigation during growth should be steady and uninterrupted otherwise dryness may cause splitting of the outer scales. Irrigation is stopped when the tops mature and start falling.";
            disease = "<b>Diseases --> Control Measures</b><br> 1. Downy mildew --> Spray Difolatan (0.1%) or Dithane M-45 (0.2%) <br> 2. Smut --> Spray Captan, Biltox or Thiram 75%";

            allCrops.add(new Crop("Onion",R.drawable.onion,introduction,climate,soil,fertilizer,irrigation,disease));
        }
    }


}
