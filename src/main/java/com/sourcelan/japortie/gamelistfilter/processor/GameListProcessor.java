package com.sourcelan.japortie.gamelistfilter.processor;

import com.sourcelan.japortie.gamelistfiler.types.GameList;
import com.sourcelan.japortie.gamelistfiler.types.GameType;
import com.sourcelan.japortie.gamelistfilter.comparator.GameComparator;
import com.sourcelan.japortie.gamelistfilter.listbuilder.GameListBuilder;
import com.sourcelan.japortie.gamelistfilter.parser.GameListParser;
import com.sourcelan.japortie.gamelistfilter.parser.GameListParserException;
import com.sourcelan.japortie.gamelistfilter.persistor.GameListPersistor;
import com.sourcelan.japortie.gamelistfilter.persistor.GameListPersistorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class GameListProcessor {
    static private Logger LOGGER = LoggerFactory.getLogger(GameListProcessor.class);

    private GameListParser gameListParser;
    private GameComparator gameComparator;
    private GameListBuilder gameListBuilder;
    private GameListPersistor gameListPersistor;

    public GameListProcessor(GameListParser gameListParser,
                      GameComparator gameComparator,
                      GameListBuilder gameListBuilder,
                      GameListPersistor gameListPersistor) throws GameListProcessorException {
        this.gameListParser = gameListParser;
        this.gameComparator = gameComparator;
        this.gameListBuilder = gameListBuilder;
        this.gameListPersistor = gameListPersistor;

        validate();
    }

    private void validate() throws GameListProcessorException {
        if(this.gameListParser == null) {
            throw new GameListProcessorException("Coudln't create GameListProccessor: GameListParser is null!");
        }
        if(this.gameComparator == null) {
            throw new GameListProcessorException("Coudln't create GameListProccessor: GameComparator is null!");
        }
        if(this.gameListBuilder == null) {
            throw new GameListProcessorException("Coudln't create GameListProccessor: GameListBuilder is null!");
        }
        if(this.gameListPersistor == null) {
            throw new GameListProcessorException("Coudln't create GameListProccessor: GameListPersistor is null!");
        }
    }

    public void process(File file) {
        try {
            List<GameType> gameTypeList = gameListParser.getGameList(file);
            Integer inCount = gameTypeList.size();
            gameTypeList.forEach(gameType -> {
                    LOGGER.info("Process '" + gameType.getName() + "'");
                    if(gameComparator.compare(gameType, gameListBuilder.getGame(gameType.getId()))) {
                        gameListBuilder.saveGame(gameType);
                    }});
            GameList gameList = gameListBuilder.buildGameList();
            Integer outCount = gameList.getGame().size();
            LOGGER.info("Count games before: " + inCount);
            LOGGER.info("Count games after: " + outCount);
            gameListPersistor.writeGameList(file, gameList);
        } catch (GameListParserException e) {
            LOGGER.error("Couldn't process GameList. ", e);
        } catch (GameListPersistorException e) {
            LOGGER.error("Couldn't save GameListFile", e);
        }

    }
}
