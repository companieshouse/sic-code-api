package uk.gov.companieshouse.siccode.api.search;

import java.time.LocalDateTime;

/**
 * SicCodeTestData - This class is used to store mock test data that is commonly used in test classes.
 * The score is not used in unit test becuase it is a transiently value in the model object since it is obtained
 * from a search rather than a value stored in the mongo repository
 */

public class SicCodeTestData {

    public final static CombinedSicActivitiesStorageModel BARLEY_FARMING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("10", "01110", "Barley Farming",
    "barley farming", "Growing of cereals except rice, leguminous crops and oil seeds", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel BARLEY_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
    "barley growing", "Growing of cereals except rice, leguminous crops and oil seeds", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel BEAN_GROWING_ORGANIC_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing organic",
    "bean growing organic", "Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel BEAN_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("18", "01110", "Bean growing",
    "bean growing", "Growing of cereals except rice, leguminous crops and oil seeds", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel ARMOURED_CAR_SERVICES_STORAGE_MODEL= new CombinedSicActivitiesStorageModel("20", "80100", "Armoured car services",
    "armoured car services", "public security activities", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel BARLEY_MALTING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("30", "11060", "Barley malting (manufacture)",
    "barley malting manufacture", "Manufacture of malt", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    public final static CombinedSicActivitiesStorageModel BUS_MANUFACTURE_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("40", "29201", "Body for bus (manufacture)",
    "body for bus manufacture", "Manufacture of bodies (coachwork) for motor vehicles (except caravans)", false, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    

    public final static CombinedSicActivitiesApiModel BARLEY_FARMING_API_MODEL = new CombinedSicActivitiesApiModel("01110", "Barley Farming",
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    public final static CombinedSicActivitiesApiModel BARLEY_GROWING_API_MODEL = new CombinedSicActivitiesApiModel("01110", "Barley growing",
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    public final static CombinedSicActivitiesApiModel BEAN_GROWING_ORGANIC_API_MODEL = new CombinedSicActivitiesApiModel("01110", "Bean growing organic",
    "Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false);

    public final static CombinedSicActivitiesApiModel BEAN_GROWING_API_MODEL = new CombinedSicActivitiesApiModel("01110", "Bean growing",
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    public final static CombinedSicActivitiesApiModel ARMOURED_CAR_SERVICES_API_MODEL= new CombinedSicActivitiesApiModel("80100", "Armoured car services",
    "public security activities", false);

    public final static CombinedSicActivitiesApiModel BARLEY_MALTING_API_MODEL = new CombinedSicActivitiesApiModel("11060", "Barley malting (manufacture)",
    "Manufacture of malt", false);

    public final static CombinedSicActivitiesApiModel BUS_MANUFACTURE_API_MODEL = new CombinedSicActivitiesApiModel("29201", "Body for bus (manufacture)",
    "Manufacture of bodies (coachwork) for motor vehicles (except caravans)", false);

}
