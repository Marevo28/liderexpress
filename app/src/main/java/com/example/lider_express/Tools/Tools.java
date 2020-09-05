package com.example.lider_express.Tools;

public class Tools {

    public static class BND{

        // available tools:
        public static final String PUMP_CONTROL_CARD = "PumpControlCard";
        public static final String CONTAINER_CONTROL_CARD = "ContainerControlCard";

        public static String[] getStructure(String toolName){
            String[] structure = null;
            switch (toolName){
                case PUMP_CONTROL_CARD:
                    structure = new String[]{"bnd", "pump", "controlCard"};
                    break;
                case CONTAINER_CONTROL_CARD:
                    structure = new String[]{"bnd", "container", "controlCard"};
                    break;
            }
            return structure;
        }

    }

}
