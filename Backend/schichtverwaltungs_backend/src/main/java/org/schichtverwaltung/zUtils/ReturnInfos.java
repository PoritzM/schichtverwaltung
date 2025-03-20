package org.schichtverwaltung.zUtils;

import java.util.ArrayList;

public class ReturnInfos {

    private ArrayList<ArrayList<String>> infos = new ArrayList<>();

    public ReturnInfos () {
        infos = new ArrayList<>();
    }

    public void addInfo (String description, int value) {
        ArrayList<String> infoSet = new ArrayList<>();

        infoSet.add(description);
        infoSet.add(String.valueOf(value));

        infos.add(infoSet);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < infos.size(); i++) {
            sb.append("[");
            ArrayList<String> innerList = infos.get(i);
            for (int j = 0; j < innerList.size(); j++) {
                sb.append(innerList.get(j));
                if (j < innerList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < infos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
