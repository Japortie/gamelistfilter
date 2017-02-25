package com.sourcelan.japortie.gamelistfilter.comparator;

import com.sourcelan.japortie.gamelistfiler.types.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GameComparator {
    static private Logger LOGGER = LoggerFactory.getLogger(GameComparator.class);

    private Map<String, Integer> regionRatingMap;

    public GameComparator() {
        regionRatingMap = new HashMap<String, Integer>();
        regionRatingMap.put("germany", 24);
        regionRatingMap.put("europe", 20);
        regionRatingMap.put("usa, europe", 16);
        regionRatingMap.put("usa", 12);
        regionRatingMap.put("world", 8);
        regionRatingMap.put("japan, usa", 4);
        regionRatingMap.put("japan", 1);
    }

    public Boolean compare(GameType gameType1, GameType gameType2) {
        if(gameType2 == null) {
            LOGGER.info("First version of the Game");
            return true;
        } else if (gameType1 == null) {
            return false;
        }

        LOGGER.info("Compare Game '" + gameType1.getName() + "' with '" + gameType2.getName() + "'");
        return(rankGame(gameType1) > rankGame(gameType2));
    }

    private Integer rankGame(GameType gameType) {
        String region = gameType.getRegion();
        if (region != null && regionRatingMap.get(gameType.getRegion().toLowerCase()) != null) {
            Integer rating = regionRatingMap.get(gameType.getRegion().toLowerCase()) + 5;
            if (gameType.getName().toLowerCase().contains("beta")) {
                rating = rating - 5;
            } else if (gameType.getName().toLowerCase().contains("(rev 1)") || gameType.getName().toLowerCase().contains("(rev a)")) {
                rating = rating + 2;
            } else if (gameType.getName().toLowerCase().contains("(rev 2)") || gameType.getName().toLowerCase().contains("(rev b)")) {
                rating = rating + 3;
            } else if (gameType.getName().toLowerCase().contains("(rev 3)") || gameType.getName().toLowerCase().contains("(rev c)")) {
                rating = rating + 4;
            } else if (gameType.getName().toLowerCase().contains("(rev 4)") || gameType.getName().toLowerCase().contains("(rev d)")) {
                rating = rating + 5;
            } else if (gameType.getName().toLowerCase().contains("(rev 5)") || gameType.getName().toLowerCase().contains("(rev e)")) {
                rating = rating + 6;
            }

            if (gameType.getRomtype().toLowerCase().equals("prototype")) {
                rating = rating - 10;
            }
            return rating;
        } else {
            return 0;
        }
    }
}
