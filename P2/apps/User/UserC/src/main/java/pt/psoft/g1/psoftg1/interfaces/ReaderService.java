package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.dto.ReaderDTO;
import pt.psoft.g1.psoftg1.dto.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.model.Reader;

/**
 *
 */

public interface ReaderService {
    ReaderDTO create(Reader reader) throws  Exception;
    ReaderDTO update(String id, Reader request) throws  Exception;
    void removeReader(String readerNumber) throws  Exception;
}
