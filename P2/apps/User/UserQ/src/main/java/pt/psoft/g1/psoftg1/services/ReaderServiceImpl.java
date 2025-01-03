package pt.psoft.g1.psoftg1.services;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.interfaces.ReaderService;
import pt.psoft.g1.psoftg1.interfaces.UserRepository;
import pt.psoft.g1.psoftg1.mappers.ReaderMapper;
import pt.psoft.g1.psoftg1.model.reader.ReaderDetails;
import pt.psoft.g1.psoftg1.model.user.Reader;
import pt.psoft.g1.psoftg1.dto.CreateReaderRequest;
import pt.psoft.g1.psoftg1.dto.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.dto.SearchReadersQuery;
import pt.psoft.g1.psoftg1.dto.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepo;

    private final UserRepository userRepo;
    private final ReaderMapper readerMapper;

    private final ForbiddenNameRepository forbiddenNameRepository;

    private final PhotoRepository photoRepository;

// todo
//    @Override
//    public List<ReaderBookCountDTO> findTopByGenre(String genre, LocalDate startDate, LocalDate endDate){
//        if(startDate.isAfter(endDate)){
//            throw new IllegalArgumentException("Start date cannot be after end date");
//        }
//        Pageable pageableRules = PageRequest.of(0,5);
//        return this.readerRepo.findTopByGenre(pageableRules, genre, startDate, endDate).getContent();
//    }


    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        return this.readerRepo.findByReaderNumber(readerNumber);
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        return this.readerRepo.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<ReaderDetails> findByUsername(final String username) {
        return this.readerRepo.findByUsername(username);
    }


    @Override
    public Iterable<ReaderDetails> findAll() {
        return this.readerRepo.findAll();
    }

    // todo
//    @Override
//    public List<ReaderDetails> findTopReaders(int minTop) {
//        if(minTop < 1) {
//            throw new IllegalArgumentException("Minimum top reader must be greater than 0");
//        }
//
//        Pageable pageableRules = PageRequest.of(0,minTop);
//        Page<ReaderDetails> page = readerRepo.findTopReaders(pageableRules);
//        return page.getContent();
//    }

    private List<String> getGenreListFromStringList(List<String> interestList) {
        if(interestList == null) {
            return null;
        }

        if(interestList.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> genreList = new ArrayList<>();
        for(String interest : interestList) {
            Optional<String> optGenre = "todo".describeConstable();

            // todo call genre microsservice to findByString
            // genreRepo.findByString(interest);
            if(optGenre.isEmpty()) {
                throw new NotFoundException("Could not find genre with name " + interest);
            }

            genreList.add(optGenre.get());
        }

        return genreList;
    }

}
