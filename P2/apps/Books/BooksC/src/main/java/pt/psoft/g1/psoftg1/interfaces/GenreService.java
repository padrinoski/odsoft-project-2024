package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.model.Genre;
import pt.psoft.g1.psoftg1.model.GenreDTO;


public interface GenreService {
    GenreDTO create(final Genre book) throws  Exception;
    GenreDTO updateByPK(final String pk, final Genre book) throws Exception;
    void deleteByPK(final String pk) throws Exception;
}
