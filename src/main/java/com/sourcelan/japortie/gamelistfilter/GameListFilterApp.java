package com.sourcelan.japortie.gamelistfilter;

import com.sourcelan.japortie.gamelistfilter.comparator.GameComparator;
import com.sourcelan.japortie.gamelistfilter.listbuilder.GameListBuilder;
import com.sourcelan.japortie.gamelistfilter.parser.GameListParser;
import com.sourcelan.japortie.gamelistfilter.parser.GameListParserException;
import com.sourcelan.japortie.gamelistfilter.persistor.GameListPersistor;
import com.sourcelan.japortie.gamelistfilter.persistor.GameListPersistorException;
import com.sourcelan.japortie.gamelistfilter.processor.GameListProcessor;
import com.sourcelan.japortie.gamelistfilter.processor.GameListProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GameListFilterApp {
    static private Logger LOGGER = LoggerFactory.getLogger(GameListFilterApp.class);

    public static void main(String args[]) {
        if (args.length > 0) {
            try {
                GameListParser gameListParser = new GameListParser();
                GameComparator gameComparator = new GameComparator();
                GameListBuilder gameListBuilder = new GameListBuilder();
                GameListPersistor gameListPersistor = new GameListPersistor();

                GameListProcessor gameListProcessor = new GameListProcessor(gameListParser,
                        gameComparator,
                        gameListBuilder,
                        gameListPersistor);

                gameListProcessor.process(new File(args[0]));
            } catch (GameListParserException e) {
                LOGGER.error("Error during parsing of GameList.", e);
            } catch (GameListPersistorException e) {
                LOGGER.error("Error persisting GameList.", e);
            } catch (GameListProcessorException e) {
                LOGGER.error("Error processing GameList.", e);
            }
        } else {
            LOGGER.error("No GameList provided.");
        }
    }
}
