package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.interfaces.GenreService;
import pt.psoft.g1.psoftg1.model.Genre;
import pt.psoft.g1.psoftg1.model.GenreDTO;

@Service
@Log4j2
public class GenreServiceImpl implements GenreService {

    @Override
    public GenreDTO create(Genre book) throws Exception {
        return null;
    }

    @Override
    public GenreDTO updateByPK(String pk, Genre book) throws Exception {
        return null;
    }

    @Override
    public void deleteByPK(String pk) throws Exception {

    }
}
