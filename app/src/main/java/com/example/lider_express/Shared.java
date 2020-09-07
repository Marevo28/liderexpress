package com.example.lider_express;

import android.content.Context;

/**
 * @author LeStat
 */

public class Shared {

    public static Context context = MainActivity.getContext();

    public static boolean flagUpdate = false;

    // Эта переменная для файла базы данных, который будет обновляться
    public static String nameUpdateDB = "leaderexpert.db";

    // Эта переменная идет в конуструктор DataBaseHelper
    // C этим именем создается база данных
    public static String nameDB = "leaderexpert.db";

    // Общий список имен таблиц оборудования и дефектов
    // Таблицы в Базе данных называть соответсвенно
    // 2019
    public static String nameBND2019 = "Bashneft2019";
    public static String nameMegion2019 = "Megion2019";
    public static String namePolus2019 = "Polus2019";
    public static String nameDefectBND2019 = "DefectBashneft2019";
    public static String nameDefectMegion2019 = "DefectMegion2019";
    public static String nameDefectPolus2019 = "DefectPolus2019";
    // 2020
    public static String nameBND2020 = "Bashneft2020";
    public static String nameMegion2020 = "Megion2020";
    public static String namePolus2020 = "Polus2020";
    public static String nameDefectBND2020 = "DefectBND2020";
    public static String nameDefectMegion2020 = "DefectMegion2020";
    public static String nameDefectPolus2020 = "DefectPolus2020";

    public static String nameBashneft2020_UDE = "Bashneft2020_UDE";
    public static String nameDefectBashneft2020_UDE = "DefectBND2020_UDE";
    public static String nameDefectBashneft2020_SPPK = "DefectBND2020_SPPK";
    public static String nameBashneft2020_Nasos = "Bashneft2020_Nasos";
    public static String nameDefectBashneft2020_Nasos = "DefectBND2020_Nasos";
    public static String nameDefectBashneft2020_Container = "DefectBND2020_Container";

    /**
     *
     *  XMMP
     *
     */
    public static String nameHMMR = "HMMR";
    public static String nameHMMP_Pump = "HMMR_Pump";
    public static String nameHMMP_Container = "HMMR_Container";
    public static String nameDefectHMMR_Pump = "DefectHMMR_Pump";
    public static String nameDefectHMMR_Container = "DefectHMMR_Container";

    // 2021
    public static String nameBND2021 = "Bashneft2021";
    public static String nameMegion2021 = "Megion2021";
    public static String namePolus2021 = "Polus2021";
    public static String nameDefectBND2021 = "DefectBashneft2021";
    public static String nameDefectMegion2021 = "DefectMegion2021";
    public static String nameDefectPolus2021 = "DefectPolus2021";
    // 2022
    public static String nameBND2022 = "Bashneft2022";
    public static String nameMegion2022 = "Megion2022";
    public static String namePolus2022 = "Polus2022";
    public static String nameDefectBND2022 = "defectBashneft2022";
    public static String nameDefectMegion2022 = "defectMegion2022";
    public static String nameDefectPolus2022 = "defectPolus2022";
    // 2023
    public static String nameBND2023 = "Bashneft2023";
    public static String nameMegion2023 = "Megion2023";
    public static String namePolus2023 = "Polus2023";
    public static String nameDefectBND2023 = "defectBashneft2023";
    public static String nameDefectMegion2023 = "defectMegion2023";
    public static String nameDefectPolus2023 = "defectPolus2023";
    // 2024
    public static String nameBND2024 = "Bashneft2024";
    public static String nameMegion2024 = "Megion2024";
    public static String namePolus2024 = "Polus2024";
    public static String nameDefectBND2024 = "defectBashneft2024";
    public static String nameDefectMegion2024 = "defectMegion2024";
    public static String nameDefectPolus2024 = "defectPolus2024";


    // Const
    public static final String BND_TYPE_SPEC_ALL = "0";
    public static final String BND_TYPE_DATA = "1";
    public static final String BND_TYPE_SPEC_NKO = "2";
    public static final String BND_TYPE_SPEC_JOURNAL = "3";
    public static final String BND_TYPE_EXP_JOURNAL = "4";

    // Путь папки для обновления файла баззы данных
    public static String pathUpdateDB = "data/data/com.example.lider_express/databaseUpdate";

    // Путь папки базы данных
    public static String pathDB = "data/data/com.example.lider_express/databases";

    // Адрес для скачивания базы данных
    public static String URL = "http://officele.ru/files/leaderexpert.db";

    public static String URLAllForm = "http://officele.ru/Android/DefectBND2020_AllForm.php";

}
