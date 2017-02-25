package com.sourcelan.japortie.gamelistfilter.listbuilder;

import com.sourcelan.japortie.gamelistfiler.types.GameList;
import com.sourcelan.japortie.gamelistfiler.types.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GameListBuilder {
    static private Logger LOGGER = LoggerFactory.getLogger(GameListBuilder.class);

    private Map<Integer, GameType> gameTypeMap;

    public GameListBuilder() {
        gameTypeMap = new HashMap<Integer, GameType>();
    }

    public GameType getGame(Integer gameId) {
        return gameTypeMap.get(gameId);
    }

    public void saveGame(GameType gameType) {
        LOGGER.info("Add Game '" + gameType.getName() + "' to map");
        // Remove Meta information from game title (e.G. "(Europe) (Beta)")
        gameType.setName(gameType.getName().replaceAll("\\(.*?\\) ?", "").trim());
        LOGGER.info("Set name to '" + gameType.getName() + "'");
        gameTypeMap.put(gameType.getId(), gameType);
    }
    
    public GameList buildGameList() {
        GameList gameList = new GameList();
        gameList.getGame().addAll(gameTypeMap.values());
        return gameList;
    }
}
