package com.sourcelan.japortie.gamelistfilter.parser;

import com.sourcelan.japortie.gamelistfiler.types.GameList;
import com.sourcelan.japortie.gamelistfiler.types.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class GameListParser {
    static private Logger LOGGER = LoggerFactory.getLogger(GameListParser.class);
    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;

    public GameListParser() throws GameListParserException {
        try {
            jaxbContext = JAXBContext.newInstance(GameList.class);
            unmarshaller = jaxbContext.createUnmarshaller();

        } catch (JAXBException e) {
            throw new GameListParserException("Couldn't create GameListParser", e);
        }
    }

    public List<GameType> getGameList(File file) throws GameListParserException {
        if(file == null) {
            throw new GameListParserException("Couldn't unmarshall File. File was null.");
        }
        try {
            return ((GameList) unmarshaller.unmarshal(file)).getGame();
        } catch (JAXBException e) {
            throw new GameListParserException("Couldn't unmarshall File", e);
        }
    }
}
