package com.sourcelan.japortie.gamelistfilter.persistor;

import com.sourcelan.japortie.gamelistfiler.types.GameList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class GameListPersistor {
    private JAXBContext jaxbContext;
    private Marshaller marshaller;

    public GameListPersistor() throws GameListPersistorException {
        try {
            jaxbContext = JAXBContext.newInstance(GameList.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            throw new GameListPersistorException("Couldn't create GameListPersistor", e);
        }
    }

    public void writeGameList(File file, GameList gameList) throws GameListPersistorException {
        if(file == null) {
            throw new GameListPersistorException("Couldn't write GameList. File is null!");
        }
        if(gameList == null) {
            throw new GameListPersistorException("Couldn't write GameList. GameList is null!");
        }
        try {
            marshaller.marshal(gameList, file);
        } catch (JAXBException e) {
            throw new GameListPersistorException("Couldn't write GameList", e);
        }
    }
}
